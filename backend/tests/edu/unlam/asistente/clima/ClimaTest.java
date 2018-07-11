package edu.unlam.asistente.clima;

import org.junit.Before;
import org.junit.Test;

public class ClimaTest {
	private Clima clima;

	@Before
	public void setup() {
		clima = new Clima("Erfurt");
	}

	@Test
	public void busquedaExistente() {
		System.out.println(clima.obtenerDiaLluvioso());
	}

	@Test
	public void test() {
		System.out.println(clima.obtenerHora());
	}
}
