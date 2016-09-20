package mainpackage;

public class Seleccion implements AlgoritmoOrdenacion{

	private long asignaciones;
	private long comparaciones;

	public Seleccion() {
		asignaciones = 0;
		comparaciones = 0;
	}
	
	@Override
	public int[] ordenar(int[] vector) {
		asignaciones = 0; comparaciones = 0;
		int minimo;
		int index = 0;
		int temp;
		
		for (int i = 0; i<vector.length ; i++){
			minimo = index;
			for (int j=index; j<vector.length; j++){
				comparaciones ++;
				if(vector[j] < vector[minimo]){ //COMPARACION
					minimo = j;
				}
			}
			asignaciones ++;
			temp = vector[index]; //ASIGNACION
			asignaciones ++;
			vector[index] = vector[minimo]; //ASIGNACION
			asignaciones ++;
			vector[minimo] = temp; //ASIGNACION
			index ++;
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
		return "seleccion";
	}

}
