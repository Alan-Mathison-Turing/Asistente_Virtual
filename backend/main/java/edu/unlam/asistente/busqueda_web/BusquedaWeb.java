package edu.unlam.asistente.busqueda_web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.unlam.asistente.asistente_virtual.IDecision;

public class BusquedaWeb implements IDecision {
	
	private IDecision siguienteDecision;
	private final static String REGEX_WIKIPEDIA = "@\\w*\\,*\\s*(?:quien|quienes|busca|buscame|investiga|investigame|que|cual|cuales) \\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";
	private final static String REGEX_YOUTUBE = "@\\w*\\,*\\s*(?:quiero|mostra|mostrame) \\w* (?:video) \\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern patronWikipedia =  Pattern.compile(REGEX_WIKIPEDIA);
		Matcher matcherWikipedia = patronWikipedia.matcher(mensaje);

		Pattern patronYoutube =  Pattern.compile(REGEX_YOUTUBE);
		Matcher matcherYoutube = patronYoutube.matcher(mensaje);
		
		if(mensaje.matches(REGEX_WIKIPEDIA)) {
			matcherWikipedia.find();
			String terminoBusqueda = capitalizarPrimerLetra(matcherWikipedia.group(1));
			if(terminoBusqueda.equals("")) return siguienteDecision.leerMensaje(mensaje, usuario);
			return busquedaWeb(terminoBusqueda);
		}
		
		if(mensaje.matches(REGEX_YOUTUBE)) {
			matcherYoutube.find();
			String terminoBusqueda = capitalizarPrimerLetra(matcherYoutube.group(1));
			if(terminoBusqueda.equals("")) return siguienteDecision.leerMensaje(mensaje, usuario);
			return busquedaVideo(terminoBusqueda);
		}
		
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}
	
	public String busquedaWeb(String terminoBusqueda) {
		String respuesta = busquedaWikipedia(terminoBusqueda);
		return respuesta != "" ? respuesta : voyATenerSuerte(terminoBusqueda);
	}
	
	private String busquedaWikipedia(String terminoBusqueda) {
		Wikipedia wikipedia = new Wikipedia();
		return wikipedia.busqueda(terminoBusqueda);
	}
	
	private String voyATenerSuerte(String terminoBusqueda) {
		Google google = new Google();
		return google.busqueda(terminoBusqueda);
	}
	
	public String busquedaVideo(String terminoBusqueda) {
		Youtube youtube = new Youtube();
		return youtube.busqueda(terminoBusqueda);
	}
	
	private String capitalizarPrimerLetra(final String palabras) {
	    return Stream.of(palabras.trim().split("\\s"))
	    .filter(word -> word.length() > 0)
	    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
	    .collect(Collectors.joining(" "));
	}

}
