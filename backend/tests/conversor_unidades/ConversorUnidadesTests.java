package conversor_unidades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConversorUnidadesTests {

	private ConversorUnidades cu;
	
	@Before
	public void setup() {
		cu = new ConversorUnidades();
	}
	
	@Test
	public void magnitudInvalida() {
		Assert.assertEquals(-1, cu.convertirUnidad(1, "metros", "basura"), 0);
		Assert.assertEquals(-1, cu.convertirUnidad(1, "basura", "litro"), 0);
	}
	
	@Test
	public void unidadLongitud() {
		Assert.assertEquals(100.00, cu.convertirUnidad(1, "metros", "centimetros"), 0);
		Assert.assertEquals(14.87, cu.convertirUnidad(1487, "centimetros", "metros"), 0);
		Assert.assertEquals(7.87, cu.convertirUnidad(3.10, "pulgadas", "centimetros"), 0);
		Assert.assertEquals(38.58, cu.convertirUnidad(98, "centimetros", "pulgadas"), 0);
		Assert.assertEquals(426.72, cu.convertirUnidad(14, "pies", "centimetros"), 0);
		Assert.assertEquals(3.22, cu.convertirUnidad(98, "centimetros", "pies"), 0);
		Assert.assertEquals(44.13, cu.convertirUnidad(13.45, "metros", "pies"), 0);
		Assert.assertEquals(7.62, cu.convertirUnidad(25, "pies", "metros"), 0);
		Assert.assertEquals(853.15, cu.convertirUnidad(21.67, "metros", "pulgadas"), 0);
		Assert.assertEquals(0.18, cu.convertirUnidad(7, "pulgadas", "metros"), 0);
	}
	
	@Test
	public void unidadMasa() {
		Assert.assertEquals(1000.00, cu.convertirUnidad(1, "kilo", "gramos"), 0);
		Assert.assertEquals(2000.00, cu.convertirUnidad(2, "kilos", "gramos"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1000, "gramos", "kilos"), 0);
		Assert.assertEquals(35.27, cu.convertirUnidad(1000, "gramos", "onzas"), 0);
		Assert.assertEquals(85.05, cu.convertirUnidad(3, "onzas", "gramos"), 0);
		Assert.assertEquals(1.7, cu.convertirUnidad(1700, "kilo", "tonelada"), 0);
		Assert.assertEquals(1700, cu.convertirUnidad(1.7, "tonelada", "kilo"), 0);
		Assert.assertEquals(4, cu.convertirUnidad(4000000, "gramos", "toneladas"), 0);
		Assert.assertEquals(7000000, cu.convertirUnidad(7, "toneladas", "gramos"), 0);
		Assert.assertEquals(2, cu.convertirUnidad(70547.9, "onzas", "toneladas"), 0);
		Assert.assertEquals(70548.0, cu.convertirUnidad(2, "toneladas", "onzas"), 0);
	}
	
}
