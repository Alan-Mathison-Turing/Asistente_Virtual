package edu.unlam.asistente.busqueda_web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GoogleTest {

	Google google;
	
	@Before
	public void setup() {
		google = new Google();
	}

	@Test
	public void busquedaExistente() {
		Assert.assertEquals("<a href=\"http://www.wordreference.com/es/translation.asp?tranword=term\">"
							+ "<u>http://www.wordreference.com/es/translation.asp?tranword=term</u></a><br/><b>term</b> - "
							+ "Translation to Spanish, pronunciation, and forum discussions.", 
							google.busqueda("term"));
	}
	
	@Test
	public void busquedavacia() {
		Assert.assertEquals("No encontré lo que buscabas, ¿podrías ser más específico?", google.busqueda(""));
	}
	
}
