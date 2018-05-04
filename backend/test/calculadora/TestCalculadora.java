package calculadora;

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
	public void TestParentesisSumar() {
		Calculadora calculadora = new Calculadora("3+(2+1)");
		assertEquals(6.0, calculadora.resolver(), 0);
	}

	@Test
	public void TestParentesisRestar() {
		Calculadora calculadora = new Calculadora("3-(2+1)");
		assertEquals(0.0, calculadora.resolver(), 0);
	}

	@Test
	public void TestParentesisMultiplicar() {
		Calculadora calculadora = new Calculadora("3*(2+1)");
		assertEquals(9.0, calculadora.resolver(), 0);
	}

	@Test
	public void TestParentesisDividir() {
		Calculadora calculadora = new Calculadora("3/(2+1)");
		assertEquals(1.0, calculadora.resolver(), 0);
	}

	@Test
	public void parentesisAlPrincipio() {
		Calculadora calculadora = new Calculadora("(2+2)*3");
		assertEquals(12.0, calculadora.resolver(), 0);
	}

	@Test
	public void testGramatica1() {
		try {
			Calculadora calculadora = new Calculadora("3/(2+1");
			calculadora.resolver();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGramatica2() {
		try {
			Calculadora calculadora = new Calculadora("3/(2+1)*");
			calculadora.resolver();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGramatica3() {
		try {
			Calculadora calculadora = new Calculadora("/3/(2+1)");
			calculadora.resolver();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGramaticaEspacios() {
		Calculadora calculadora = new Calculadora("(4-8)*2 + 4 / ( 1 + 1)");
		assertEquals(-6.0, calculadora.resolver(), 0);
	}

	@Test
	public void testCuentaMixta() {
		Calculadora calculadora = new Calculadora("-2-(3*1)+4");
		assertEquals(-1.0, calculadora.resolver(), 0);
	}

	@Test
	public void testCuentaMixta2() {
		Calculadora calculadora = new Calculadora("-2+(-5)/5*2-10");
		assertEquals(-14.0, calculadora.resolver(), 0);
	}

	@Test
	public void testCuentaMixta3() {
		Calculadora calculadora = new Calculadora("-2+(-5)/(5*2)-10");
		assertEquals(-12.5, calculadora.resolver(), 0);
	}

	@Test
	public void testSignosIguales() { // ++
		Calculadora calculadora = new Calculadora("(2++2)");
		assertEquals(4.0, calculadora.resolver(), 0);
	}

	@Test
	public void testSignosIguales2() { // +-
		Calculadora calculadora = new Calculadora("(2+-2)");
		assertEquals(0.0, calculadora.resolver(), 0);
	}

	@Test
	public void testSignosIguales3() { // -+
		Calculadora calculadora = new Calculadora("(2-+2)");
		assertEquals(0.0, calculadora.resolver(), 0);
	}

	@Test
	public void testSignosIguales4() { // --
		Calculadora calculadora = new Calculadora("(2--2)");
		assertEquals(4.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPotencia() {
		Calculadora calculadora = new Calculadora("2^2");
		assertEquals(4.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPotenciaConParentesis() {
		Calculadora calculadora = new Calculadora("2^(2*2)");
		assertEquals(16.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPotenciaConAlgoDespues() {
		Calculadora calculadora = new Calculadora("2^2*2");
		assertEquals(8.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPotenciaNegativaEntreParentesis() {
		Calculadora calculadora = new Calculadora("2^(-2)");
		assertEquals(0.25, calculadora.resolver(), 0);
	}

	@Ignore
	public void testRaizCuadrada() {
		Calculadora calculadora = new Calculadora("4?(2)");
		assertEquals(2.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPorcentaje() {
		Calculadora calculadora = new Calculadora("10% de 100");
		assertEquals(10.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPorcentajeIntermedio() {
		Calculadora calculadora = new Calculadora("50% de 50");
		assertEquals(25.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPorcentajeAvanzado() {
		Calculadora calculadora = new Calculadora("10% de (120-20)");
		assertEquals(10.0, calculadora.resolver(), 0);
	}

	@Test
	public void testPorcentajeExperto() {
		Calculadora calculadora = new Calculadora("10% de (150-(25*2/1))");
		assertEquals(10.0, calculadora.resolver(), 0);
	}
}
