package asistente_virtual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF06Tests {

	public final static String USUARIO = "delucas";

	Bot jenkins;

	@Before
	public void setup() {
		jenkins = new Bot("jenkins");
	}

	@Test
	public void calculos() {
		Assert.assertEquals(
				"@delucas 3",
				jenkins.leerMensaje("@jenkins cuánto es 1 + 2")
			);
		
		/* Comento el resto porque necesitamos el m�todo que realiza los c�lculos (todas estas preguntas
		 * entran por el mismo IF)
		 */
		
		
		Assert.assertEquals(
				"@delucas 1",
				jenkins.leerMensaje("@jenkins cuánto es 5 - 2 * 2")
			);
		/*
		Assert.assertEquals(
				"@delucas 10",
				jenkins.leerMensaje("@jenkins cuánto es el 10% de 100")
			);
		*/
		Assert.assertEquals(
				"@delucas 42",
				jenkins.leerMensaje("@jenkins cuánto es el 17 + 5 ^ 2")
			);
		
		// agregar otros casos
	}
	
	@Test
	public void calculosCompuestos() {
		Assert.assertEquals(
				"@delucas -6",
				jenkins.leerMensaje("@jenkins cuánto es (4-8)*2 + 4 / ( 1 + 1)")
			);
		
		// agregar otros casos
	}
	
}