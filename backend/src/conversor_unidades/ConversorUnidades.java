package conversor_unidades;

import java.text.DecimalFormat;

/*
 * Como usuario quiero pedirle al asistente conversiones de unidades de medida 
 * para agilizar mis cálculos. 25pts. Debe incluir al menos 3 unidades de cada 
 * tipo: masa, longitud, capacidad y tiempo tanto del Sistema Internacional como del 
 * Sistema Imperial.
 */

public class ConversorUnidades {

	private String desde;
	private String hasta;
	private double numero;
	
	public ConversorUnidades(double numero, String mensaje) {
		this.numero = numero;
		String[] palabras = mensaje.split(" ");
		this.desde = palabras[0];
		this.hasta = palabras[palabras.length - 1];
	}
	
	public String convertirUnidad(){
		DecimalFormat df = new DecimalFormat("#.00");
		if(this.desde.contains("gramo") && this.hasta.endsWith("kilo")){
			return " " + df.format(this.numero) + " kilo equivale a " + df.format(gramosToKilos()) + " gramos";			
		}

		return "";
	}
	
	private double kilosToGramos() {
		return this.numero / 1000;
	}
	
	private double gramosToKilos() {
		return this.numero * 1000;
	}

	private double gramosToOnzas() {
		return this.numero * 0.035274;
	}
	
}
