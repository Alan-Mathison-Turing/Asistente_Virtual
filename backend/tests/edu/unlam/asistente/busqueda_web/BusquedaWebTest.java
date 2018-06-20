package edu.unlam.asistente.busqueda_web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusquedaWebTest {

	BusquedaWeb busquedaWeb;
	
	@Before
	public void setup() {
		busquedaWeb = new BusquedaWeb();
	}
	

	@Test
	public void busquedaExistente() {
		Assert.assertEquals("https://es.wikipedia.org/wiki/Diego_Armando_Maradona\n" + 
							"Diego Armando Maradona es un exfutbolista y director técnico "
							+ "argentino. Actualmente se desempeña como presidente y "
							+ "director deportivo del FC Dinamo Brest de la Liga Premier de Bielorrusia.", 
							busquedaWeb.busquedaWeb("maradona"));
	}

	@Test
	public void busquedaCompuesta() {
		Assert.assertEquals("https://es.wikipedia.org/wiki/Diego_Armando_Maradona\n" + 
							"Diego Armando Maradona es un exfutbolista y director técnico "
							+ "argentino. Actualmente se desempeña como presidente y "
							+ "director deportivo del FC Dinamo Brest de la Liga Premier de Bielorrusia.", 
							busquedaWeb.busquedaWeb("Diego Armando Maradona"));
	}
	
	@Test
	public void busquedaCompuestaDos() {
		Assert.assertEquals("https://es.wikipedia.org/wiki/Diego_Armando_Maradona\n" + 
							"Diego Armando Maradona es un exfutbolista y director técnico "
							+ "argentino. Actualmente se desempeña como presidente y "
							+ "director deportivo del FC Dinamo Brest de la Liga Premier de Bielorrusia.", 
							busquedaWeb.busquedaWeb("Diego Maradona"));
	}
	
	@Test
	public void busquedaVariosResultados() {
		Assert.assertEquals("https://es.wikipedia.org/wiki/Pablo\n" + 
							"El nombre de Pablo puede referirse a:Pablo (nombre).\n" + 
							"Pablo de Tarso, apóstol, teólogo y escritor cristiano del siglo I.\n" + 
							"Pablo de Tebas, eremita egipcio (228-342).\n" + 
							"Pablo I, papa de 757 a 767.\n" + 
							"Pablo II, papa de 1464 a 1471.\n" + 
							"Pablo III, papa de 1534 a 1549.\n" + 
							"Pablo IV, papa de 1555 a 1559.\n" + 
							"Pablo V, papa de 1605 a 1621.\n" + 
							"Pablo VI, papa de 1963 a 1978.\n" + 
							"Pablo el Diácono, monje benedictino del siglo VIII.\n" + 
							"Pablo Picasso, pintor y escultor español, creador del cubismo.\n" + 
							"Pablo Neruda, poeta chileno, premio Nobel de Literatura 1971.\n" + 
							"Pablo Escobar, narcotraficante colombiano.", 
							busquedaWeb.busquedaWeb("pablo"));
	}
	
	@Test
	public void busquedaNoExistente() {
		Assert.assertEquals("https://www.youtube.com/watch?v=p2Cp22ucH9o\n"
							+ "9 Sep 2009 ... Like, Subscribe, and enjoy! All the rights are owned by the artist of this song.", 
						busquedaWeb.busquedaWeb("wasawasa"));
	}

	@Test
	public void busquedaNoExistenteDos() {
		Assert.assertEquals("http://www.wordreference.com/es/translation.asp?tranword=term\n"
							+ "term - Translation to Spanish, pronunciation, and forum discussions.", 
						busquedaWeb.busquedaWeb("term"));
	}
	
	@Test
	public void busquedaVacia() {
		Assert.assertEquals(null + "\n" + null, busquedaWeb.busquedaWeb(""));
	}
	
	@Test
	public void busquedaEspacio() {
		Assert.assertEquals(null + "\n" + null, busquedaWeb.busquedaWeb(" "));
	}
	
}
