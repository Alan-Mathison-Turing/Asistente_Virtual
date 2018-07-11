package edu.unlam.asistente.financiera;

/**
 * Interfaz que administra la cotización de una moneda. <br>
 */
public interface Cotizacion {
	/**
	 * Mensaje con el nombre de la moneda. <br>
	 * 
	 * @param moneda
	 *            Moneda a buscar. <br>
	 * @return Valor de la cotización. <br>
	 */
	public String leerMoneda(String moneda);

	/**
	 * Obtiene la siguiente cotización. <br>
	 * 
	 * @return Siguiente cotización. <br>
	 */
	public Cotizacion getSiguienteCotizacion();

	/**
	 * Establece la siguiente cotización. <br>
	 * 
	 * @param cotizacion
	 *            Cotización. <br>
	 */
	public void setSiguienteCotizacion(Cotizacion cotizacion);
}
