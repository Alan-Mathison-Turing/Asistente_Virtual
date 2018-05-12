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
	public void unidadLongitud() {
		Assert.assertEquals(100.00, cu.convertirUnidad(1, "metros", "centimetros"), 0);
	}
	
	@Test
	public void unidadMasa() {
		Assert.assertEquals(1000.00, cu.convertirUnidad(1, "kilo", "gramos"), 0);
		Assert.assertEquals(2000.00, cu.convertirUnidad(2, "kilos", "gramos"), 0);
		Assert.assertEquals(1.00, cu.convertirUnidad(1000, "gramos", "kilos"), 0);
		Assert.assertEquals(35.274, cu.convertirUnidad(1000, "gramos", "onzas"), 0);

	}
	
}
