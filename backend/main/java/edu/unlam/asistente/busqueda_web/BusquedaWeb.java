package edu.unlam.asistente.busqueda_web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import edu.unlam.asistente.asistente_virtual.IDecision;

public class BusquedaWeb implements IDecision {
	
	private IDecision siguienteDecision;
	private final static String REGEX_WIKIPEDIA = "@\\w*\\,*\\s*(?:quien|quienes|busca|buscame|investiga|investigame|que|cual|cuales) \\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";
	private final static String REGEX_YOUTUBE = "@\\w*\\,*\\s*(?:quiero|mostra|mostrame) \\w* (?:video) \\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";

	private final static String WIKIPEDIA_URL = "https://es.wikipedia.org/wiki/";
	private final static String WIKIPEDIA_API_URL = "https://es.wikipedia.org/api/rest_v1/page/summary/";
	
	private final static String GOOGLE_KEY = "AIzaSyAXE9MOeoJ6B_57bYQajtm7NwJFS5UUSXU";
	private final static String GOOGLE_SEARCH = "https://www.googleapis.com/customsearch/v1?key=" + GOOGLE_KEY + "&cx=001277225422276918147:okw_xih_z8k&gl=ar&num=1";
	
	private final static String YOUTUBE_KEY = "AIzaSyAgnWeCMRhohRG9Af_cQNpbognP2-XkZbU";
	private final static String YOUTUBE_SEARCH = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video&videoDefinition=high&key=" + YOUTUBE_KEY + "&q=";
	private final static String YOUTUBE_EMBED_URL = "https://www.youtube.com/embed/";
	
	private HttpsURLConnection connection;
	
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
		String urlWikipedia = "";
		String contenidoArticuloWikipedia = "";
		URL url;
		
		try {
			url = new URL(WIKIPEDIA_API_URL + URLEncoder.encode(terminoBusqueda, "UTF-8"));
	        JsonParser parser = generarConexion(url);
	        
	        if(parser == null) {
	        	return "";
	        }
	        
	        while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("extract_html".equals(fieldName)){
	                	contenidoArticuloWikipedia = parser.getValueAsString();
	                }
	                if("canonical".equals(fieldName)) {
	                	urlWikipedia = WIKIPEDIA_URL + parser.getValueAsString();
	                }
	            }
	        }
	        connection.disconnect();
	        return "<a href=\"" + urlWikipedia + "\"><u>" + urlWikipedia + "</u></a><br/>" + contenidoArticuloWikipedia;
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
        return "";
	}
	
	private String voyATenerSuerte(String terminoBusqueda) {
		String urlPrimerResultado = null;
		String contenidoArticuloGoogle = null;
		URL url;
		
		try {
			url = new URL(GOOGLE_SEARCH + "&q=" + URLEncoder.encode(terminoBusqueda, "UTF-8"));
	        JsonParser parser = generarConexion(url);
	        
	        if(parser == null) {
	        	return "No encontré lo que buscabas, ¿podrías ser más específico?";
	        }
	        
	        while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("htmlSnippet".equals(fieldName)){
	                    contenidoArticuloGoogle = parser.getValueAsString();
	                }
	                if("link".equals(fieldName)) {
	                	urlPrimerResultado = parser.getValueAsString();
	                }
	                if("totalResults".equals(fieldName)) {
	                	if(parser.getValueAsString().equals("0"))
	                		connection.disconnect();
	                		return "No encontré lo que buscabas, ¿podrías ser más específico?";
	                }
	            }
	        }
	        
	        connection.disconnect();
	        
	        if(urlPrimerResultado == null) {
	        	return "No encontré lo que buscabas, ¿podrías ser más específico?";
	        } else {
	        	return "<a href=\"" + urlPrimerResultado + "\"><u>" + urlPrimerResultado + "</u></a><br/>" + contenidoArticuloGoogle;
	        }	        
	        
		} catch (IOException e) {
			e.printStackTrace();
        	return "No encontré lo que buscabas, ¿podrías ser más específico?";
		}
	}
	
	public String busquedaVideo(String terminoBusqueda) {
		return busquedaYoutube(terminoBusqueda);
	}
	
	private String busquedaYoutube(String terminoBusqueda) {
		URL url;
		String videoId = null;
		
		try {
			url = new URL(YOUTUBE_SEARCH + URLEncoder.encode(terminoBusqueda, "UTF-8"));

			JsonParser parser = generarConexion(url);
	        
	        if(parser == null) {
	        	return "No encontré el video que buscabas, ¿podrías ser más específico?";
	        }
			
	        while(!parser.isClosed()){
	        	JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("videoId".equals(fieldName)) {
	                	videoId = parser.getValueAsString();
	                	break;
	                }
	            }
	        }

	        if(videoId == null) {
	        	return "No encontré el video que buscabas, ¿podrías ser más específico?";
	        } else {
	        	return YOUTUBE_EMBED_URL + videoId + "?autoplay=1";
	        }	        
	        
		} catch (IOException e) {
			e.printStackTrace();
        	return "No encontré el video que buscabas, ¿podrías ser más específico?";
		}
	}
	
	private JsonParser generarConexion(URL url) {
		try {
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        if(connection.getResponseCode() != 200) {
	        	connection.disconnect();
	        	return null;
	        }
	        
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser = factory.createParser(connection.getInputStream());

	        return parser;
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;        
	}
	
	private String capitalizarPrimerLetra(final String palabras) {
	    return Stream.of(palabras.trim().split("\\s"))
	    .filter(word -> word.length() > 0)
	    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
	    .collect(Collectors.joining(" "));
	}
	
	
}

