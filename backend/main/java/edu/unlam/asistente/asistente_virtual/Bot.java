package edu.unlam.asistente.asistente_virtual;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.busqueda_web.BusquedaWeb;
import edu.unlam.asistente.calculadora.Calculadora;
import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.chucknorris.ChuckNorrisFacts;
import edu.unlam.asistente.conversor_unidades.ConversorUnidad;
import edu.unlam.asistente.cordialidad.Cordialidad;
import edu.unlam.asistente.leyes_robotica.LeyesRobotica;
import edu.unlam.asistente.recordatorioEventos.GestionRecordatorio;



public class Bot implements IDecision {

	
	private String nombre;
	private IDecision siguienteDecision;
	
	public final static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @%s ¿podrás repetirlo?";
	
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
		
		return "https://media0.giphy.com/media/l41m0CPz6UCnaUmxG/giphy.gif";
		/*
		try {
			Cordialidad cordialidad = new Cordialidad();
			LeyesRobotica leyesRobotica = new LeyesRobotica();
			ChuckNorrisFacts chuckFacts = new ChuckNorrisFacts();
			Calculadora calculadora = new Calculadora();
			ConversorUnidad conversorUnidad = new ConversorUnidad();
			Calendario calendario = new Calendario();
			GestionRecordatorio gestionRecordatorio = new GestionRecordatorio();
			BusquedaWeb busquedaWeb = new BusquedaWeb();
			this.setSiguienteDecision(cordialidad);
			cordialidad.setSiguienteDecision(leyesRobotica);
			leyesRobotica.setSiguienteDecision(chuckFacts);
			chuckFacts.setSiguienteDecision(calculadora);
			calculadora.setSiguienteDecision(gestionRecordatorio);
			gestionRecordatorio.setSiguienteDecision(conversorUnidad);
			conversorUnidad.setSiguienteDecision(busquedaWeb);
			busquedaWeb.setSiguienteDecision(calendario);
			return siguienteDecision.leerMensaje(mensaje, usuario);
		}
		catch(Exception e) {
			return String.format(MSG_NO_ENTIENDO, usuario);
		}*/

	}
	
	// Obtiene el número contenido en un mensaje que matchee el regex.
	public static double obtenerNumero(String mensaje, Pattern formato_numero) {
		Matcher matcher = formato_numero.matcher(mensaje);
		matcher.find();
		return Double.parseDouble(matcher.group());
	}
	
	// Remueve los tildes del String que recibe.
	private String removerTildes(String texto) {
	    String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
	    // Cadena de caracteres ASCII que reemplazarÃ¡n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto;
	    for (int i = 0; i < original.length(); i++) {
	    // Reemplazamos los caracteres especiales.
	    	output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}	
	
}