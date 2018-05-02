package calculadora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que administra una calculadora.
 * <p>
 * Resuelve distintos tipos de operaciones desde sumas y restas hasta potencias
 * y porcentajes. Administra distintos niveles de paréntesis para cálculos más
 * complejos. <br>
 */
public class Calculadora {
	/**
	 * Centa en las calculadora. <br>
	 */
	private String cuenta;
	/**
	 * Números de la cuenta. <br>
	 */
	private double[] numeros;
	/**
	 * Simbolos de la cuenta. <br>
	 */
	private char[] simbolos;

	/**
	 * Regex de un número real. <br>
	 */
	private static final Pattern numero = Pattern.compile("[\\d\\.]+");

	/**
	 * Regex de símbolos de una cuenta. <br>
	 */
	private static final Pattern simbolo = Pattern.compile("[\\+\\-\\*\\/]");

	/**
	 * Matcheador para los regex. <br>
	 */
	private Matcher matcher;

	/**
	 * Crea una cuenta a resolver usando las características de una calculadora.
	 * <br>
	 * 
	 * @param cuenta
	 *            Cuenta a resolver. <br>
	 */
	public Calculadora(final String cuenta) {
		this.cuenta = cuenta;
		this.corregirCuenta();
		this.iniciarVectores();
		this.extraerNumeros();
		this.extraerSimbolos();
	}

	/**
	 * Resuelve la cuenta. <br>
	 * 
	 * @return Resultado. <br>
	 */
	public double resolver() {
		double resultado = 0;
		double auxiliar = 0;
		int indiceNumero = 0;
		int indiceSimbolo = 0;
		// El siguiente valor a incorporar es un número.
		// boolean numeroSiguiente = true;

		// this.simbolos[indiceSimbolo] != '-'

		resultado += this.numeros[indiceNumero];
		indiceNumero++;

		while (indiceNumero + indiceSimbolo < this.cuenta.length()) {
			// if (numeroSiguiente) {
			// auxiliar += this.numeros[indiceNumero];
			//
			// System.out.println("Auxiliar: " + auxiliar);
			//
			// indiceNumero++;
			// numeroSiguiente = false;
			// } else {
			switch (this.simbolos[indiceSimbolo]) {
			case '+':
				auxiliar = 0;
				auxiliar += this.numeros[indiceNumero];
				resultado += auxiliar;
				break;
			case '-':
				auxiliar = 0;
				auxiliar += this.numeros[indiceNumero];
				resultado -= auxiliar;
				break;
			case '*':
				auxiliar *= this.numeros[indiceNumero];
				// numeroSiguiente = false;
				break;
			}
			indiceNumero++;
			indiceSimbolo++;
		}
		// }
		return resultado;
	}

	private void corregirCuenta(){
		
	}
	
	/**
	 * Inicializa los vectores auxiliares. <br>
	 */
	private void iniciarVectores() {
		this.numeros = new double[this.cuenta.length()];
		this.simbolos = new char[this.cuenta.length()];
	}

	/**
	 * Extrae los números de la cuenta. <br>
	 */
	private void extraerNumeros() {
		int i = 0;
		this.matcher = numero.matcher(this.cuenta);
		while (this.matcher.find()) {
			this.numeros[i] = Double.parseDouble(this.matcher.group());
			// System.out.println(this.numeros[i]);
			i++;
		}
	}

	/**
	 * Extrae los símbolos de la cuenta. <br>
	 */
	private void extraerSimbolos() {
		int i = 0;
		this.matcher = simbolo.matcher(this.cuenta);
		while (this.matcher.find()) {
			this.simbolos[i] = this.matcher.group().charAt(0);
			// System.out.println(this.simbolos[i]);
			i++;
		}
	}
}
