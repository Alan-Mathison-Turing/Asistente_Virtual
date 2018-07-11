package edu.unlam.asistente.calendario;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalendarioTest {
	
	private Date fecha1;
	private Date fecha2;
	private Date fecha3;
	private List<Date> listaFechas;
	
	@Before
	public void setUp() {
		fecha1 = new GregorianCalendar(2018, 0, 1, 12, 0).getTime();
		fecha2 = new GregorianCalendar(2018, 10, 30, 15, 0).getTime();
		fecha3 = new GregorianCalendar(2020, 0, 1, 12, 0).getTime();
		listaFechas = new ArrayList<Date>();
		listaFechas.add(fecha1);
		listaFechas.add(fecha2);
		listaFechas.add(fecha3);
		
	}
	
	@Test
	public void esFechaProximaTrueTest() {
		Assert.assertTrue(Calendario.esFechaProxima(fecha3));
	}
	
	@Test
	public void esFechaProximaFalseTest() {
		Assert.assertTrue(!Calendario.esFechaProxima(fecha1));
	}
	
	@Test
	public void getFechaParaSqliteDeStringTest() throws ParseException {
		Assert.assertEquals(fecha1, Calendario.getFechaParaSqliteDeString("2018-01-01 12:00:00"));
	}
	
	@Test
	public void getFechaMasCercanaDeListaFechasTest() {
		Date aux = Calendario.getFechaMasCercanaDeListaFechas(listaFechas);
		Assert.assertEquals(fecha2, aux);
	}
}
