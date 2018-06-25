package edu.unlam.asistente.calculadora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;

/**
 * Clase que administra una calculadora.
 * <p>
 * Resuelve distintos tipos de operaciones desde sumas y restas hasta potencias
 * y porcentajes. Administra distintos niveles de paréntesis para cálculos más
 * complejos. <br>
 */
public class Calculadora implements IDecision {
	/**
	 * Cantidad de números y símbolos en una cuenta. <br>
	 */
	private int cantidad = 0;

	/**
	 * Cuenta en las calculadora. <br>
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
	 * Regex del formato de una cuenta.
	 * <p>
	 * Permite espacios que luego van a ser ignorados. <br>
	 */
	private static final Pattern FORMATO_CUENTA = Pattern.compile("^[\\d\\+\\-\\/\\*\\.\\^\\%\\(\\)de ]*$");

	/**
	 * Regex para controlar que no haya símbolos al principio o al final de la
	 * cuenta de manera ilegal. <br>
	 */
	private static final Pattern CONTROL_SINTAXIS_ERRONEA = Pattern
			.compile("(^[\\/\\*\\^\\)\\%]|[\\+\\-\\/\\*\\^\\.\\(\\%]$)");

	/**
	 * Regex de un número real. <br>
	 */
	private static final Pattern NUMERO = Pattern.compile("[\\d\\.]+");

	/**
	 * Regex de símbolos de una cuenta. <br>
	 */
	private static final Pattern SIMBOLO = Pattern.compile("[\\+\\-\\*\\/\\^\\%\\(\\)]");

	/**
	 * Matcheador para los regex. <br>
	 */
	private Matcher matcher;

	/**
	 * Indica cuál es la siguiente decisión que debe intentar resolver la
	 * solicitud. <br>
	 */
	private IDecision siguienteDecision;

	/**
	 * Constructor por default. <br>
	 */
	public Calculadora() {
	}

