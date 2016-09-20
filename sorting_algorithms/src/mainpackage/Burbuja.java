package mainpackage;

public class Burbuja implements AlgoritmoOrdenacion{

	private long asignaciones;
	private long comparaciones;
	
	public Burbuja() {
		asignaciones = 0;
		comparaciones = 0;
	}
	
	@Override
	public int[] ordenar(int[] vector) {
		asignaciones = 0; comparaciones = 0;
		int pivote;
		
		for(int i=0 ; i<(vector.length-1) ; i++){
			for(int j=0 ; j<(vector.length-i-1) ; j++){
				comparaciones ++;
				if(vector[j] > vector[j+1]){ //COMPARACION
					asignaciones ++;
					pivote = vector[j]; //ASIGNACION
					asignaciones ++;
					vector[j] = vector[j+1]; //ASIGNACION
					asignaciones ++;
					vector[j+1] = pivote; //ASIGNACION
				}
			}
		}
		return vector;
	}
	
	@Override
	public long getAsignaciones() {
		return asignaciones;
	}
	
	@Override
	public long getComparaciones() {
		return comparaciones;
	}

	@Override
	public String getName() {
		return "burbuja";
	}

}
