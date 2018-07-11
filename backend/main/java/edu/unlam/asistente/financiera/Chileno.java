package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del peso Chileno. <br>
 */
public class Chileno implements Cotizacion {
	/**
	 * Regex del peso Chileno. <br>
	 */
	private static final Pattern REGEX_CHILENO = Pattern.compile("(?:chileno|Chileno)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_CHILENO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("CLP");
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
