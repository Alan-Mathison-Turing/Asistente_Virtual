package conversor_unidades;

import java.text.DecimalFormat;

public class ConversorUnidades implements Unidad {

	private Unidad next;
	public ConversorUnidades() {

	}
	
	public double convertirUnidad(double numero, String desde, String hasta){
		Masa masa = new Masa();
		Longitud longitud = new Longitud();
		this.setNext(masa);
		masa.setNext(longitud);
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
