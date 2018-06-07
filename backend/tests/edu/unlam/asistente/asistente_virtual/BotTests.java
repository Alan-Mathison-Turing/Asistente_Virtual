package edu.unlam.asistente.asistente_virtual;

import static org.junit.Assert.assertEquals;

import edu.unlam.asistente.asistente_virtual.Bot;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BotTests {
	
	public final static String USUARIO = "delucas";
	//usuario de test para integracion con DB
	public final static String TEST_USER = "testUser";
	
	public final static Date FECHA_HORA = new GregorianCalendar(2018, 3, 1, 15, 15, 0).getTime();
	Bot jenkins;
	
	@Before
	public void instanciarBot() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void mensajeInentendible() {
		String rta = jenkins.leerMensaje("audwhkawud", USUARIO);
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}
	
	@Test
	public void mensajeVacio() {
		String rta = jenkins.leerMensaje("", USUARIO);
		assertEquals(Bot.MSG_NO_ENTIENDO, rta);
	}
	
	@Test
	public void sinsentido() {
		String[] mensajes = {
				"Este mensaje no tiene sentido @jenkins"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Disculpa... no entiendo el pedido, @delucas ¿podrás repetirlo?",
					jenkins.leerMensaje(mensaje, USUARIO)
			);
		}
	}
	
	@Test
	public void saludo() {
		Calendar calendario = Calendar.getInstance();
		String[] mensajes = {
				"¡Hola, @jenkins!",
				"@jenkins hola!",
				"buen día @jenkins",
				"@jenkins, buenas tardes",
				"hey @jenkins"
		};
		for (String mensaje : mensajes) {
			if(calendario.get(Calendar.HOUR_OF_DAY) < 13)
				Assert.assertEquals("Buenos días @" + USUARIO + "!", jenkins.leerMensaje(mensaje, USUARIO));
			if(calendario.get(Calendar.HOUR_OF_DAY) > 19)
				Assert.assertEquals("Buenas noches @" + USUARIO + "!", jenkins.leerMensaje(mensaje, USUARIO));
			if(calendario.get(Calendar.HOUR_OF_DAY) >= 13 && calendario.get(Calendar.HOUR_OF_DAY) <= 19)
				Assert.assertEquals("Buenas tardes @" + USUARIO + "!", jenkins.leerMensaje(mensaje, USUARIO));
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
					"No es nada, @" + Bot.USUARIO,
					jenkins.leerMensaje(mensaje, USUARIO)
			);
		}
	}
	
	@Test
	public void despedida() {
		String[] mensajes = {
				"¡Chau @jenkins!",
				"Adios @jenkins",
				"Hasta luego @jenkins!"
		};
		for (String mensaje : mensajes) {
			Assert.assertEquals(
					"Hasta luego @" + Bot.USUARIO + "!",
					jenkins.leerMensaje(mensaje, USUARIO)
			);
		}
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
					"@delucas son las 20:21 PM",
					jenkins.leerMensaje(mensaje, USUARIO)
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
					jenkins.leerMensaje(mensaje, USUARIO)
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
					jenkins.leerMensaje(mensaje, USUARIO)
			);
		}
	}
	
	@Test
	public void diaDentroDe() {
		Assert.assertEquals(
				"@delucas será el domingo 20 de mayo de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 días?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas será el miércoles 18 de julio de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 meses?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas será el lunes 18 de mayo de 2020",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 años?", USUARIO)
			);
	}
	
	@Test
	public void diaHace() {
		Assert.assertEquals(
				"@delucas fue viernes 4 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue ayer?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas fue miércoles 2 de mayo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 3 dias?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas fue lunes 5 de marzo de 2018",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 meses?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas fue jueves 5 de mayo de 2016",
				jenkins.leerMensaje("@jenkins que dia fue hace 2 años?", USUARIO)
			);
	}
	
	
	@Test
	public void tiempoDesde() {
		Assert.assertEquals(
				"@delucas entre el 5 de mayo de 2018 y el 24 de mayo de 2018 pasaron 19 días",
				jenkins.leerMensaje("@jenkins cuántos días pasaron desde el 5 de mayo de 2018?", USUARIO)
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void tiempoHasta() {
		Assert.assertEquals(
				"@delucas faltan 1 días",
				jenkins.leerMensaje("@jenkins cuántos días faltan para el 6 de mayo?", USUARIO)
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void calculos() {
		Assert.assertEquals(
				"@delucas 3",
				jenkins.leerMensaje("@jenkins cuánto es 1 + 2", USUARIO)
			);
		
		
		Assert.assertEquals(
				"@delucas 1",
				jenkins.leerMensaje("@jenkins cuánto es 5 - 2 * 2", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 10",
				jenkins.leerMensaje("@jenkins cuánto es el 10% de 100", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 42",
				jenkins.leerMensaje("@jenkins cuánto es el 17 + 5 ^ 2", USUARIO)
			);
		
		// agregar otros casos
	}
	
	@Test
	public void calculosCompuestos() {
		Assert.assertEquals(
				"@delucas -6",
				jenkins.leerMensaje("@jenkins cuánto es (4-8)*2 + 4 / ( 1 + 1)", USUARIO)
			);
		
		// agregar otros casos
	}
	
	
	
	// Desde acá tests de ConversorUnidad (REQ #11).
	@Test
	public void magnitudInvalida() {
		Assert.assertEquals(
				Bot.MSG_NO_ENTIENDO,
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 rama", USUARIO)
			);
	}
	
	@Test
	public void convertirCeroUnidad() {
		Assert.assertEquals(
				"@delucas 0,00 galones equivale a 0,00 litros",
				jenkins.leerMensaje("@jenkins cuántos litros son 0 galones?", USUARIO)
			);
	}
	
	@Test
	public void convertirUnidadRegex() {
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos hay en 1 kilo?", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántas gramos son 1 kilo?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántas gramos hay en 1 kilo?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo ?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo   ?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts. equivale a 10000,00 cms.",
				jenkins.leerMensaje("@jenkins cuántos cms. son 100 mts.?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts equivale a 10000,00 cms.",
				jenkins.leerMensaje("@jenkins cuántos cms. son 100 mts?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts. equivale a 10000,00 cms",
				jenkins.leerMensaje("@jenkins cuántos cms son 100 mts.?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts. equivale a 10000,00 cms.",
				jenkins.leerMensaje("@jenkins cuántos cms. son 100 mts. ?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts. equivale a 10000,00 cms.",
				jenkins.leerMensaje("@jenkins cuántos cms. son 100 mts.  ?", USUARIO)
			);
		
	}
	
	@Test
	public void unidadesDeMasa() {
		
		Assert.assertEquals(
				"@delucas 1,00 kilo equivale a 1000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 1 kilo", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,00 kilogramo equivale a 1000,00 gr",
				jenkins.leerMensaje("@jenkins cuántos gr son 1 kilogramo", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 2,00 kilos equivale a 2000,00 gramos",
				jenkins.leerMensaje("@jenkins cuántos gramos son 2 kilos", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1000,00 gramos equivale a 1,00 kilos",
				jenkins.leerMensaje("@jenkins cuántos kilos son 1000 gramos", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1000,00 gramos equivale a 35,27 onzas",
				jenkins.leerMensaje("@jenkins cuántas onzas son 1000 gramos", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 4000000,00 gramos equivale a 4,00 toneladas",
				jenkins.leerMensaje("@jenkins cuántas toneladas son 4000000 gramos", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 1,70 toneladas equivale a 1700,00 kilos",
				jenkins.leerMensaje("@jenkins cuántos kilos son 1.7 toneladas", USUARIO)
			);
	}

	@Test
	public void unidadesDeTiempo() {
		Assert.assertEquals(
				"@delucas 60,00 segundos equivale a 1,00 minutos",
				jenkins.leerMensaje("@jenkins cuántos minutos hay en 60 segundos", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 3,50 horas equivale a 12600,00 segundos",
				jenkins.leerMensaje("@jenkins cuántos segundos hay en 3.5 horas?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 3,50 horas equivale a 12600,00 seg.",
				jenkins.leerMensaje("@jenkins cuántas seg. hay en 3.5 horas?", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 3,50 hs. equivale a 12600,00 segundo",
				jenkins.leerMensaje("@jenkins cuántas segundo hay en 3.5 hs.?", USUARIO)
			);
	}
	
	@Test
	public void unidadesDeCapacidad() {
		Assert.assertEquals(
				"@delucas 3500,00 cm3 equivale a 3,50 litros",
				jenkins.leerMensaje("@jenkins cuántos litros hay en 3500 cm3", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 10,00 galones equivale a 45,46 litros",
				jenkins.leerMensaje("@jenkins cuántos litros son 10 galones?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 10,00 lts. equivale a 10000,00 cm3.",
				jenkins.leerMensaje("@jenkins cuántos cm3. son 10 lts.?", USUARIO)
			);
	}
	
	@Test
	public void unidadesDeLongitud() {
		Assert.assertEquals(
				"@delucas 1000,00 metros equivale a 1,00 kilometros",
				jenkins.leerMensaje("@jenkins cuántos kilometros hay en 1000 metros", USUARIO)
			);

		Assert.assertEquals(
				"@delucas 1,00 pulgada equivale a 2,54 centimetros",
				jenkins.leerMensaje("@jenkins cuántos centímetros son 1 pulgada?", USUARIO)
			);
		
		Assert.assertEquals(
				"@delucas 100,00 mts. equivale a 10000,00 cms",
				jenkins.leerMensaje("@jenkins cuántos cms son 100 mts.?", USUARIO)
			);
		
	}
	
	//Test gestion de eventos
	@Test
	public void consultarFechaProximoEventoTest() {
		Assert.assertEquals(
				"@testUser tu próximo evento será el domingo 30 de diciembre de 2018 a las 05:00",
				jenkins.leerMensaje("@jenkins cuando será mi próximo evento?", TEST_USER));
	}
	
	@Test
	public void consultarDiasProximoEventoTest() {
		Assert.assertEquals(
				"@testUser tu próximo evento será dentro de 206 días",
				jenkins.leerMensaje("@jenkins cuánto falta para mi próximo evento?", TEST_USER));
	}
	
	@Test
	public void armarEventoConRegexExpresion1Test() {
		Assert.assertEquals("@testUser tu alarma fue guardada existosamente!",
				jenkins.leerMensaje("@jenkins recordame un evento test el 21 de diciembre de 2018 a las 12:12", TEST_USER));
	}
}
