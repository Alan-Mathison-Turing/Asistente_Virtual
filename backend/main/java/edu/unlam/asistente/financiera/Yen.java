package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Yen. <br>
 */
public class Yen implements Cotizacion {
	/**
	 * Regex del Yen. <br>
	 */
	private static final Pattern REGEX_YEN = Pattern.compile("(?:yen|Yen)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_YEN.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("JPY");
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
