package edu.unlam.asistente.financiera;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MonedaTest {

	@Parameter(0)
	public int numeroMoneda;
	@Parameter(1)
	public String nombreMoneda;

	@Parameters(name = "{1}")
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 1, "dolar" }, { 2, "euro" }, { 3, "peso uruguayo" }, { 4, "guarani" },
				{ 5, "bolivar" }, { 6, "peso boliviano" }, { 7, "peso Chileno" }, { 8, "Colombiano" }, { 9, "Libra" },
				{ 10, "peso Mexicano" }, { 11, "real" }, { 12, "Sol peruano" } };
		return Arrays.asList(data);
	}

	@Test
	public void testCotizacion() {
		System.out.println("Valor de la moneda " + this.nombreMoneda.toLowerCase() + ": "
				+ new Moneda().leerMoneda(this.nombreMoneda));
	}
}
