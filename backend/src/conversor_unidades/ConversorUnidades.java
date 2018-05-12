package conversor_unidades;

import java.text.DecimalFormat;

/*
 * Como usuario quiero pedirle al asistente conversiones de unidades de medida 
 * para agilizar mis cálculos. 25pts. Debe incluir al menos 3 unidades de cada 
 * tipo: masa, longitud, capacidad y tiempo tanto del Sistema Internacional como del 
 * Sistema Imperial.
 */

public class ConversorUnidades implements Unidad {

	private Unidad next;
	public ConversorUnidades() {

	}
	
	public double convertirUnidad(double numero, String desde, String hasta){
		Masa masa = new Masa();
		this.setNext(masa);
		return next.convertirUnidad(numero, desde, hasta);
	}
	
	@Override
	public void setNext(Unidad unidad) {
		next = unidad;
	}

	@Override
	public Unidad getNext() {
		return next;
	}

}
