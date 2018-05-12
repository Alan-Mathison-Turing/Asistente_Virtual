package conversor_unidades;

public class Masa implements Unidad{

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
		
		if(desde.contains("gramo") && hasta.contains("kilo")) {
			return gramosToKilos(numero);			
		}
		if(desde.contains("kilo") && hasta.contains("gramo")) {
			return kilosToGramos(numero);			
		}
		if(desde.contains("gramo") && hasta.contains("onza")) {
			return gramosToOnzas(numero);
		}
		if(desde.contains("onza") && hasta.contains("gramo")) {
			return onzasToGramos(numero);
		}
		else {
			return next.convertirUnidad(numero, desde, hasta);
		}
		
	}
	
	private double kilosToGramos(double numero) {
		return numero * 1000;
	}
	
	private double gramosToKilos(double numero) {
		return numero / 1000;
	}

	private double gramosToOnzas(double numero) {
		return numero * 0.035274;
	}

	private double onzasToGramos(double numero) {
		return numero * 28.3495;
	}
	
}
