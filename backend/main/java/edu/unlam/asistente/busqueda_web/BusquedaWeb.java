package edu.unlam.asistente.busqueda_web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.unlam.asistente.asistente_virtual.IDecision;

public class BusquedaWeb implements IDecision {
	
	private IDecision siguienteDecision;
	private final static String REGEX = "@\\w*\\,*\\s*(?:quien|quienes|busca|buscame|investiga|investigame|que|cual|cuales) \\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern patron =  Pattern.compile(REGEX);
		Matcher matcher = patron.matcher(mensaje);

		if(mensaje.matches(REGEX)) {
			matcher.find();
			String terminoBusqueda = capitalizarPrimerLetra(matcher.group(1));
			if(terminoBusqueda.equals("")) return siguienteDecision.leerMensaje(mensaje, usuario);
			return busquedaWeb(terminoBusqueda);
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
	
	private String capitalizarPrimerLetra(final String palabras) {
	    return Stream.of(palabras.trim().split("\\s"))
	    .filter(word -> word.length() > 0)
	    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
	    .collect(Collectors.joining(" "));
	}

}