	/**
	 * Crea una cuenta a resolver usando las características de una calculadora.
	 * <br>
	 * 
	 * @param cuenta
	 *            Cuenta a resolver. <br>
	 * @throws IllegalArgumentException
	 *             El formato de la cuenta no es correcto. <br>
	 */
	public Calculadora(final String cuenta) throws IllegalArgumentException {
		try {
			this.validarFormato(cuenta);
			this.cuenta = cuenta;
			this.corregirCuenta();
			this.iniciarVectores();
			this.extraerNumeros();
			this.extraerSimbolos();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Lee un mensaje pedido por el usuario.
	 * <p>
	 * En caso de que el mensaje pida una cuenta a resolver, esta se resuelve,
	 * sino avanza a la siguiente posible petición. <br>
	 */
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		if (mensaje.contains("cuanto es")) {
			int posicionInicial;
			if (mensaje.contains("el")) {
				posicionInicial = mensaje.indexOf("el") + 2;
			} else {
				posicionInicial = mensaje.indexOf("es") + 2;
			}
			try {
				return new String("@" + usuario + " "
						+ (int) new Calculadora(mensaje.substring(posicionInicial, mensaje.length())).resolver());
			} catch (IllegalArgumentException e) {
				return new String("@" + usuario + " " + e.getMessage());
			}
		}
		return this.siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return this.siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		this.siguienteDecision = decision;
	}

	/**
	 * Obtiene el siguiente valor de la cuenta. <br>
	 */
	private double obtenerSiguienteValor() {
		double auxiliar;
		// Compruebo si el próximo símbolo es un paréntesis. De serlo,
		// resuelvo todo lo de adentro.
		if (this.simbolos[this.indiceSimbolo + 1] == '(') {
			this.indiceSimbolo += 2;
			auxiliar = this.resolver();
		} else {
			auxiliar = this.numeros[this.indiceNumero];
			this.controlarIndices(1, 1);
		}
		return auxiliar;
	}

	/**
	 * Controla los índices. <br>
	 * 
	 * @param indiceNumero
	 *            Cantidad de posiciones a mover el índice de números. <br>
	 * @param indiceSimbolo
	 *            Cantidad de posiciones a mover el índice de símbolos. <br>
	 */
	private void controlarIndices(final int indiceNumero, final int indiceSimbolo) {
		this.indiceNumero += indiceNumero;
		this.indiceSimbolo += indiceSimbolo;
	}

	/**
	 * Resuelve la cuenta. <br>
	 * 
	 * @return Resultado. <br>
	 */
	public double resolver() {
		double resultado = 0;
		double auxiliar = 0;

		auxiliar = this.numeros[this.indiceNumero];
		this.indiceNumero++;

		while (this.indiceNumero + this.indiceSimbolo < this.cantidad) {
			switch (this.simbolos[this.indiceSimbolo]) {
			case '+':
				resultado += auxiliar;
				auxiliar = this.obtenerSiguienteValor();
				break;

			case '-':
				resultado += auxiliar;
				auxiliar = this.obtenerSiguienteValor() * (-1);
				break;

			case '*':
				auxiliar *= this.obtenerSiguienteValor();
				break;

			case '/':
				auxiliar /= this.obtenerSiguienteValor();
				break;

			case '(':
				// Para que no se pase el índice. En este caso volvemos uno
				// atrás para número.
				this.controlarIndices(-1, 1);
				auxiliar = this.resolver();
				break;

			case ')':
				// Este caso no nos interesa el break ya que nos interesa el
				// resultado adentro de los paréntesis.
				resultado += auxiliar;
				this.controlarIndices(0, 1);
				return resultado;

			case '^':
				auxiliar = Math.pow(auxiliar, this.obtenerSiguienteValor());
				break;

			// case '?': // este seria el caso de raiz, si el caracter fuera ?.
			// // Para probar, usar el test raizCuadrada
			//
			// if (this.simbolos[this.indiceSimbolo + 1] == '(') {
			// this.indiceSimbolo += 2;
			// auxiliar = Math.sqrt(this.resolver());
			// } else {
			// auxiliar = Math.sqrt(auxiliar);
			// this.controlarIndices(1, 1);
			// }
			// break;

			case '%':
				auxiliar = this.obtenerSiguienteValor() * auxiliar / 100;
				break;
			}
		}
		return resultado + auxiliar;
	}

	/**
	 * Valida el formato de la cuenta. <br>
	 * 
	 * @param cuenta
	 *            Cuenta a analizar. <br>
	 * @throws IllegalArgumentException
	 *             El formato de la cuenta no es correcto. <br>
	 */
	private void validarFormato(final String cuenta) throws IllegalArgumentException {
		int contadorAperturaParentesis = 0, contadorCierreParentesis = 0;

		// Compruebo que no haya carecteres que no sean de una cuenta.
		this.matcher = FORMATO_CUENTA.matcher(cuenta);
		if (!this.matcher.find()) {
			throw new IllegalArgumentException("Hay caracteres que no pertenecen a la sintáxis de una cuenta.");
		}

		// Comprubo que no haya errores de sintáxis.
		this.matcher = CONTROL_SINTAXIS_ERRONEA.matcher(cuenta);
		if (this.matcher.find()) {
			throw new IllegalArgumentException("Hay errores de sintaxis.");
		}

		// Compruebo que haya tantas aperturas de paréntesis como cierres.
		this.matcher = Pattern.compile("\\(").matcher(cuenta);
		while (this.matcher.find()) {
			contadorAperturaParentesis++;
		}
		this.matcher = Pattern.compile("\\)").matcher(cuenta);
		while (this.matcher.find()) {
			contadorCierreParentesis++;
		}
		if (contadorAperturaParentesis != contadorCierreParentesis) {
			throw new IllegalArgumentException("Faltan declarar paréntesis.");
		}
	}

	/**
	 * Corrige la cuenta para evitar problemas de procesamiento de números. <br>
	 */
	private void corregirCuenta() {
		this.cuenta = this.cuenta.replace(")-", ")+0-");
		this.cuenta = this.cuenta.replace("(-", "(0-");
		this.cuenta = this.cuenta.replace("++", "+");
		this.cuenta = this.cuenta.replace("+-", "-");
		this.cuenta = this.cuenta.replace("-+", "-");
		this.cuenta = this.cuenta.replace("--", "+");
		if (this.cuenta.charAt(0) == '-') {
			this.cuenta = '0' + this.cuenta;
		}
	}

	/**
	 * Inicializa los vectores auxiliares. <br>
	 */
	private void iniciarVectores() {
		this.numeros = new double[this.cuenta.length() + 1];
		this.simbolos = new char[this.cuenta.length() + 1];
	}

	/**
	 * Extrae los números de la cuenta. <br>
	 */
	private void extraerNumeros() {
		int i = 0;
		this.matcher = NUMERO.matcher(this.cuenta);
		while (this.matcher.find()) {
			this.numeros[i] = Double.parseDouble(this.matcher.group());
			i++;
		}
		this.cantidad += i;
	}

	/**
	 * Extrae los símbolos de la cuenta. <br>
	 */
	private void extraerSimbolos() {
		int i = 0;
		this.matcher = SIMBOLO.matcher(this.cuenta);
		while (this.matcher.find()) {
			this.simbolos[i] = this.matcher.group().charAt(0);
			i++;
		}
		this.cantidad += i;
	}
}
