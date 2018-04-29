package asistente_virtual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class BotTests {
	
	Bot bot;
	
	@Before
	public void instanciarBot() {
		bot = new Bot("jenkins");
	}
	
	@Test
	public void mensajeInentendible() {
		String rta = bot.leerMensaje("audwhkawud");
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}
	
	@Test
	public void mensajeVacio() {
		String rta = bot.leerMensaje("");
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}

}
