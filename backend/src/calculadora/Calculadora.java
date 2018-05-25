package calculadora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import asistente_virtual.IDecision;

/**
 * Clase que administra una calculadora.
 * <p>
 * Resuelve distintos tipos de operaciones desde sumas y restas hasta potencias
 * y porcentajes. Administra distintos niveles de paréntesis para cálculos más
 * complejos. <br>
 */
public class Calculadora implements IDecision{

	private int cantidad = 0;

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
	 * Regex del formato de una cuenta.
	 * <p>
	 * Permite espacios que luego van a ser ignorados. <br>
	 */
	private static final Pattern formato_cuenta = Pattern.compile("^[\\d\\+\\-\\/\\*\\.\\^\\%\\(\\)de ]*$");

	/**
	 * Regex para controlar que no haya símbolos al principio o al final de la
	 * cuenta de manera ilegal. <br>
	 */
	private static final Pattern control_sintaxis_erronea = Pattern
			.compile("(^[\\/\\*\\^\\)\\%]|[\\+\\-\\/\\*\\^\\.\\(\\%]$)");

	/**
	 * Regex de un número real. <br>
	 */
	private static final Pattern numero = Pattern.compile("[\\d\\.]+");

	/**
	 * Regex de símbolos de una cuenta. <br>
	 */
	private static final Pattern simbolo = Pattern.compile("[\\+\\-\\*\\/\\^\\%\\(\\)]");

	/**
	 * Matcheador para los regex. <br>
	 */
	private Matcher matcher;

	
	/*
	 * Utilizado para indicar cual es el siguiente que debe intentar resolver la solicitud.
	 */
	private IDecision siguienteDecision;
	
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
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		this.cuenta = cuenta;
		this.corregirCuenta();
		this.iniciarVectores();
		this.extraerNumeros();
		this.extraerSimbolos();
	}

	// Constructor genérico para poder implementar Chain of Responsability.
	public Calculadora() {
	}
	
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		if(mensaje.contains("cuanto es")) {
			int posicionInicial;
			String respuesta = "";
			if (mensaje.contains("el"))
				posicionInicial = mensaje.indexOf("el") + 2;
			else
				posicionInicial = mensaje.indexOf("es") + 2;
			Calculadora calculadora = new Calculadora(mensaje.substring(posicionInicial, mensaje.length()));
			int resultado = (int)calculadora.resolver();
			return respuesta = "@" + usuario + " " + resultado;
		}
		return  siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
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

		while (indiceNumero + indiceSimbolo < cantidad) {
			switch (this.simbolos[this.indiceSimbolo]) {
			case '+':
				resultado += auxiliar;
				// Compruebo si el próximo símbolo es un paréntesis. De serlo,
				// resuelvo todo lo de adentro y eso lo multiplico.
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar = this.resolver();
				} else {
					auxiliar = this.numeros[this.indiceNumero];
					this.indiceNumero++;
					this.indiceSimbolo++;
				}
				break;

			case '-':
				resultado += auxiliar;
				// Compruebo si el próximo símbolo es un paréntesis. De serlo,
				// resuelvo todo lo de adentro y eso lo multiplico.
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar = this.resolver() * (-1);
				} else {
					auxiliar = this.numeros[this.indiceNumero] * (-1);
					this.indiceNumero++;
					this.indiceSimbolo++;
				}
				break;

			case '*':
				// Compruebo si el próximo símbolo es un paréntesis. De serlo,
				// resuelvo todo lo de adentro y eso lo multiplico.
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar *= this.resolver();
				} else {
					auxiliar *= this.numeros[this.indiceNumero];
					this.indiceNumero++;
					this.indiceSimbolo++;
				}
				break;

			case '/':
				// Compruebo si el próximo símbolo es un paréntesis. De serlo,
				// resuelvo todo lo de adentro y eso lo multiplico.
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar /= this.resolver();
				} else {
					auxiliar /= this.numeros[this.indiceNumero];
					this.indiceNumero++;
					this.indiceSimbolo++;
				}
				break;

			case '(':
				// Para que no se pase el índice. En este caso volvemos uno
				// atrás para número.
				this.indiceSimbolo++;
				this.indiceNumero--;
				auxiliar = this.resolver();

				break;

			case ')':
				// Este caso no nos interesa el break ya que nos interesa el
				// resultado adentro de los paréntesis.

				resultado += auxiliar;

				this.indiceSimbolo++;
				return resultado;
			case '^':

				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar = Math.pow(auxiliar, this.resolver());
				} else {
					auxiliar = Math.pow(auxiliar, this.numeros[this.indiceNumero]);
					this.indiceNumero++;
					this.indiceSimbolo++;
				}

				break;

			case '?': // este seria el caso de raiz, si el caracter fuera ?.
						// Para probar, usar el test raizCuadrada

				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar = Math.sqrt(this.resolver());
				} else {
					auxiliar = Math.sqrt(auxiliar);
					this.indiceNumero++;
					this.indiceSimbolo++;
				}

				break;

			case '%':
				if (this.simbolos[this.indiceSimbolo + 1] == '(') {
					this.indiceSimbolo += 2;
					auxiliar = this.resolver() * auxiliar / 100;
				} else {
					auxiliar = this.numeros[this.indiceNumero] * auxiliar / 100;
					this.indiceNumero++;
					this.indiceSimbolo++;
				}
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
		this.matcher = formato_cuenta.matcher(cuenta);
		if (!this.matcher.find()) {
			throw new IllegalArgumentException("Hay caracteres que no pertenecen a la sintáxis de una cuenta.");
		}

		// Comprubo que no haya errores de sintáxis.
		this.matcher = control_sintaxis_erronea.matcher(cuenta);
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
		cuenta = cuenta.replace(")-", ")+0-");
		cuenta = cuenta.replace("(-", "(0-");
		cuenta = cuenta.replace("++", "+");
		cuenta = cuenta.replace("+-", "-");
		cuenta = cuenta.replace("-+", "-");
		cuenta = cuenta.replace("--", "+");
		if (cuenta.charAt(0) == '-') {
			cuenta = '0' + cuenta;
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
		this.matcher = numero.matcher(this.cuenta);
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
		this.matcher = simbolo.matcher(this.cuenta);
		while (this.matcher.find()) {
			this.simbolos[i] = this.matcher.group().charAt(0);
			i++;
		}
		this.cantidad += i;
	}

}
