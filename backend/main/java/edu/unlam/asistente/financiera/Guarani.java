package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Guarani. <br>
 */
public class Guarani implements Cotizacion {
	/**
	 * Regex del Guarani. <br>
	 */
	private static final Pattern REGEX_GUARANI = Pattern.compile("(?:guarani|Guarani)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_GUARANI.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("PYG");
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
