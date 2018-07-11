package edu.unlam.asistente.conversor_unidades;

import edu.unlam.asistente.conversor_unidades.ConversorUnidad;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConversorUnidadesTests {

	private ConversorUnidad cu;
	
	@Before
	public void setup() {
		cu = new ConversorUnidad();
	}
	
	@Test
	public void magnitudInvalida() {
		Assert.assertEquals(-1, cu.convertirUnidad(1, "Metro", "basura"), 0);
		Assert.assertEquals(-1, cu.convertirUnidad(1, "basura", "Litro"), 0);
	}
	
	@Test
	public void valorCero() {
		Assert.assertEquals(0, cu.convertirUnidad(0, "Metro", "Centimetro"), 0);
	}
	
	@Test
	public void valorNegativo() {
		Assert.assertEquals(-2, cu.convertirUnidad(-1, "Metro", "Centimetro"), 0);
	}
	
	@Test
	public void unidadLongitud() {
		Assert.assertEquals(100.00, cu.convertirUnidad(1, "Metro", "Centimetro"), 0);
		Assert.assertEquals(14.87, cu.convertirUnidad(1487, "Centimetro", "Metro"), 0);
		Assert.assertEquals(7.87, cu.convertirUnidad(3.10, "Pulgada", "Centimetro"), 0);
		Assert.assertEquals(38.58, cu.convertirUnidad(98, "Centimetro", "Pulgada"), 0);
		Assert.assertEquals(426.72, cu.convertirUnidad(14, "Pie", "Centimetro"), 0);
		Assert.assertEquals(3.22, cu.convertirUnidad(98, "Centimetro", "Pie"), 0);
		Assert.assertEquals(44.13, cu.convertirUnidad(13.45, "Metro", "Pie"), 0);
		Assert.assertEquals(7.62, cu.convertirUnidad(25, "Pie", "Metro"), 0);
		Assert.assertEquals(853.15, cu.convertirUnidad(21.67, "Metro", "Pulgada"), 0);
		Assert.assertEquals(0.18, cu.convertirUnidad(7, "Pulgada", "Metro"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1000, "Metro", "Kilometro"), 0);
		Assert.assertEquals(10000.00, cu.convertirUnidad(10, "Kilometro", "Metro"), 0);
		Assert.assertEquals(10.00, cu.convertirUnidad(1000000, "Centimetro", "Kilometro"), 0);
		Assert.assertEquals(100000.00, cu.convertirUnidad(1, "Kilometro", "Centimetro"), 0);
		Assert.assertEquals(0.30, cu.convertirUnidad(1000, "Pie", "Kilometro"), 0);
		Assert.assertEquals(6561.68, cu.convertirUnidad(2, "Kilometro", "Pie"), 0);
		Assert.assertEquals(0.25, cu.convertirUnidad(10000, "Pulgada", "Kilometro"), 0);
		Assert.assertEquals(78740.2, cu.convertirUnidad(2, "Kilometro", "Pulgada"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Centimetro", "Centimetro"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Metro", "Metro"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Kilometro", "Kilometro"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Pie", "Pie"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Pulgada", "Pulgada"), 0);
	}
	
	@Test
	public void unidadMasa() {
		Assert.assertEquals(1000.00, cu.convertirUnidad(1, "Kilo", "Gramo"), 0);
		Assert.assertEquals(2000.00, cu.convertirUnidad(2, "Kilo", "Gramo"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1000, "Gramo", "Kilo"), 0);
		Assert.assertEquals(35.27, cu.convertirUnidad(1000, "Gramo", "Onza"), 0);
		Assert.assertEquals(85.05, cu.convertirUnidad(3, "Onza", "Gramo"), 0);
		Assert.assertEquals(1.7, cu.convertirUnidad(1700, "Kilo", "Tonelada"), 0);
		Assert.assertEquals(1700, cu.convertirUnidad(1.7, "Tonelada", "Kilo"), 0);
		Assert.assertEquals(4, cu.convertirUnidad(4000000, "Gramo", "Tonelada"), 0);
		Assert.assertEquals(7000000, cu.convertirUnidad(7, "Tonelada", "Gramo"), 0);
		Assert.assertEquals(2, cu.convertirUnidad(70547.9, "Onza", "Tonelada"), 0);
		Assert.assertEquals(70548.0, cu.convertirUnidad(2, "Tonelada", "Onza"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Gramo", "Gramo"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Kilo", "Kilo"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Onza", "Onza"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Tonelada", "Tonelada"), 0);
	}
	
	@Test
	public void unidadCapacidad() {
		Assert.assertEquals(1700.00, cu.convertirUnidad(1.7, "Litro", "Cm3"), 0);
		Assert.assertEquals(4.57, cu.convertirUnidad(4566, "Cm3", "Litro"), 0);
		Assert.assertEquals(2.20, cu.convertirUnidad(10, "Litro", "Galon"), 0);
		Assert.assertEquals(45.46, cu.convertirUnidad(10, "Galon", "Litro"), 0);
		Assert.assertEquals(0.42, cu.convertirUnidad(1899, "Cm3", "Galon"), 0);
		Assert.assertEquals(44142.53, cu.convertirUnidad(9.71, "Galon", "Cm3"), 0);		
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Cm3", "Cm3"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Litro", "Litro"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Galon", "Galon"), 0);
	}
	
	@Test
	public void unidadTiempo() {
		Assert.assertEquals(1.00, cu.convertirUnidad(60, "Segundo", "Minuto"), 0);
		Assert.assertEquals(1.48, cu.convertirUnidad(89, "Segundo", "Minuto"), 0);
		Assert.assertEquals(186, cu.convertirUnidad(3.10, "Minuto", "Segundo"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(3600, "Segundo", "Hora"), 0);
		Assert.assertEquals(12600.00, cu.convertirUnidad(3.5, "Hora", "Segundo"), 0);
		Assert.assertEquals(0.23, cu.convertirUnidad(20000, "Segundo", "Dia"), 0);
		Assert.assertEquals(838944.00, cu.convertirUnidad(9.71, "Dia", "Segundo"), 0);		
		Assert.assertEquals(250.00, cu.convertirUnidad(15000, "Minuto", "Hora"), 0);
		Assert.assertEquals(210.00, cu.convertirUnidad(3.5, "Hora", "Minuto"), 0);
		Assert.assertEquals(10.42, cu.convertirUnidad(15000, "Minuto", "Dia"), 0);
		Assert.assertEquals(13982.40, cu.convertirUnidad(9.71, "Dia", "Minuto"), 0);
		Assert.assertEquals(1.5, cu.convertirUnidad(36, "Hora", "Dia"), 0);
		Assert.assertEquals(864, cu.convertirUnidad(36, "Dia", "Hora"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Segundo", "Segundo"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Minuto", "Minuto"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Hora", "Hora"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1, "Dia", "Dia"), 0);
	}
	
}
