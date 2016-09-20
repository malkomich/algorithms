package mainpackage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class Vector {
	
	private final int numVectores = 100;
	private AlgoritmoOrdenacion algoritmo;
	private BufferedWriter salida;
	
	public Vector(int tipoAlgoritmo, BufferedWriter salida){
		switch (tipoAlgoritmo) {
		case 1:
			algoritmo = new Insercion();
			break;
		case 2:
			algoritmo = new Seleccion();
			break;
		case 3:
			algoritmo = new Burbuja();
			break;
		case 4:
			algoritmo = new QuickSort();
			break;
		case 5:
			algoritmo = new QuickSortEficiente();
			break;
		default:
			break;
		}
		this.salida = salida;
	}
	
	public int[] llenarVector(int[] v ){
		int i;
		Random numAleatorio = new Random();
		
		for(i=0 ; i<v.length ; i++){
			v[i] = numAleatorio.nextInt(100);
		}
		return v;
	}
	
	public void crearVectores(int min, int max){
		int i,j;
		long tiempo_temp;
		float tiempoTotal;
		int incremento;
		
		if(max>10000)
			incremento = (max-min)/10 + 1;
		else
			incremento = (max-min)/1000 + 1;
		
		for(i=min ; i<=max ; i+=incremento){
			tiempoTotal = 0;
			for(j=0 ; j<numVectores ; j++){
				int[] vector = llenarVector(new int[i]);
				tiempo_temp = System.nanoTime();
				vector = ordenarVector(vector);
				tiempoTotal += (System.nanoTime() - tiempo_temp);
			}
			try {
				salida.write(i+"\t"+Math.round((tiempoTotal/numVectores)/1000)+"\t"+algoritmo.getAsignaciones()+"\t"+algoritmo.getComparaciones()+"\t"+algoritmo.getName()+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int[] ordenarVector(int[] vector){
		return algoritmo.ordenar(vector);
	}
	
	//Operacion sólo para pruebas(ya que no se debe imprimir resultados desde clases del dominio)
	public static void imprimirVector(int[] vector){
		System.out.print("{");
		for(int i=0;i<vector.length;i++){
			if(i != vector.length-1)
				System.out.print(vector[i]+",");
			else
				System.out.print(vector[i]+"}\n");
		}
	}

}