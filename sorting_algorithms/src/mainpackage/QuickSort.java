package mainpackage;

public class QuickSort implements AlgoritmoOrdenacion{

	private long asignaciones;
	private long comparaciones;
	
	public QuickSort() {
		asignaciones = 0;
		comparaciones = 0;
	}
	
	@Override
	public int[] ordenar(int[] vector) {
		asignaciones = 0; comparaciones = 0;
		int indiceSuperior = vector.length-1;
		quicksort(vector, 0, indiceSuperior);
		return vector;
	}
	
	private void quicksort(int[] vect, int inf, int sup){
		
		int pivote, i, j, temp;
		
		//Asignacion del pivote mas eficiente.
		pivote = vect[inf+(sup-inf)/2]; //ASIGNACION(no significativa)
		
		i=inf;
		j=sup;
		
		while(i <= j){
			comparaciones ++;
			while(vect[i] < pivote){ //BUCLE DE COMPARACION
				i++;
				comparaciones ++;
			}
			comparaciones ++;
			while(vect[j] > pivote){ //BUCLE DE COMPARACION
				j--;
				comparaciones ++;
			}
			
			if(i <= j){
				asignaciones ++;
				temp = vect[i]; //ASIGNACION
				asignaciones ++;
				vect[i] = vect[j]; //ASIGNACION
				asignaciones ++;
				vect[j] = temp; //ASIGNACION
				i++;
				j--;
			}
		}
		
		//Recursion
		if(inf<j)
			quicksort(vect, inf, j);
		if(sup > i)
			quicksort(vect, i, sup);

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
		return "quicksort";
	}

}
