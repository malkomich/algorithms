package distanciaEdicion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Launcher {
	
	public static void main(String[] args) {
		AlgoritmoEdicion algoritmo = new EdicionDinamica();
		FileInputStream fileInput = null;
		FileWriter fileOutput = null;
		try {
			fileInput = new FileInputStream(new File("entrada.txt"));
			fileOutput = new FileWriter(new File("salida.txt"));
			InputStreamReader is = new InputStreamReader(fileInput);
			BufferedReader reader = new BufferedReader(is);
			PrintWriter writer = new PrintWriter(fileOutput);
			fase2(reader, writer, algoritmo); // Elegimos la fase de la practica a ejecutar.
			System.out.println("\tAlgoritmo finalizado.");
			
		} catch (FileNotFoundException e) {
			System.err.println("El archivo no existe.");
		} catch (IOException e) {
			System.err.println("Error leyendo el archivo.");
		} finally{
			try {
				fileInput.close();
				fileOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void fase1(BufferedReader reader, PrintWriter writer, AlgoritmoEdicion algoritmo) throws IOException{
		String initString;
		while((initString=reader.readLine()) != null){
			String finalString = reader.readLine();
			String solucion = algoritmo.fase1(initString,finalString);
			writer.write(solucion+"\n");
			reader.readLine(); // Para omitir el salto de linea.
		}
	}
	
	public static void fase2(BufferedReader reader, PrintWriter writer, AlgoritmoEdicion algoritmo) throws IOException{
		String initString;
		while((initString=reader.readLine()) != null){
			String finalString = reader.readLine();
			String solucion = algoritmo.fase1(initString,finalString);
			writer.write(solucion+"\n");
			reader.readLine(); // Para omitir el salto de linea.
		}
	}

}
