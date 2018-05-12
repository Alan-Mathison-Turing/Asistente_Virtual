package conversor_unidades;

public class Longitud implements Unidad{

	private Unidad next;
	
	@Override
	public void setNext(Unidad unidad) {
		next = unidad;		
	}

	@Override
	public Unidad getNext() {
		return next;
	}

	@Override
	public double convertirUnidad(double numero, String desde, String hasta) {
		if(desde.contains("metro") && hasta.contains("centimetro")) {
			return metrosToCentimetros(numero);
		}
		else {
			return next.convertirUnidad(numero, desde, hasta);
		}
	}
	
	private double metrosToCentimetros(double numero) {
		return numero * 100;
	}

}
