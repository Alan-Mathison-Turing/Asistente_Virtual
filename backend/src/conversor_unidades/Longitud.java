package conversor_unidades;

public class Longitud implements IUnidad{

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
		if(desde.matches("(?:centimetro|metro|pulgada|pie)(\\w?)") && hasta.matches("(?:centimetro|metro|pulgada|pie)(\\w?)")) {
			if(desde.contains("metro") && hasta.contains("centimetro")) {
				return metrosToCentimetros(numero);
			}
			if(desde.contains("centimetro") && hasta.contains("metro")) {
				return centimetrosToMetros(numero);
			}
			if(desde.contains("pulgada") && hasta.contains("centimetro")) {
				return pulgadasToCentimetros(numero);
			}
			if(desde.contains("centimetro") && hasta.contains("pulgada")) {
				return centimetrosToPulgadas(numero);
			}
			if(desde.contains("pie") && hasta.contains("centimetro")) {
				return piesToCentimetros(numero);
			}
			if(desde.contains("centimetro") && hasta.contains("pie")) {
				return centimetrosToPies(numero);
			}		
			if(desde.contains("metro") && hasta.contains("pie")) {
				return metrosToPies(numero);
			}
			if(desde.contains("pie") && hasta.contains("metro")) {
				return piesToMetros(numero);
			}
			if(desde.contains("metro") && hasta.contains("pulgada")) {
				return metrosToPulgadas(numero);
			}
			if(desde.contains("pulgada") && hasta.contains("metro")) {
				return pulgadasToMetros(numero);
			}			
		}
		return next.convertirUnidad(numero, desde, hasta);
	}
	
	private double metrosToCentimetros(double numero) {
		return numero * 100;
	}

	private double centimetrosToMetros(double numero) {
		return numero / 100;
	}
	
	private double pulgadasToCentimetros(double numero) {
		return numero * 2.54;
	}
	
	private double centimetrosToPulgadas(double numero) {
		return numero / 2.54;
	}
	
	private double piesToCentimetros(double numero) {
		return numero * 30.48;
	}

	private double centimetrosToPies(double numero) {
		return numero / 30.48;
	}
	
	private double metrosToPies(double numero) {
		return numero / 0.3048;
	}
	
	private double piesToMetros(double numero) {
		return numero * 0.3048;
	}

	private double metrosToPulgadas(double numero) {
		return numero * 39.3701;
	}	
	
	private double pulgadasToMetros(double numero) {
		return numero / 39.3701;
	}

}
