package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del peso Uruguayo. <br>
 */
public class Uruguayo implements Cotizacion {
	/**
	 * Regex del peso Uruguayo. <br>
	 */
	private static final Pattern REGEX_URUGUAYO = Pattern.compile("(?:uruguayo|Uruguayo)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_URUGUAYO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("UYU");
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
