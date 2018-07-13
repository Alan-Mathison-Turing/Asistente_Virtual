package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del peso Colombiano. <br>
 */
public class Colombiano implements Cotizacion {
	/**
	 * Regex del peso Colombiano. <br>
	 */
	private static final Pattern REGEX_COLOMBIANO = Pattern.compile("(?:colombiano|Colombiano)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_COLOMBIANO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("COP");
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
