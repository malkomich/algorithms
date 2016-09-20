package test;



import org.junit.Ignore;
import org.junit.Test;

import distanciaEdicion.AlgoritmoEdicion;
import distanciaEdicion.EdicionDinamica;

public class Prueba {

	@Test
	@Ignore
	public void fase1() {
		AlgoritmoEdicion algoritmo = new EdicionDinamica();
		String result = algoritmo.fase1("integer", "entero");
		System.out.println(result);
	}
	
	@Test
	public void fase2() {
		AlgoritmoEdicion algoritmo = new EdicionDinamica();
		String result = algoritmo.fase2("plasma", "altruismo");
		System.out.println(result);
	}

}
