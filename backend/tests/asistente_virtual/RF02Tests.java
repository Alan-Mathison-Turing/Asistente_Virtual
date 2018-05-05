package asistente_virtual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF02Tests {

	public final static String USUARIO = "delucas"; 
	
	Bot jenkins;
	
	@Before
	public void setup() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void agradecimiento() {
		String[] mensajes = {
				"¡Muchas gracias, @jenkins!",
				"@jenkins gracias",
				"gracias @jenkins"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"No es nada, @delucas",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
}