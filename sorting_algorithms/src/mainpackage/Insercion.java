package mainpackage;

public class Insercion implements AlgoritmoOrdenacion{

	private long asignaciones;
	private long comparaciones;

	public Insercion() {
		asignaciones = 0;
		comparaciones = 0;
	}
	
	@Override
	public int[] ordenar(int[] vector) {
		asignaciones = 0; comparaciones = 0;
		int pivote;
		
		for(int i=1 ; i<(vector.length) ; i++){
			int j = i-1;
			asignaciones ++;
			pivote = vector[i]; //ASIGNACION
			if(j>=0){
				comparaciones++;
				while(j >= 0 && pivote < vector[j]){ //BUCLE DE COMPARACION
					asignaciones ++;
					vector[j+1] = vector[j]; //ASIGNACION
					asignaciones ++;
					vector[j] = pivote; //ASIGNACION
					j--;
					/* Si la primera condicion no se cumple, no realizara la comprobacion del vector. */
					if(j>=0)
						comparaciones++;
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
		return "insercion";
	}
	
}
