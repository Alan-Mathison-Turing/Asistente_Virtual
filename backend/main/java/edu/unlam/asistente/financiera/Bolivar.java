package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Bolivar. <br>
 */
public class Bolivar implements Cotizacion {
	/**
	 * Regex del Bolivar. <br>
	 */
	private static final Pattern REGEX_BOLIVAR = Pattern.compile("(?:bolivar|Bolivar)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_BOLIVAR.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("VEF");
		}
		return this.siguienteCotizacion.leerMoneda(moneda);
	}

	@Override
	public Cotizacion getSiguienteCotizacion() {
		return this.siguienteCotizacion;
	}

	@Override
	public void setSiguienteCotizacion(Cotizacion cotizacion) {
		this.siguienteCotizacion = cotizacion;
	}
}
