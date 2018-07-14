package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Boliviano. <br>
 */
public class Boliviano implements Cotizacion {
	/**
	 * Regex del Boliviano. <br>
	 */
	private static final Pattern REGEX_BOLIVIANO = Pattern.compile("(?:boliviano|Boliviano)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_BOLIVIANO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("BOB");
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
