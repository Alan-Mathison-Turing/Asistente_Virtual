package edu.unlam.asistente.conversor_unidades;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.asistente_virtual.IDecision;

public class ConversorUnidad implements IDecision {
	
	private IDecision siguienteDecision;
	private final static String REGEX = "@\\w*\\,*\\s*(?:cuantas|cuantos) (\\w*\\.*) (?:son|hay en) (\\d*\\.*\\d) (\\w*\\.*)(?:\\s* \\?|\\.*\\s*\\?)?"; 
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern patron =  Pattern.compile(REGEX);
		Matcher matcher = patron.matcher(mensaje);

		if(mensaje.matches(REGEX)) {
			String respuesta = "";
			
			DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
			simbolo.setDecimalSeparator(',');
			DecimalFormat df = new DecimalFormat("#0.00", simbolo);
			matcher.find();
			String hasta = matcher.group(1);
			double numero = Double.parseDouble(matcher.group(2));
			String desde = matcher.group(3);
			double resultado = convertirUnidad(numero, diccionario(desde), diccionario(hasta));
			
			if(resultado == -1) return Bot.MSG_NO_ENTIENDO;
			if(resultado == -2) return "@" + usuario + " las magnitudes no pueden ser negativas.";
			return respuesta = "@" + usuario + " " + df.format(numero) + " " + desde + 
						" equivale a " + df.format(resultado) + " " + hasta;				
		}
		return  siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}
	
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

	
	/**
	 * @param palara: palabra a evaluar.
	 * @return string: retorna palabra asociada en el HashMap si existe.
	 */
	private String diccionario(String palabra) {
	    String singular;
	    HashMap<String,String> diccionario = new HashMap<>();
	    diccionario.put("gr","Gramo");
	    diccionario.put("gr.","Gramo");
	    diccionario.put("grs","Gramo");
	    diccionario.put("grs.","Gramo");
	    diccionario.put("gramo","Gramo");
	    diccionario.put("gramos","Gramo");
	    diccionario.put("kilo","Kilo");
	    diccionario.put("kilos","Kilo");
	    diccionario.put("kg","Kilo");
	    diccionario.put("kg.","Kilo");
	    diccionario.put("kilogramo","Kilo");
	    diccionario.put("kilogramos","Kilo");
	    diccionario.put("onza","Onza");
	    diccionario.put("onzas","Onza");
	    diccionario.put("tonelada","Tonelada");
	    diccionario.put("toneladas","Tonelada");
	    diccionario.put("cc","Cm3");
	    diccionario.put("cc.","Cm3");
	    diccionario.put("cm3","Cm3");
	    diccionario.put("cm3.","Cm3");
	    diccionario.put("centimetro3","Cm3");
	    diccionario.put("centimetros3","Cm3");
	    diccionario.put("gal","Galon");
	    diccionario.put("gal.","Galon");
	    diccionario.put("galon","Galon");
	    diccionario.put("galones","Galon");
	    diccionario.put("lt","Litro");
	    diccionario.put("lt.","Litro");
	    diccionario.put("lts","Litro");
	    diccionario.put("lts.","Litro");
	    diccionario.put("litro","Litro");
	    diccionario.put("litros","Litro");
	    diccionario.put("mm","Milimetro");
	    diccionario.put("mm.","Milimetro");
	    diccionario.put("mms","Milimetro");
	    diccionario.put("mms.","Milimetro");
	    diccionario.put("milimetro","Milimetro");
	    diccionario.put("milimetros","Milimetro");
	    diccionario.put("cm","Centimetro");
	    diccionario.put("cm.","Centimetro");
	    diccionario.put("cms","Centimetro");
	    diccionario.put("cms.","Centimetro");
	    diccionario.put("centimetro","Centimetro");
	    diccionario.put("centimetros","Centimetro");
	    diccionario.put("mt","Metro");
	    diccionario.put("mt.","Metro");
	    diccionario.put("mts","Metro");
	    diccionario.put("mts.","Metro");
	    diccionario.put("metro","Metro");
	    diccionario.put("metros","Metro");
	    diccionario.put("km","Kilometro");
	    diccionario.put("km.","Kilometro");
	    diccionario.put("kms","Kilometro");
	    diccionario.put("kms.","Kilometro");
	    diccionario.put("kilometro","Kilometro");
	    diccionario.put("kilometros","Kilometro");
	    diccionario.put("pie","Pie");
	    diccionario.put("pies","Pie");
	    diccionario.put("plg","Pulgada");
	    diccionario.put("plg.","Pulgada");
	    diccionario.put("pulg","Pulgada");
	    diccionario.put("pulg.","Pulgada");
	    diccionario.put("pulgada","Pulgada");
	    diccionario.put("pulgadas","Pulgada");
	    diccionario.put("seg","Segundo");
	    diccionario.put("seg.","Segundo");
	    diccionario.put("segs","Segundo");
	    diccionario.put("segs.","Segundo");
	    diccionario.put("segundo","Segundo");
	    diccionario.put("segundos","Segundo");
	    diccionario.put("min","Minuto");
	    diccionario.put("min.","Minuto");
	    diccionario.put("mins","Minuto");
	    diccionario.put("mins.","Minuto");
	    diccionario.put("minuto","Minuto");
	    diccionario.put("minutos","Minuto");
	    diccionario.put("h","Hora");
	    diccionario.put("h.","Hora");
	    diccionario.put("hs","Hora");
	    diccionario.put("hs.","Hora");
	    diccionario.put("hora","Hora");
	    diccionario.put("horas","Hora");
	    diccionario.put("dia","Dia");
	    diccionario.put("dias","Dia");
	    singular = diccionario.get(palabra);
	    return singular != null ? singular : palabra;
	}
	
}

