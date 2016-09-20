package practica3.ada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
		
		//METODOS para Diccionario,Entrada.txt
		//Metodo 1
		//Metodo que guarda en una lista un diccionario situado en un determinado fichero
		public static List<String> crearDiccionario(){
			List<String> diccionario = new ArrayList<String>();
			File archivo = null;
		    FileReader fr = null;
		    BufferedReader br = null;
		 
		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		         // hacer una lectura comoda (disponer del metodo readLine()).
		         archivo = new File ("diccionario.txt");
		         fr = new FileReader (archivo);
		         br = new BufferedReader(fr);
		         // Lectura del fichero
		         String linea;
		         while((linea=br.readLine())!=null)
		            diccionario.add(linea);
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }finally{
		          // En el finally cerramos el fichero, para asegurarnos
		          // que se cierra tanto si todo va bien como si salta 
		          // una excepcion.
		          try{                    
		             if( null != fr ){   
		                fr.close();     
		             }                  
		          }catch (Exception e2){ 
		             e2.printStackTrace();
		          }
		       }
		      return diccionario;
		}
		
		//Metodo 2
		//Metodo que imprime el diccionario recogido desde un fichero
		public static void imprimirDiccionario(List<String> diccionario){
			int i=0;
			for(String d: diccionario){
				System.out.println(i+": "+d);
				i++;
			}
		}
		
		//Metodo 3
		//Metodo que guarda las palabras de una lista con un tama�o determinado por un entero a elegir
		public static List<String> cribarDiccionario(int n,List<String> diccionario){
			List<String> diccionarioCribado =  new ArrayList<String>();
			for(String d: diccionario){
				if(d.length() == n){
					diccionarioCribado.add(d);	
				}
			}
			return diccionarioCribado;
		}
		
		//Metodo 4
		//Metodo que recoge el numero de letras no coincidentes en dos palabras
		public static int distanciaCadenas(String c1,String c2){
			int distancia = 0;
			for(int i=0;i<c1.length();i++){
				if(c1.charAt(i) != c2.charAt(i))
					distancia++;
			}
			return distancia;
		}
		
		//Metodo 5
		//Metodo que crea la matriz de adyacencia de un diccionario para usar Dijsktra
		public static int[][] matrizAdy(List<String> diccionario){
			int[][] matriz = new int[diccionario.size()][diccionario.size()];
			for(int i=0;i<matriz.length;i++){
				for(int j=0;j<matriz.length;j++){
					if(i == j)
						matriz[i][j]= 0;
					else{	
							if(distanciaCadenas(diccionario.get(i), diccionario.get(j)) == 1)
								matriz[i][j] = distanciaCadenas(diccionario.get(i), diccionario.get(j));
							else
								matriz[i][j] = 1000;
					}
				}
			}
			return matriz;
		}
		
		//Metodo 6
		//Metodo que recoge el la posicion en la que esta una palabra. Ej: Abitar --> Posicion 3.
		public static int buscarPosicionPalabra(String c1,List<String> diccionario){
			int posicion = 0;
			for(int i=0;i<diccionario.size();i++){
				if(diccionario.get(i).equals(c1))
					posicion = i;
			}
			return posicion;
		}		
		
		
		//METODOS PARA DIJKSTRA
		//Metodo 7
		//Metodo que recoge la posicion en la que se situa el minimo de un vector
		public static int minimo(int[] D,boolean[] ocupado){
			int maximo = 100;
			int v = 1;
			for(int i=0;i<D.length;i++){
				if(!ocupado[i] && (D[i] <= maximo)){
					maximo = D[i];
					v = i;
				}
			}
			return v;
		}
		
		//Metodo 8
		//Metodo que recoge el coste minimo en una matriz de adyacencia para usar Dijsktra
		public static void imprimirCosteMinimo(int[][] matriz,int[] D,List<String> dicc,String cadenaDestino){
			for(int i=0;i<matriz.length;i++){
				if(cadenaDestino.equals(dicc.get(i)))
					if(D[i] != 1000)
						System.out.println("Coste minimo a "+dicc.get(i)+" = "+D[i]);
					else
						System.out.println("NO HAY CAMINO");
			}
		}
		
		//Metodo 9
		//Metodo que imprime el camino minimo hallado anteriormente entre dos nodos
		public static void imprimirCamino(int nodOrigen,int nodoDestino,int[] P,List<String> dicc){
			int nodo = nodoDestino;
			FileWriter fichero = null;
	        PrintWriter pw = null;
	        ArrayList<String> camino =  new ArrayList<String>();
	        
	        try
	        {	
	        	//Creamos el fichero
	            fichero = new FileWriter("salida_p3_juangon_oscfern.txt");
	            pw = new PrintWriter(fichero);
	            
	            //Escritura del fichero
	            System.out.println("Algoritmo FINALIZADO.. Compruebe el fichero txt..");
	            //System.out.println("Camino de "+dicc.get(nodOrigen)+" a "+dicc.get(nodo)+":");
	            while (nodo != nodOrigen){
	            	//System.out.print("- "+dicc.get(nodo)+" ");
	            	camino.add(dicc.get(nodo).toUpperCase()); //Metodo toUpperCase --> Letras Mayusculas
	            	//pw.println("- "+dicc.get(nodo)+" ");
	            	nodo = P[nodo];
	            }
	            camino.add(dicc.get(nodOrigen).toUpperCase());  //Metodo toUpperCase --> Letras Mayusculas
	            //pw.println("- "+dicc.get(nodOrigen));
	            Collections.reverse(camino);
	            pw.println(camino);
	            //System.out.print("- "+dicc.get(nodOrigen));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	           // Aprovechamos el finally para 
	           // asegurarnos que se cierra el fichero.
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
			
		}
		
		//Metodo 10
		//Metodo que lee de un determinado fichero la cadena que aparezca en la linea que solicitamos
		public static String leerCadena(String cadena,int numLinea){
			File archivo = null;
		    FileReader fr = null;
		    BufferedReader br = null;
		    String[] cadenas = new String[3];
		    
		      try {
		         // Apertura del fichero y creacion de BufferedReader para poder
		         // hacer una lectura comoda (disponer del metodo readLine()).
		         archivo = new File ("entrada.txt");
		         fr = new FileReader (archivo);
		         br = new BufferedReader(fr);
		         
		         // Lectura del fichero
		         String linea;
		         int i= 0;
		         while((linea=br.readLine())!=null){
		            cadenas[i] = linea;
		            i++;
		         }
		         cadena = cadenas[numLinea];
		         
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }finally{
		          // En el finally cerramos el fichero, para asegurarnos
		          // que se cierra tanto si todo va bien como si salta 
		          // una excepcion.
		          try{                    
		             if( null != fr ){   
		                fr.close();     
		             }                  
		          }catch (Exception e2){ 
		             e2.printStackTrace();
		          }
		       }
		       return cadena.toLowerCase();
		}
		
		//Metodo 11
		//Metodo que simula el algoritmo de Dijkstra
		public static void dijkstra(int[][] matriz,String cadenaOrigen,String cadenaDestino,List<String> dicc){
			int[] D = new int[matriz.length];
			int[] P = new int[matriz.length];
			boolean[] ocupado = new boolean[matriz.length];
			int nodOrigen = buscarPosicionPalabra(cadenaOrigen, dicc);
			int nodoDestino = buscarPosicionPalabra(cadenaDestino, dicc);
			
			//Inicializacion vectores
			for(int i=0;i<matriz.length;i++){
				ocupado[i] = false;
				D[i] = matriz[nodOrigen][i];
				P[i] = nodOrigen;
			}
			ocupado[nodOrigen] = true;
			D[nodOrigen] = 0;
			
			//Algoritmo Dijkstra
			for(int i = 0; i<matriz.length;i++){
				int minimo = minimo(D,ocupado);//
				ocupado[minimo] = true;
				for(int j=0;j<matriz.length;j++){
					if(!ocupado[j]){
						if((D[minimo]+matriz[minimo][j] < D[j])){
							D[j] = D[minimo]+matriz[minimo][j];
							P[j] = minimo;
						}
					}
				}
			}
			
			imprimirCosteMinimo(matriz, D,dicc,cadenaDestino);
			imprimirCamino(nodOrigen, nodoDestino, P,dicc);
		
		}
		
		//Programa principal para ejecutar el algoritmo de Dijkstra
		public static void main(String[] args) {	
			String palabraOrigen = "";
			String palabraDestino = "";
			
			palabraOrigen = leerCadena(palabraOrigen, 0);
			palabraDestino = leerCadena(palabraDestino, 1);
			
			List<String> diccionario = crearDiccionario();
			List<String> diccionariocribado = cribarDiccionario(palabraOrigen.length(), diccionario);
			int[][] matrizAdy = matrizAdy(diccionariocribado);
			dijkstra(matrizAdy,palabraOrigen,palabraDestino,diccionariocribado);
			
		}
}//FIN
