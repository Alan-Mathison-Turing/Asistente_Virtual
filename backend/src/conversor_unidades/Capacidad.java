package conversor_unidades;

public class Capacidad implements IUnidad {

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
		if(desde.matches("(?:cm3|litro|galon|pie)(\\w*)") && hasta.matches("(?:cm3|litro|galon|pie)(\\w*)")){
			if(desde.contains("litro") && hasta.contains("cm3")) {
				return litrosToCm3(numero);
			}
			if(desde.contains("cm3") && hasta.contains("litro")) {
				return cm3ToLitros(numero);
			}		
			if(desde.contains("litro") && hasta.contains("galon")) {
				return litrosToGalon(numero);
			}		
			if(desde.contains("galon") && hasta.contains("litro")) {
				return galonToLitros(numero);
			}		
			if(desde.contains("cm3") && hasta.contains("galon")) {
				return cm3ToGalon(numero);
			}		
			if(desde.contains("galon") && hasta.contains("cm3")) {
				return galonToCm3(numero);
			}			
		}
		return next.convertirUnidad(numero, desde, hasta);
	}
	
	private double litrosToCm3(double numero) {
		return numero * 1000;
	}
	
	private double cm3ToLitros(double numero) {
		return numero / 1000;
	}
	
	private double litrosToGalon(double numero) {
		return numero / 4.54609;
	}
	
	private double galonToLitros(double numero) {
		return numero * 4.54609;
	}
	
	private double cm3ToGalon(double numero) {
		return numero / 4546.09;
	}
	
	private double galonToCm3(double numero) {
		return numero * 4546.09;
	}

}
