package distanciaEdicion;

import java.util.ArrayList;
import java.util.Collections;

public class EdicionDinamica extends AlgoritmoEdicion{
	
	private int distancia;
	
	private int asignaciones;
	private int comparaciones;
	
	public EdicionDinamica() {
		distancia = 0;
	}

	/** 
	 * Calcula cual de los 3 parametros que recibe es el menor.
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private int distanciaMinima(int a, int b, int c){
		if(a <= b && a <= c){
			return a;
		}
		if(b <= a && b <= c){
			return b;
		}
		return c;
	}
	
	private void rellenarMatriz(int[][]matriz,int colMat,int filMat){
		for(int i=0; i<=colMat; i++){
			asignaciones++;
			matriz[i][0] = i;
		}
		for(int j=0; j<=filMat; j++){
			asignaciones++;
			matriz[0][j] = j;
		}
	}
	
	private void solucionUnica(ArrayList<String> solucion,int[][]m,int filMat,int colMat,String str1,String str2){
		
		int filMatriz = str1.length();
		int colMatriz = str2.length();
		while ((filMatriz != 0) && (colMatriz != 0)) {
	            int min = Math.min(Math.min(m[filMatriz-1][colMatriz-1], m[filMatriz-1][colMatriz]), m[filMatriz][colMatriz-1]);
	            comparaciones++;
	            if (min == m[filMatriz-1][colMatriz-1]) {
	            	comparaciones++;
	                if (m[filMatriz-1][colMatriz-1]==m[filMatriz][colMatriz]){
	                	copy(solucion,str2.charAt(colMatriz-1));
	                }
	                else
	                	swap(solucion,str1.charAt(filMatriz-1), str2.charAt(colMatriz-1));
	                //Decrementamos 
	                filMatriz--;
	                colMatriz--;
	            } else {
	            	comparaciones++;
	            	if (min == m[filMatriz-1][colMatriz]) {
		            	comparaciones++;
		            	delete(solucion,str1.charAt(filMatriz-1));
		                filMatriz--;
	            	} else {
		            	comparaciones++;
		            	if (min == m[filMatriz][colMatriz-1]) {
			            	comparaciones++;
			            	insert(solucion,str2.charAt(colMatriz-1));
			                colMatriz--;
	            		}
	            	} 	}
	            if((filMatriz == 0) && (colMatriz == 1))
	            	delete(solucion,str2.charAt(colMatriz-1));
	            if((filMatriz == 1) && (colMatriz == 0))
	            	insert(solucion,str1.charAt(filMatriz-1));
	        }
		solucion.add("Operaciones:\n");
		solucion.add("Comparaciones: " + comparaciones
				+ " Asignaciones: " + asignaciones + "\n");
		Collections.reverse(solucion);
		st.append(solToString(solucion));
	}
	
	private void buscarSoluciones(int distancia, ArrayList<String> sol, int[][] matriz,int fil,int col,String str1,String str2){
		ArrayList<String> solucion = (ArrayList<String>) sol.clone();
		if(fil==0 || col ==0){
			if ((fil == 0) && (col == 1)) {
				delete(solucion, str2.charAt(col - 1));
				distancia++;
			}
			if ((fil == 1) && (col == 0)) {
				insert(solucion, str1.charAt(fil - 1));
				distancia++;
			}
			if(distancia == this.distancia){
				solucion.add("Operaciones:\n");
				solucion.add("Comparaciones: " + comparaciones
						+ " Asignaciones: " + asignaciones + "\n");
				Collections.reverse(solucion);
				st.append(solToString(solucion));
			}
		} else{
			int min = Math.min(Math.min(matriz[fil - 1][col - 1],
					matriz[fil - 1][col]), matriz[fil][col - 1]);
			comparaciones++;
			if ((min == matriz[fil - 1][col - 1]) && (fil>0) && (col>0)) {
				comparaciones++;
				if (matriz[fil - 1][col - 1] == matriz[fil][col]) {
					copy(solucion, str2.charAt(col - 1));
					buscarSoluciones(distancia, solucion, matriz, fil - 1,
							col - 1, str1, str2);
				} else {
					swap(solucion, str1.charAt(fil - 1),
							str2.charAt(col - 1));
					buscarSoluciones(distancia + 1, solucion, matriz, fil - 1,
							col - 1, str1, str2);
				}
				solucion.remove(solucion.size()-1);
			}
			comparaciones++;
			if ((min == matriz[fil - 1][col]) && (fil>0) && (col>0)) {
				delete(solucion, str1.charAt(fil - 1));
				buscarSoluciones(distancia + 1, solucion, matriz, fil - 1, col,
						str1, str2);
				solucion.remove(solucion.size()-1);
			}
			comparaciones++;
			if ((min == matriz[fil][col - 1]) && (fil>0) && (col>0)) {
				insert(solucion, str2.charAt(col - 1));
				buscarSoluciones(distancia + 1, solucion, matriz, fil, col - 1,
						str1, str2);
				solucion.remove(solucion.size()-1);
			}
		}
	}
	
	/**
	 * Rellena una matriz de tama�o str1.length X str2.length y
	 * posteriormente la recorrer hacia atr�s para obtener los 
	 * pasos realizados y guardarlos en un ArrayList.
	 */
	@Override
	public String fase1(String str1, String str2) {
		
		init();
		
		int[][] m = new int[str1.length()+1][str2.length()+1];
		rellenarMatriz(m,str1.length(),str2.length());
		for(int i = 1; i <= str1.length(); i++) {
			comparaciones++;
			for(int j = 1; j <= str2.length(); j++) {
				comparaciones++;
				asignaciones++;
				m[i][j]= distanciaMinima(m[i-1][j]+1, m[i][j-1]+1, m[i-1][j-1] + ((str1.charAt(i-1) == str2.charAt(j-1)) ? 0 : 1));	
			}
		}
		distancia = m[str1.length()][str2.length()];
		
		writeStrings(str1, str2);
		setDistance(distancia);
		ArrayList<String> sol = new ArrayList<>();
		solucionUnica(sol, m, str1.length(), str2.length(), str1, str2);
		
	    return toString();
	}

	@Override
	public String fase2(String str1, String str2) {

		init();
		
		int[][] m = new int[str1.length()+1][str2.length()+1];
		rellenarMatriz(m,str1.length(),str2.length());
		for(int i = 1; i <= str1.length(); i++) {
			comparaciones++;
			for(int j = 1; j <= str2.length(); j++) {
				comparaciones++;
				asignaciones++;
				m[i][j]= distanciaMinima(m[i-1][j]+1, m[i][j-1]+1, m[i-1][j-1] + ((str1.charAt(i-1) == str2.charAt(j-1)) ? 0 : 1));	
			}
		}
		distancia = m[str1.length()][str2.length()];
		
		writeStrings(str1, str2);
		setDistance(distancia);
		ArrayList<String> sol = new ArrayList<>();
		buscarSoluciones(0, sol, m, str1.length(), str2.length(), str1, str2);
		
	    return toString();
	}

}
