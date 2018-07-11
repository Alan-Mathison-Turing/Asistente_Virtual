package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Sol. <br>
 */
public class Sol implements Cotizacion {
	/**
	 * Regex del Sol. <br>
	 */
	private static final Pattern REGEX_SOL = Pattern.compile("(?:sol|Sol)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_SOL.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("PEN");
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
