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
					"@delucas son las 01:49 a. m.",
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
					"@delucas hoy es 5 de mayo de 2018",
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
					"@delucas hoy es sábado",
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
				"@delucas será el lunes 7 de mayo de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 días?")
			);
		
		Assert.assertEquals(
				"@delucas será el jueves 5 de julio de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas será el martes 5 de mayo de 2020",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 años?")
			);
	}
	
	@Test
	public void diaHace() {
		Assert.assertEquals(
				"@delucas fue viernes 4 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue ayer?")
			);
		
		Assert.assertEquals(
				"@delucas fue miércoles 2 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 3 dias?")
			);
		
		Assert.assertEquals(
				"@delucas fue lunes 5 de marzo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas fue jueves 5 de mayo de 2016",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 años?")
			);
	}
	
	
	@Test
	public void tiempoDesde() {
		Assert.assertEquals(
				"@delucas entre el 5 de mayo de 2017 y el 5 de mayo de 2018 pasaron 365 días",
				jenkins.leerMensaje("@jenkins cuántos días pasaron desde el 5 de mayo de 2017?")
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void tiempoHasta() {
		Assert.assertEquals(
				"@delucas faltan 1 días",
				jenkins.leerMensaje("@jenkins cuántos días faltan para el 6 de mayo?")
			);
		
		// agregar casos de prueba
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
	
	@Test
	public void magnitudInvalida() {
		Assert.assertEquals(
				Bot.MSG_NO_ENTIENDO,
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 rama")
			);
	}
	
	@Test
	public void unidadesDeMasa() {
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo")
			);

		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo?")
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo ?")
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo   ?")
			);
		
		Assert.assertEquals(
				"@delucas 2,00 kilos equivale a 2000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 2 kilos")
			);
		
		Assert.assertEquals(
				"@delucas 1000,00 gramos equivale a 1,00 kilos",
				jenkins.leerMensaje("@jenkins cuántos kilos son 1000 gramos")
			);
		
		Assert.assertEquals(
				"@delucas 1000,00 gramos equivale a 35,27 onzas",
				jenkins.leerMensaje("@jenkins cuántas onzas son 1000 gramos")
			);
		
		Assert.assertEquals(
				"@delucas 4000000,00 gramos equivale a 4,00 toneladas",
				jenkins.leerMensaje("@jenkins cuántas toneladas son 4000000 gramos")
			);
		
		Assert.assertEquals(
				"@delucas 1,70 toneladas equivale a 1700,00 kilos",
				jenkins.leerMensaje("@jenkins cuántos kilos son 1.7 toneladas")
			);
	}

	@Test
	public void unidadesDeTiempo() {
		Assert.assertEquals(
				"@delucas 60,00 segundos equivale a 1,00 minutos",
				jenkins.leerMensaje("@jenkins cuántos minutos hay en 60 segundos")
			);

		Assert.assertEquals(
				"@delucas 3,50 horas equivale a 12600,00 segundos",
				jenkins.leerMensaje("@jenkins cuántos segundos hay en 3.5 horas?")
			);
		
	}
	
	@Test
	public void unidadesDeCapacidad() {
		Assert.assertEquals(
				"@delucas 3500,00 cm3 equivale a 3,50 litros",
				jenkins.leerMensaje("@jenkins cuántos litros hay en 3500 cm3")
			);

		Assert.assertEquals(
				"@delucas 10,00 galones equivale a 45,46 litros",
				jenkins.leerMensaje("@jenkins cuántos litros son 10 galones?")
			);
		
	}
	
	@Test
	public void unidadesDeLongitud() {
		Assert.assertEquals(
				"@delucas 1000,00 metros equivale a 1,00 kilometros",
				jenkins.leerMensaje("@jenkins cuántos kilometros hay en 1000 metros")
			);

		Assert.assertEquals(
				"@delucas 1,00 pulgada equivale a 2,54 centimetros",
				jenkins.leerMensaje("@jenkins cuántos centímetros son 1 pulgada?")
			);
		
	}
	
}
