package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización del peso Mexicano. <br>
 */
public class Mexicano implements Cotizacion {
	/**
	 * Regex del peso Mexicano. <br>
	 */
	private static final Pattern REGEX_MEXICANO = Pattern.compile("(?:mexicano|Mexicano)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_MEXICANO.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("MXN");
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
