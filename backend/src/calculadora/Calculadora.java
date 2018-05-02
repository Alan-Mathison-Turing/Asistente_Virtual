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
	 * Índice de la posición de los números en la cuenta. <br>
	 */
	private int indiceNumero = 0;

	/**
	 * Índice de la posición de los símbolos en la cuenta. <br>
	 */
	private int indiceSimbolo = 0;

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
	private static final Pattern simbolo = Pattern.compile("[\\+\\-\\*\\/\\(\\)]");

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

		auxiliar = this.numeros[indiceNumero];
		indiceNumero++;

		while (indiceNumero + indiceSimbolo < this.cuenta.length()) {
			switch (this.simbolos[this.indiceSimbolo]) {
			case '+':
				resultado += auxiliar;
				auxiliar = this.numeros[this.indiceNumero];
				break;
			case '-':
				resultado += auxiliar;
				auxiliar = this.numeros[this.indiceNumero] * (-1);
				break;
			case '*':
				// Compruebo si el próximo símbolo es un paréntesis. De serlo,
				// resuelvo todo lo de adentro y eso lo multiplico.
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo++;
					auxiliar *= this.resolver();
				} else {
					auxiliar *= this.numeros[this.indiceNumero];
				}
				break;
			case '/':
				auxiliar /= this.numeros[this.indiceNumero];
				break;
			case '(':
				// Para que no se pase el índice.
				this.indiceNumero--;
				break;
			case ')':
				// Este caso no nos interesa el break ya que nos interesa el
				// resultado adentro de los paréntesis.
				this.indiceNumero++;
				this.indiceSimbolo++;
				return resultado + auxiliar;
			}
			this.indiceNumero++;
			this.indiceSimbolo++;
		}
		return resultado + auxiliar;
	}

	private void corregirCuenta() {

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
			i++;
		}
	}
}
