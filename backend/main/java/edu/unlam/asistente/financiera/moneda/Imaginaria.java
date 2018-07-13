package edu.unlam.asistente.financiera.moneda;

/**
 * Clase que administra la cotización de una moneda imaginaria.
 * <p>
 * <i><b>Nota:</b> Esta clase es solo para indicar que la moneda solicitada no
 * existe, no cumple otro propósito.</i>
 */
public class Imaginaria implements Cotizacion {
	/**
	 * Mensaje a mostrar cuando no existe una moneda específica. <br>
	 */
	final static String MSG_NO_MONEDA = "un monto no conocido ya que no se trata de una moneda que se encuentra disponible.";
	/**
	 * Siguiente cotización. <br>
	 */
	private Cotizacion siguienteCotizacion;

	@Override
	public String leerMoneda(String moneda) {
		return MSG_NO_MONEDA;
	}

	@Override
	public Cotizacion getSiguienteCotizacion() {
		return this.siguienteCotizacion;
	}

	@Override
	public void setSiguienteCotizacion(Cotizacion cotizacion) {
		throw new RuntimeException("No se puede agregar otra moneda.");
	}
}
