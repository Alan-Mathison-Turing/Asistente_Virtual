package edu.unlam.asistente.clima;

import org.junit.Before;
import org.junit.Test;

public class ClimaTest {
	private Clima clima;

	@Before
	public void setup() {
		clima = new Clima("Buenos Aires");
	}

	@Test
	public void testObtenerLluvia() {
		System.out.println(clima.obtenerDiaLluvioso());
	}

	@Test
	public void testObtenerClima() {
		System.out.println(clima.obtenerClimaActual());
	}

	// @Test
	// public void test() {
	// System.out.println(clima.obtenerHora());
	// }
}
