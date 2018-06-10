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
		Assert.assertEquals("http://www.wordreference.com/es/translation.asp?tranword=term", 
							google.busqueda("term"));
	}
	
	@Test
	public void busquedavacia() {
		Assert.assertEquals("https://www.google.com/doodles", google.busqueda(""));
	}
	
}
