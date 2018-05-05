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
	Bot jenkins;
	
	@Before
	public void instanciarBot() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void mensajeInentendible() {
		String rta = jenkins.leerMensaje("audwhkawud");
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}
	
	@Test
	public void mensajeVacio() {
		String rta = jenkins.leerMensaje("");
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}
	
	@Test
	public void hora() {
		String[] mensajes = {
				"¿qué hora es, @jenkins?",
				"@bot, la hora por favor",
				"me decís la hora @jenkins?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas son las 3:15 PM",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
	@Test
	public void fecha() {
		String[] mensajes = {
				"¿qué día es, @jenkins?",
				"@bot, la fecha por favor",
				"me decís la fecha @jenkins?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas hoy es 1 de abril de 2018",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
	@Test
	public void diaDeLaSemana() {
		String[] mensajes = {
				"¿qué día de la semana es hoy, @jenkins?"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas hoy es domingo",
					jenkins.leerMensaje(mensaje)
			);
		}
	}
	
	@Test
	public void sinsentido() {
		String[] mensajes = {
				"Este mensaje no tiene sentido @jenkins"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Disculpa... no entiendo el pedido, @delucas ¿podrás repetirlo?",
					jenkins.leerMensaje(mensaje)
			);
		}
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
	
	@Test
	public void diaDentroDe() {
		Assert.assertEquals(
				"@delucas será el domingo 6 de mayo de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 días?")
			);
		
		Assert.assertEquals(
				"@delucas será el miércoles 4 de julio de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas será el lunes 4 de mayo de 2020",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 años?")
			);
	}
	
	@Test
	public void diaHace() {
		Assert.assertEquals(
				"@delucas fue jueves 3 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue ayer?")
			);
		
		Assert.assertEquals(
				"@delucas fue martes 1 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 3 dias?")
			);
		
		Assert.assertEquals(
				"@delucas fue domingo 4 de marzo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas fue miércoles 4 de mayo de 2016",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 años?")
			);
	}
	
	
	@Test
	public void tiempoDesde() {
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 365 días",
				jenkins.leerMensaje("@jenkins cuántos días pasaron desde el 1 de abril de 2017?")
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void tiempoHasta() {
		Assert.assertEquals(
				"@delucas faltan 2 días",
				jenkins.leerMensaje("@jenkins cuántos días faltan para el 6 de mayo?")
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void formatearFechaViewDosDigitosTest() {
		String fechaRetornada = jenkins.formatearFechaView("14/05/2018");
		Assert.assertEquals("lunes 14 de mayo de 2018", fechaRetornada);
	}
	
	@Test
	public void formatearFechaViewUnDigitoTest() {
		String fechaRetornada = jenkins.formatearFechaView("4/5/2018");
		Assert.assertEquals("viernes 4 de mayo de 2018", fechaRetornada);
	}
	
	@Test
	public void fechaDentroDeTest() {
		String fechaRetornada = jenkins.fechaDentroDe(4, "meses");
		Assert.assertEquals("viernes 4 de mayo de 2018", fechaRetornada);
	}
	
	@Test
	public void tiempoDesdeHastaEnMesesTest() {
		int fechaRetornada = jenkins.tiempoDesdeHasta("4/5/2018", "5/6/2019", "meses");
		Assert.assertEquals(13, fechaRetornada);
	}
	
	@Test
	public void tiempoDesdeHastaEnDiasTest() {
		int fechaRetornada = jenkins.tiempoDesdeHasta("4/5/2018", "5/6/2019", "dias");
		Assert.assertEquals(397, fechaRetornada);
	}
	
	@Test
	public void tiempoDesdeHastaEnAniosTest() {
		int fechaRetornada = jenkins.tiempoDesdeHasta("4/5/2018", "5/6/2019", "anios");
		Assert.assertEquals(1, fechaRetornada);
	}
	
	@Test
	public void calculos() {
		Assert.assertEquals(
				"@delucas 3",
				jenkins.leerMensaje("@jenkins cuánto es 1 + 2")
			);
		
		
		Assert.assertEquals(
				"@delucas 1",
				jenkins.leerMensaje("@jenkins cuánto es 5 - 2 * 2")
			);
		
		Assert.assertEquals(
				"@delucas 10",
				jenkins.leerMensaje("@jenkins cuánto es el 10% de 100")
			);
		
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
