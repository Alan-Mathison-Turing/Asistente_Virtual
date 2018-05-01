package asistente_virtual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BotTests {
	
	public final static String USUARIO = "delucas";
	public final static Date FECHA_HORA = new GregorianCalendar(2018, 3, 1, 15, 15, 0).getTime();
	Bot bot;
	
	@Before
	public void InstanciarBot() {
		bot = new Bot();
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

	
	@Test
	public void hora() {
		String[] mensajes = {
				"¿qué hora es, @bot?",
				"@bot, la hora por favor",
				"me decís la hora @bot?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas son las 3:15 PM",
					bot.leerMensaje(mensaje)
			);
		}
	}
	
	@Test
	public void fecha() {
		String[] mensajes = {
				"¿qué día es, @bot?",
				"@bot, la fecha por favor",
				"me decís la fecha @bot?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas hoy es 1 de abril de 2018",
					bot.leerMensaje(mensaje)
			);
		}
	}
	
	@Test
	public void diaDeLaSemana() {
		String[] mensajes = {
				"¿qué día de la semana es hoy, @bot?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas hoy es domingo",
					bot.leerMensaje(mensaje)
			);
		}
	}
	
}
