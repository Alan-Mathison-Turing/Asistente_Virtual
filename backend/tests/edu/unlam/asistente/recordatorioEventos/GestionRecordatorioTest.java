package edu.unlam.asistente.recordatorioEventos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.pojo.Evento;
import org.junit.Assert;

public class GestionRecordatorioTest {
	
	Evento evento;
	Evento evento2;
	Evento evento3;
	GestionRecordatorio gestRecordatorio;
	Pattern pattern;
	Matcher matcher;
	
	@Before
	public void setUp() {
		evento = new Evento(1, "2018-12-21 12:12:00", "test");
		evento2 = new Evento(1, "2018-12-01 12:12:00", "test");
		evento3 = new Evento(1, "2018-12-21 01:12:00", "test");
		pattern = Pattern.compile(GestionRecordatorio.getRegex());
		gestRecordatorio = new GestionRecordatorio();
		
	}
	
	@Test
	public void armarEventoConRegexExpresion1Test() {
		Matcher m = pattern.matcher("@testBot recordame un evento test el 21 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion2Test() {
		Matcher m = pattern.matcher("@testBot anotame un evento test el 21 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion3Test() {
		Matcher m = pattern.matcher("@testBot agendame un evento test el 21 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion4Test() {
		Matcher m = pattern.matcher("@testBot recordame un evento test el 21/12/2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion5Test() {
		Matcher m = pattern.matcher("@testBot anotame un evento test el 21/12/2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion6Test() {
		Matcher m = pattern.matcher("@testBot agendame un evento test el 21/12/2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion7Test() {
		Matcher m = pattern.matcher("@testBot recordame un evento test el 1 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion8Test() {
		Matcher m = pattern.matcher("@testBot anotame un evento test el 1 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion9Test() {
		Matcher m = pattern.matcher("@testBot agendame un evento test el 1 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion10Test() {
		Matcher m = pattern.matcher("@testBot recordame un evento test el 01 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion11Test() {
		Matcher m = pattern.matcher("@testBot anotame un evento test el 01 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion12Test() {
		Matcher m = pattern.matcher("@testBot agendame un evento test el 01 de diciembre de 2018 a las 12:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento2, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion13Test() {
		Matcher m = pattern.matcher("@testBot recordame un evento test el 21 de diciembre de 2018 a las 1:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento3, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion14Test() {
		Matcher m = pattern.matcher("@testBot anotame un evento test el 21 de diciembre de 2018 a las 1:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento3, resultado);
	}
	
	@Test
	public void armarEventoConRegexExpresion15Test() {
		Matcher m = pattern.matcher("@testBot agendame un evento test el 21 de diciembre de 2018 a las 1:12");
		Evento resultado = gestRecordatorio.armarEventoConRegex(m);
		resultado.setId(1);
		Assert.assertEquals(evento3, resultado);
	}
	
}
