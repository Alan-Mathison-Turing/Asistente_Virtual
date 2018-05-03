package asistente_virtual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF00Tests {

	public final static String USUARIO = "delucas"; 
	
	Bot jenkins;
	
	@Before
	public void setup() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void sinsentido() {
		String[] mensajes = {
				"Este mensaje no tiene sentido @jenkins"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Disculpa... no entiendo el pedido, @delucas ¿podrías repetirlo?",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
}