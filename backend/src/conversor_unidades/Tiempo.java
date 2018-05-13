package conversor_unidades;

public class Tiempo implements IUnidad {

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
		if(desde.matches("(?:segundo|minuto|hora|dia)(\\w?)") && hasta.matches("(?:segundo|minuto|hora|dia)(\\w?)")) {
			if(desde.contains("segundo") && hasta.contains("minuto")) {
				return segundosToMinutos(numero);
			}
			if(desde.contains("minuto") && hasta.contains("segundo")) {
				return minutosToSegundos(numero);
			}
			if(desde.contains("segundo") && hasta.contains("hora")) {
				return segundosToHoras(numero);
			}
			if(desde.contains("hora") && hasta.contains("segundo")) {
				return horasToSegundos(numero);
			}
			if(desde.contains("segundo") && hasta.contains("dia")) {
				return segundosToDias(numero);
			}
			if(desde.contains("dia") && hasta.contains("segundo")) {
				return diasToSegundos(numero);
			}
			if(desde.contains("minuto") && hasta.contains("hora")) {
				return minutosToHoras(numero);
			}
			if(desde.contains("hora") && hasta.contains("minuto")) {
				return horasToMinutos(numero);
			}
			if(desde.contains("minuto") && hasta.contains("dia")) {
				return minutosToDias(numero);
			}
			if(desde.contains("dia") && hasta.contains("minuto")) {
				return diasToMinutos(numero);
			}
			if(desde.contains("hora") && hasta.contains("dia")) {
				return horasToDias(numero);
			}
			if(desde.contains("dia") && hasta.contains("hora")) {
				return diasToHoras(numero);
			}	
		}
		return next.convertirUnidad(numero, desde, hasta);
	}
	
	private double segundosToMinutos(double numero) {
		return numero / 60;
	}

	private double minutosToSegundos(double numero) {
		return numero * 60;
	}
	
	private double segundosToHoras(double numero) {
		return numero / 3600;
	}

	private double horasToSegundos(double numero) {
		return numero * 3600;
	}
	
	private double segundosToDias(double numero) {
		return numero / 86400;
	}
	
	private double diasToSegundos(double numero) {
		return numero * 86400;
	}
	
	private double minutosToHoras(double numero) {
		return numero / 60;
	}

	private double horasToMinutos(double numero) {
		return numero * 60;
	}
	
	private double minutosToDias(double numero) {
		return numero / 1440;
	}

	private double diasToMinutos(double numero) {
		return numero * 1440;
	}
	
	private double horasToDias(double numero) {
		return numero / 24;
	}
	
	private double diasToHoras(double numero) {
		return numero * 24;
	}
	
}
