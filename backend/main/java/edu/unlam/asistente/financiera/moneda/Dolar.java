package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Dolar. <br>
 */
public class Dolar implements Cotizacion {
	/**
	 * Regex del Dolar. <br>
	 */
	private static final Pattern REGEX_DOLAR = Pattern.compile("(?:dolar|Dolar)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_DOLAR.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("USD");
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
