package edu.unlam.asistente.financiera;

import java.util.regex.Pattern;

/**
 * Clase que administra la cotización de la Libra esterlina. <br>
 */
public class Libra implements Cotizacion {
	/**
	 * Regex de la Libra. <br>
	 */
	private static final Pattern REGEX_LIBRA = Pattern
			.compile("(?:libra|Libra|libra esterlina|Libra esterlina|Libra Esterlina)");
	/**
	 * Indica cuál es la siguiente cotización que debe intentar resolver la
	 * solicitud. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		if (REGEX_LIBRA.matcher(moneda).find()) {
			return Moneda.obtenerCotizacionActual("GBP");
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
