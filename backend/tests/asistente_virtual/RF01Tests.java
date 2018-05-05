package asistente_virtual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF01Tests {

	public final static String USUARIO = "delucas"; 
	
	Bot jenkins;
	
	@Before
	public void setup() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void saludo() {
		String[] mensajes = {
				"¡Hola, @jenkins!",
				"@jenkins hola!",
				"buen día @jenkins",
				"@jenkins, buenas tardes",
				"hey @jenkins"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"¡Hola, @delucas!",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
}