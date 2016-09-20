package distanciaEdicion;

import java.util.List;

public abstract class AlgoritmoEdicion {

	StringBuilder st;
	
	public AlgoritmoEdicion(){
		st = new StringBuilder();
	}
	
	public abstract String fase1(String initString, String finalString);
	
	public abstract String fase2(String initString, String finalString);
	
	protected void writeStrings(String s1, String s2){
		st.append(s1+"\n");
		st.append(s2+"\n");
	}
	
	protected void setDistance(int d){
		st.append("Distancia = " + d + "\n");
	}
	
	protected void insert(List<String> solucion, char c){
		solucion.add("- Insert " + c + "\n");
	}
	
	protected void copy(List<String> solucion, char c){
		solucion.add("- Copiar " + c + "\n");
	}
	
	protected void delete(List<String> solucion, char c){
		solucion.add("- Borrar " + c + "\n");
	}
	
	protected void swap(List<String> solucion, char c1, char c2){
		solucion.add("- Sustituir " + c1 + " por " + c2 + "\n");
	}
	
	protected void init(){
		st = new StringBuilder();
	}
	
	public String solToString(List<String> solucion){
		StringBuilder st = new StringBuilder();
		for(String s:solucion)
	    	st.append(s);
	    return st.toString();
	}
	
	@Override
	public String toString(){
		return st.toString();
	}
}
