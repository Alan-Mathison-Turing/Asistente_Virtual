package conversor_unidades;

public class Masa implements IUnidad{

	private IUnidad next;
	
	@Override
	public void setNextUnidad(IUnidad unidad) {
		next = unidad;
	}

	@Override
	public IUnidad getNextUnidad() {
		return next;
	}

	@Override
	public double convertirUnidad(double numero, String desde, String hasta) {	
		if(desde.matches("(?:kilo|gramo|tonelada|onza)(\\w?)") && hasta.matches("(?:kilo|gramo|tonelada|onza)(\\w?)")){
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
			if(desde.contains("kilo") && hasta.contains("tonelada")) {
				return kilosToToneladas(numero);
			}
			if(desde.contains("tonelada") && hasta.contains("kilo")) {
				return toneladasToKilos(numero);
			}
			if(desde.contains("gramo") && hasta.contains("tonelada")) {
				return gramosToToneladas(numero);
			}
			if(desde.contains("tonelada") && hasta.contains("gramo")) {
				return toneladasToGramos(numero);
			}
			if(desde.contains("onza") && hasta.contains("tonelada")) {
				return onzasToToneladas(numero);
			}
			if(desde.contains("tonelada") && hasta.contains("onza")) {
				return toneladasToOnzas(numero);
			}		
		}
		return next.convertirUnidad(numero, desde, hasta);
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
	
	private double kilosToToneladas(double numero) {
		return numero / 1000;
	}

	private double toneladasToKilos(double numero) {
		return numero * 1000;
	}

	private double gramosToToneladas(double numero) {
		return numero / 1000000;
	}

	private double toneladasToGramos(double numero) {
		return numero * 1000000;
	}
	
	private double onzasToToneladas(double numero) {
		return numero / 35274;
	}

	private double toneladasToOnzas(double numero) {
		return numero * 35274;
	}
	
}
