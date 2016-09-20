package mainpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Launcher {

	private static String nombreFichero = "output.txt";
	private static File archivo;
	private static BufferedWriter output;
	
	public static void main(String[] args) {
		int eleccion = 0;
		int minimo,maximo;
		Scanner scanner = new Scanner (System.in);
		
		//PETICION DE ALGORITMO AL USUARIO
		while((eleccion < 1) || (eleccion > 6)){
			System.out.println("Elija un algoritmo de ordenacion (1-4):");
			System.out.println("1.- Insercion");
			System.out.println("2.- Seleccion");
			System.out.println("3.- Burbuja");
			System.out.println("4.- QuickSort");
			System.out.println("5.- QuickSort Eficiente");
			System.out.println("6.- EJECUTAR TODOS");
			while (!scanner.hasNextInt()) scanner.next();
			eleccion = scanner.nextInt();
			System.out.println();
		}
		
		//PETICION DE RANGO DE TAMANHO DE LOS VECTORES AL USUARIO
		System.out.print("Indique el tamanho MINIMO de los vectores: ");
		while (!scanner.hasNextInt()) scanner.next();
    	minimo = scanner.nextInt();
		System.out.printf("Indique el tamanho MAXIMO de los vectores: ",minimo*minimo);
		do {
	        while (!scanner.hasNextInt()) scanner.next();
	        maximo = scanner.nextInt();
	    } while (maximo < minimo);
	    scanner.close();
	    crearFichero();
	    
	    if(eleccion != 6){
	    	Vector v = new Vector(eleccion,output);
	    	v.crearVectores(minimo,maximo);
	    }else
	    	executeAll(minimo,maximo);
		
		String ruta = null;
		try {
			output.close();
			ruta = archivo.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(ruta != null)
			System.out.print("\n\n\tAlgoritmo finalizado. Puede ver los resultados en el archivo: '"+ruta+"'");
	}
	
	public static void crearFichero(){
		try {
			archivo = new File(nombreFichero);
			if(archivo.exists()){
				output = new BufferedWriter(new FileWriter(archivo, true));
			} else{
				output = new BufferedWriter(new FileWriter(archivo));
				output.write("Tamanho\tTiempo\tAsignaciones\tComparaciones\tAlgoritmo\n");
			}				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void executeAll(int min, int max){
		for(int i=1;i<5;i++){
			Vector v = new Vector(i, output);
			v.crearVectores(min, max);
		}
	}

}
