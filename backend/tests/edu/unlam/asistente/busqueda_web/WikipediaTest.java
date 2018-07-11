package edu.unlam.asistente.busqueda_web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WikipediaTest {

	Wikipedia wikipedia;
	
	@Before
	public void setup() {
		wikipedia = new Wikipedia();
	}
	

	@Test
	public void busquedaExistente() {
		Assert.assertEquals("<a href=\"https://es.wikipedia.org/wiki/Diego_Armando_Maradona\">"
						+ "<u>https://es.wikipedia.org/wiki/Diego_Armando_Maradona</u></a><br/>"
						+ "<p><b>Diego Armando Maradona</b> es un exfutbolista y director técnico argentino. "
						+ "Actualmente se desempeña como presidente y director deportivo del FC Dinamo Brest de la "
						+ "Liga Premier de Bielorrusia.</p>",
							wikipedia.busqueda("maradona"));
	}

	@Test
	public void busquedaCompuesta() {
		Assert.assertEquals("<a href=\"https://es.wikipedia.org/wiki/Diego_Armando_Maradona\">"
						+ "<u>https://es.wikipedia.org/wiki/Diego_Armando_Maradona</u></a><br/>"
						+ "<p><b>Diego Armando Maradona</b> es un exfutbolista y director técnico argentino. "
						+ "Actualmente se desempeña como presidente y director deportivo del FC Dinamo Brest de la "
						+ "Liga Premier de Bielorrusia.</p>", 
							wikipedia.busqueda("Diego Armando Maradona"));
	}
	
	@Test
	public void busquedaCompuestaDos() {
		Assert.assertEquals("<a href=\"https://es.wikipedia.org/wiki/Diego_Armando_Maradona\">"
							+ "<u>https://es.wikipedia.org/wiki/Diego_Armando_Maradona</u></a><br/>"
							+ "<p><b>Diego Armando Maradona</b> es un exfutbolista y director técnico argentino. "
							+ "Actualmente se desempeña como presidente y director deportivo del FC Dinamo Brest de la "
							+ "Liga Premier de Bielorrusia.</p>", 
							wikipedia.busqueda("Diego Maradona"));
	}
	
	@Test
	public void busquedaVariosResultados() {
		Assert.assertEquals("<a href=\"https://es.wikipedia.org/wiki/Pablo\"><u>https://es.wikipedia.org/wiki/Pablo</u></a><br/><p>El nombre de <b>Pablo</b> puede referirse a:</p><ul><li>Pablo (nombre).</li>\n" + 
						"<li>Pablo de Tarso, apóstol, teólogo y escritor cristiano del siglo I.</li>\n" + 
						"<li>Pablo de Tebas, eremita egipcio (228-342).</li>\n" + 
						"<li>Pablo I, papa de 757 a 767.</li>\n" + 
						"<li>Pablo II, papa de 1464 a 1471.</li>\n" + 
						"<li>Pablo III, papa de 1534 a 1549.</li>\n" + 
						"<li>Pablo IV, papa de 1555 a 1559.</li>\n" + 
						"<li>Pablo V, papa de 1605 a 1621.</li>\n" + 
						"<li>Pablo VI, papa de 1963 a 1978.</li>\n" + 
						"<li>Pablo el Diácono, monje benedictino del siglo VIII.</li>\n" + 
						"<li>Pablo Picasso, pintor y escultor español, creador del cubismo.</li>\n" + 
						"<li>Pablo Neruda, poeta chileno, premio Nobel de Literatura 1971.</li>\n" + 
						"<li>Pablo Escobar, narcotraficante colombiano.</li></ul>", 
							wikipedia.busqueda("pablo"));
	}
	
	@Test
	public void busquedaNoExistente() {
		Assert.assertEquals("", wikipedia.busqueda("wasuwasa"));
	}
	
	@Test
	public void busquedaVacia() {
		Assert.assertEquals("", wikipedia.busqueda(""));
	}
	
	@Test
	public void busquedaEspacio() {
		Assert.assertEquals("", wikipedia.busqueda(" "));
	}

}
