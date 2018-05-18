package conversor_unidades;

import java.lang.reflect.Method;

public class ConversorUnidad {
	
	/**
	 * @param numero: cantidad a convertir.
	 * @param desde: unidad desde la que se convierte. Debe comenzar con mayuscula y ser singular.
	 * @param hasta: unidad hasta la que se convierte. Debe comenzar con mayuscula y ser singular.
	 * @return double: si procesó OK retorna conversión, si dió error retorna -1, si el numero es negativo retorna -2.
	 */
	
	public double convertirUnidad(double numero, String desde, String hasta){
		if(numero < 0) return -2;		
		try {
			// Obtengo la clase correspondiente a la unidad desde la que realizo la converión
			Class<?> clase = Class.forName(ConversorUnidad.class.getPackage().getName() + "." + desde);
			
			// Instancio un objeto de la clase deseada.
			Object magnitud = clase.newInstance();
			
			// Busco el método a utilizar para realizar la conversion a la unidad "hasta".
			Method convertir = clase.getMethod("to" + hasta, double.class);
			
			// Invoca al método pasándole la instancia de mi objeto, seguido de los parámetros del método.
			double respuesta = (double) convertir.invoke(magnitud, numero);
			
			// Devuelvo la conversion redeondeada a 2 decimales. 
			return redondearDecimales(respuesta, 2);		
		}
		catch(Exception | NoClassDefFoundError e){
			return -1;
		}

	}
	
	/**
	 * @param valorInicial: valor a redondear.
	 * @param cantidadDecimales: cantidad de decimales a las que lo voy a redondear.
	 * @return resultado: valor incial redondeado a 2 decimales.
	*/
    private double redondearDecimales(double valorInicial, int cantidadDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = (int)Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, cantidadDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, cantidadDecimales))  + parteEntera;
        return resultado;
    }

}

