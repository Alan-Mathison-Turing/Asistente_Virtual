import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import calculadora.Calculadora;

public class TestCalculadora {

	@Test
	public void testSumaBinaria() {
		Calculadora calculadora = new Calculadora("22+1.3");
		assertEquals(23.3, calculadora.resolver(), 0);
	}

	@Test
	public void testSumaTernaria() {
		Calculadora calculadora = new Calculadora("22+1.3+2");
		assertEquals(25.3, calculadora.resolver(), 0);
	}
	
	@Test
	public void testRestarBinaria(){
		Calculadora calculadora = new Calculadora("22-1.3");
		assertEquals(20.7, calculadora.resolver(), 0);		
	}
	
	@Test
	public void testRestarTernaria(){
		Calculadora calculadora = new Calculadora("22-1.3-2");
		assertEquals(18.7, calculadora.resolver(), 0);		
	}
	
	@Test
	public void testSumasYRestas(){
		Calculadora calculadora = new Calculadora("1+2-3+5-6");
		assertEquals(-1.0, calculadora.resolver(), 0);
	}
}
