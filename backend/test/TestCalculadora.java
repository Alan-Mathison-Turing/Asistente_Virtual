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
	public void testRestarBinaria() {
		Calculadora calculadora = new Calculadora("22-1.3");
		assertEquals(20.7, calculadora.resolver(), 0);
	}

	@Test
	public void testRestarTernaria() {
		Calculadora calculadora = new Calculadora("22-1.3-2");
		assertEquals(18.7, calculadora.resolver(), 0);
	}

	@Test
	public void testSumasYRestas() {
		Calculadora calculadora = new Calculadora("1+2-3+5-6");
		assertEquals(-1.0, calculadora.resolver(), 0);
	}

	@Ignore
	public void testNegativoPrimero() {
		Calculadora calculadora = new Calculadora("-1.3-2");
		assertEquals(-3.3, calculadora.resolver(), 0);
	}

	@Test
	public void testMultiplicarBinaria() {
		Calculadora calculadora = new Calculadora("2*1.5");
		assertEquals(3.0, calculadora.resolver(), 0);
	}

	@Test
	public void testMultiplicarTernaria() {
		Calculadora calculadora = new Calculadora("2*1.5*4");
		assertEquals(12.0, calculadora.resolver(), 0);
	}

	@Test
	public void testDividirBinaria() {
		Calculadora calculadora = new Calculadora("6/1.5");
		assertEquals(4.0, calculadora.resolver(), 0);
	}

	@Test
	public void testDividirTernaria() {
		Calculadora calculadora = new Calculadora("6/1.5/4");
		assertEquals(1.0, calculadora.resolver(), 0);
	}

	@Test
	public void testDividirYMultiplicar() {
		Calculadora calculadora = new Calculadora("6/1.5*10.5");
		assertEquals(42.0, calculadora.resolver(), 0);
	}

	@Test
	public void testCuentaFacil() {
		Calculadora calculadora = new Calculadora("32*1.5-20/10");
		assertEquals(46.0, calculadora.resolver(), 0);
	}

	@Test
	public void TestParentesisMultiplicar() {
		Calculadora calculadora = new Calculadora("3*(2+1)");
		assertEquals(9.0, calculadora.resolver(), 0);
	}
}
