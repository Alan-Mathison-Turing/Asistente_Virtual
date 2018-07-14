package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Rublo. <br>
 */
public class Rublo implements Cotizacion {
	/**
	 * Regex del Rublo. <br>
	 */
	private static final Pattern REGEX_RUBLO = Pattern.compile("(?:rublo|Rublo)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_RUBLO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("RUB");
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
