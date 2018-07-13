package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del Yuan. <br>
 */
public class Yuan implements Cotizacion {
	/**
	 * Regex del Real. <br>
	 */
	private static final Pattern REGEX_YUAN = Pattern.compile("(?:yuan|Yuan)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_YUAN.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("CNY");
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
