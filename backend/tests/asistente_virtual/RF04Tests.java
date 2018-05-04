package asistente_virtual;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF04Tests {

	public final static String USUARIO = "delucas";
	public final static Date FECHA_HORA = new GregorianCalendar(2018, 3, 1, 15, 15, 0).getTime();
	
	Bot jenkins;
	
	@Before
	public void setup() {
		jenkins = new Bot("jenkins");
	}
	
	@Test
	public void diaDentroDe() {
		Assert.assertEquals(
				"@delucas será el martes 3 de abril de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 días?")
			);
		
		Assert.assertEquals(
				"@delucas ser� el viernes 1 de junio de 2018",
				jenkins.leerMensaje("@jenkins qué día será dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas ser� el mi�rcoles 1 de abril de 2020",
				jenkins.leerMensaje("@jenkins qu� d�a ser� dentro de 2 a�os?")
			);
	}
	
	@Test
	public void diaHace() {
		Assert.assertEquals(
				"@delucas fue s�bado 31 de marzo de 2018",
				jenkins.leerMensaje("@jenkins qu� d�a fue ayer?")
			);
		
		Assert.assertEquals(
				"@delucas fue jueves 29 de marzo de 2018",
				jenkins.leerMensaje("@jenkins qu� d�a fue hace 3 d�as?")
			);
		
		Assert.assertEquals(
				"@delucas fue el jueves 1 de febrero de 2018",
				jenkins.leerMensaje("@jenkins qu� d�a fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas fue el viernes 1 de abril de 2016",
				jenkins.leerMensaje("@jenkins qu� d�a fue hace 2 a�os?")
			);
	}
	
	
	@Test
	public void tiempoDesde() {
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 365 d�as",
				jenkins.leerMensaje("@jenkins cu�ntos d�as pasaron desde el 1 de abril de 2017?")
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void tiempoHasta() {
		Assert.assertEquals(
				"@delucas faltan 9 d�as",
				jenkins.leerMensaje("@jenkins cu�ntos d�as faltan para el 10 de abril?")
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
}