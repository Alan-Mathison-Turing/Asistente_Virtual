package edu.unlam.asistente.asistente_virtual;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.calculadora.Calculadora;
import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.conversor_unidades.ConversorUnidad;
import edu.unlam.asistente.cordialidad.Cordialidad;


public class Bot implements IDecision {

	
	private String nombre;
	private IDecision siguienteDecision;
	
	public final static String USUARIO = "delucas";	
	public final static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @" + USUARIO + " Â¿podrÃ¡s repetirlo?";
	
	public Bot(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		mensaje = removerTildes(mensaje.toLowerCase());
		
		try {
			Cordialidad cordialidad = new Cordialidad();
			Calculadora calculadora = new Calculadora();
			ConversorUnidad conversorUnidad = new ConversorUnidad();
			Calendario calendario = new Calendario();
			this.setSiguienteDecision(cordialidad);
			cordialidad.setSiguienteDecision(calculadora);
			calculadora.setSiguienteDecision(conversorUnidad);
			conversorUnidad.setSiguienteDecision(calendario);
			return siguienteDecision.leerMensaje(mensaje, usuario);
		}
		catch(Exception e) {
			return MSG_NO_ENTIENDO;
		}

	}
	
	// Obtiene el nÃºmero contenido en un mensaje que matchee el regex.
	public static double obtenerNumero(String mensaje, Pattern formato_numero) {
		Matcher matcher = formato_numero.matcher(mensaje);
		matcher.find();
		return Double.parseDouble(matcher.group());
	}
	
	/*
	//Obtiene el string contenido en un mensaje que matchee el regex (2/06)
	public static String obtenerString (String mensaje, Pattern formato_cadena) {
		Matcher matcher = formato_cadena.matcher(mensaje);
		matcher.find();
		return (matcher.group());
	} 
	*/
	
	// Remueve los tildes del String que recibe.
	private String removerTildes(String texto) {
	    String original = "Ã€Ã�Ã‚ÃƒÃ„Ã…Ã†Ã‡ÃˆÃ‰ÃŠÃ‹ÃŒÃ�ÃŽÃ�Ã�Ã‘Ã’Ã“Ã”Ã•Ã–Ã˜Ã™ÃšÃ›ÃœÃ�ÃŸÃ Ã¡Ã¢Ã£Ã¤Ã¥Ã¦Ã§Ã¨Ã©ÃªÃ«Ã¬Ã­Ã®Ã¯Ã°Ã±Ã²Ã³Ã´ÃµÃ¶Ã¸Ã¹ÃºÃ»Ã¼Ã½Ã¿";
	    // Cadena de caracteres ASCII que reemplazarÃƒÂ¡n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto;
	    for (int i = 0; i < original.length(); i++) {
	    // Reemplazamos los caracteres especiales.
	    	output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}	
	
}