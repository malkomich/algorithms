import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<String, Integer> iCiudades = new HashMap<String, Integer>(); //Pares nombre, posición.
    static int[][] PD, PT; //Serán las matrices "P" para la Distancia y el Tiempo para el cálculo del algoritmo de Floyd
    static int contCiudades = 0; //Marcará el total de nodos
    static double distancia = 0; //Los utilizaremos para el cálculo de tiempos  y distancias totales a la hora de 
    static double tiempo = 0;    //mostrarlos por pantalla

    public static void main(String args[]) throws IOException {

        File archivoCiudades = new File("ciudades.txt");
        File archivoRutas = new File("rutas.txt");
        FileReader frCiudades = new FileReader(archivoCiudades);
        FileReader frRutas = new FileReader(archivoRutas);
        BufferedReader brCiudades = new BufferedReader(frCiudades);
        BufferedReader brRutas = new BufferedReader(frRutas);

        String[] ciudad;
        boolean autovia = false;
        //Lista de todos los vértices del grafo.
        ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();

        // Lectura del fichero
        String linea;

        while ((linea = brCiudades.readLine()) != null) {

            ciudad = linea.split(" ");
            
            //Si el nombre va seguid de un +, marcamos autovia como verdadero
            if (ciudad[0].charAt(ciudad[0].length() - 1) == '+') {

                autovia = true;
                ciudad[0] = ciudad[0].substring(0, (ciudad[0].length()) - 1);
            }

            //Creamos un objeto Ciudad para cada una y la añadimos a la lista de nodos
            Ciudad c = new Ciudad(ciudad[0], autovia, Double.parseDouble(ciudad[1]), Double.parseDouble(ciudad[2]));
            ciudades.add(c);
            iCiudades.put(ciudad[0], contCiudades);
            contCiudades++;

            autovia = false;

        }

        //Creamos un ArrayList de vectores de String para almacenar cada para de ciudades para las rutas a calcular
        ArrayList<String[]> arrayRutasEntrada = new ArrayList<String[]>();

        while ((linea = brRutas.readLine()) != null) {

            ciudad = linea.split(" ");
            arrayRutasEntrada.add(ciudad);

        }

        //Crear carreteras y autovías.
        //Creamos las matrices de adyacencia
        Ruta[][] matrizAdy = new Ruta[contCiudades][contCiudades];

        //Inicializamos la matriz a infinito (-1)
        matrizAdy = inicializarMatriz(matrizAdy);

        Ruta ruta;
        int indiceCiudad;

        ////////////////////////////CREACIÓN DEL GRAFO///////////////////////////////////////
        
        /*
        Para cada ciudad dentro de la lista de nodos, comprobamos primero si tiene o no autovia.
        En caso afirmativo, buscamos su autovía más cercana y la añadimos como una nueva ruta.
        Puesto que dos ciudades unidas por autovía no tienen porque tener como su autovía más cercana
        aquella que esté unida a ella, creamos una ruta autovía en el sentido opuesto y más adelante
        calculamos cual es, para dicha ciudad, su autovía más cercana (puede ser la ya enlazada o no)
        */
        for (int i = 0; i < contCiudades; i++) {

            if (ciudades.get(i).isAutovia() == true) { //Si la cuidad ermite autovía buscar autovía más cercana.

                //Buscar autovía más cercana y crear autovía (ruta).
                ruta = aniadirAutoviaCercana(ciudades, i);

                indiceCiudad = iCiudades.get(ruta.getCiudadDestino().getNombre()); //Posición ciudad destino

                //Añadimos la ruta obtenida...
                matrizAdy[i][indiceCiudad] = ruta;

                //Ruta simetrica
                Ruta rutaSim = new Ruta(ruta.getCiudadDestino(), ruta.getVelocidad(), ruta.getCiudadOrigen(), ruta.isAutovia());
                
                //...y su simétrica
                matrizAdy[indiceCiudad][i] = rutaSim;
            }

            //Buscar carreteras más cercanas y crear carreteras.
            ArrayList<Ruta> listaCarreteras = aniadirCarreterasCercanas(ciudades, i);

            for (int j = 0; j < listaCarreteras.size(); j++) {

                //System.out.println("Carretera entre " + listaCarreteras.get(j).getCiudadOrigen().getNombre() + " y " + listaCarreteras.get(j).getCiudadDestino().getNombre() + " con distancia: " + listaCarreteras.get(j).distancia());
                indiceCiudad = iCiudades.get(listaCarreteras.get(j).getCiudadDestino().getNombre());
                
                //Comprobamos si para esas ciudades ya existen rutas previas (autovias) y escribimos solo si 
                //la ruta esta libre
                if (matrizAdy[i][indiceCiudad].getDistancia() == Double.POSITIVE_INFINITY) {
                    matrizAdy[i][indiceCiudad] = listaCarreteras.get(j);
                }

            }

        }

        ///////////////////CALCULAR FLOYD///////////////////////
        matrizAdy = floyd(matrizAdy);
        
        //Mostramos la salida por pantalla
        mostrarSolucion(arrayRutasEntrada, matrizAdy);

    }

    /////////////////////MÉTODOS///////////////////////////
    
    /**
     * Inicializa la matriz de adyacencia
     * @param m matriz de adyacencia
     * @return matriz ya inicializada a valores infinitos
     */
    static Ruta[][] inicializarMatriz(Ruta[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {

                //Hemos implementado el constructor vacío para dar valor infinito a tiempos y distancias
                m[i][j] = new Ruta();
            }
        }

        return m;
    }

    /**
     * Inicializa las matrices P al valor -1
     * @param p matriz de nodos intermedios
     * @return matriz inicializada al valor -1
     */
    static int[][] inicializarMatriz(int[][] p) {

        for (int i = 0; i < contCiudades; i++) {
            for (int j = 0; j < contCiudades; j++) {

                p[i][j] = -1;

            }
        }

        return p;

    }

    /**
     * Calcula la ciudad con autovía más cercana.
     *
     * @param ciudades lista de ciudades;
     * @param i índice de la ciudad origen.
     * @return ciudad más cercana.
     */
    static Ruta aniadirAutoviaCercana(ArrayList<Ciudad> ciudades, int i) {

        Ciudad ciudadOrigen = ciudades.get(i);
        Ciudad ciudadCercana = null;
        double distanciaMin = Double.POSITIVE_INFINITY;

        for (int j = 0; j < ciudades.size(); j++) {

            if ((ciudades.get(j).isAutovia()) && (ciudades.get(j).distancia(ciudadOrigen) < distanciaMin) && (j != i)) {

                ciudadCercana = ciudades.get(j);
                distanciaMin = ciudades.get(j).distancia(ciudadOrigen);
            }
        }

        Ruta rutaAutovia = new Ruta(ciudadOrigen, Constantes.VAUTOVIA, ciudadCercana, true);

        return rutaAutovia;
    }

    /**
     * Calcula las ciudades a menos de 200 Km. de una ciudad.
     *
     * @param ciudades lista de ciudades.
     * @param i índice de la ciudad de origen.
     * @return Lista de ciudades a menos de 200 Km.
     */
    static ArrayList<Ruta> aniadirCarreterasCercanas(ArrayList<Ciudad> ciudades, int i) {

        Ciudad ciudadOrigen = ciudades.get(i);
        ArrayList<Ruta> rutasCercanas = new ArrayList<Ruta>();

        for (int j = 0; j < ciudades.size(); j++) {

            if ((ciudadOrigen.distancia(ciudades.get(j)) < Constantes.RADIOCERCANIA) && (j != i)) {

                Ruta ruta = new Ruta(ciudadOrigen, Constantes.VCARRETERA, ciudades.get(j), false);

                rutasCercanas.add(ruta);
            }

        }

        return rutasCercanas;
    }

    
    /**
     * Algoritmo que calcula para cada ruta dentro de la matriz de adyacencia, los caminos óptimos
     * en distancia y en tiempo
     * @param M matriz de adyacencia
     * @return matriz de adyacencia con valores de tiempo y distancia óptimos
     */
    static Ruta[][] floyd(Ruta[][] M) {

        PD = new int[contCiudades][contCiudades];
        PT = new int[contCiudades][contCiudades];
        PD = inicializarMatriz(PD);
        PT = inicializarMatriz(PT);

        for (int k = 0; k < M.length; k++) {
            for (int i = 0; i < M.length; i++) {
                for (int j = 0; j < M.length; j++) {

                    if (M[i][j].getTOptimo() > (M[i][k].getTOptimo() + M[k][j].getTOptimo())) {

                        M[i][j].setTOptimo(M[i][k].getTOptimo() + M[k][j].getTOptimo());
                        PT[i][j] = k;
                    }

                    if (M[i][j].getDistancia() > (M[i][k].getDistancia() + M[k][j].getDistancia())) {

                        M[i][j].setDistancia(M[i][k].getDistancia() + M[k][j].getDistancia());
                        PD[i][j] = k;
                    }

                }
            }
        }

        return M;

    }

    /**
     * Escribe por pantalla los pasos que se han de seguir para llegar de un nodo a otro a través de la
     * matriz de adyacencia y la matriza de nodos intermedios
     * @param M matriz de adyacencia
     * @param i Ciudad origen en la matriz de adyacencia para el cálculo 
     * @param j Ciudad destino en la matriz de adyacencia para el cálculo
     * @param p matriz de nodos intermedios
     * @param c caracter que nos indica si hemos de calcular la ruta óptima en distancia ('d') o en tiempo ('t')
     */
    static void escribeRuta(Ruta[][] M, int i, int j, int[][] p, char c) {

        if (p[i][j] == -1) {
            
            distancia += M[i][j].getDistancia();
            
            //Si queremos obtener los datos relacionados con la mejor ruta en distancia, cogemos los tiempos
            //sin optimizar. En caso contrario, cogemos los tiempos óptimizados
            if (c == 'd'){
                tiempo += M[i][j].getTiempo();
            } else if(c == 't'){
                tiempo += M[i][j].getTOptimo();
            }

            if (M[i][j].isAutovia()) {
                System.out.println(M[i][j].getCiudadOrigen().getNombre() + " " + M[i][j].getCiudadDestino().getNombre() + " (Autovía)");
            } else {
                System.out.println(M[i][j].getCiudadOrigen().getNombre() + " " + M[i][j].getCiudadDestino().getNombre() + " (Carretera)");
            }

        } else {

            escribeRuta(M, i, p[i][j], p, c );
            escribeRuta(M, p[i][j], j, p, c);

        }

    }
    
    /**
     * Devuelve una cadena con el formato "D días H horas M.mmm minutos"
     * @param t tiempo en segundos
     * @return cadena formateada
     */
    static String formato(double t){
        
        String cadena = "";
        double minutos = t / 60;
        int horas = (int)minutos / 60;
        int dias = (int)horas / 24;
        
        cadena = cadena + dias + " dias " + horas%24 + " horas " + (double)Math.round((minutos%60)*1000)/1000 + " minutos "; 
        
        return cadena;
    }

    /**
     * Mostramos la solución de las rutas por pantalla
     * @param R Lista de rutas para calcular
     * @param M Matriz de adyacencia
     */
    static void mostrarSolucion(ArrayList<String[]> R, Ruta[][] M) {
        
        for (int i = 0; i < R.size(); i++) {
            distancia = 0;
            tiempo = 0;
            String[] rutaEntrada = new String[2];
            rutaEntrada = R.get(i);
            System.out.println("=============================================================");
            System.out.println("Ruta " + (i + 1));
            System.out.println(rutaEntrada[0] + "-" + rutaEntrada[1]);
            System.out.println("=============================================================");
            System.out.println("Mejor ruta en km");
            System.out.println("");
            escribeRuta(M, iCiudades.get(rutaEntrada[0]), iCiudades.get(rutaEntrada[1]), PD, 'd');
            System.out.println("");
            System.out.println("Total km = " + (double) Math.round(distancia*1000)/1000);
            System.out.println("Total tiempo = " + formato(tiempo));
            distancia = 0;
            tiempo = 0;
            System.out.println("=============================================================");
            System.out.println("Mejor ruta en tiempo");
            System.out.println("");
            escribeRuta(M, iCiudades.get(rutaEntrada[0]), iCiudades.get(rutaEntrada[1]), PT, 't');
            System.out.println("");
            System.out.println("Total km = " + (double) Math.round(distancia*1000)/1000);
            System.out.println("Total tiempo = " + formato(tiempo));
            System.out.println("=============================================================");
            System.out.println("");

        }

    }
}
