package edu.unlam.asistente.busqueda_web;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/*
 * Clase en la cual se buscará el resultado del terminoBusqueda que se envió
 * y retornará el link al artículo junto con un breve extracto.
 */

public class Wikipedia {

	private final static String WIKIPEDIA_URL = "https://es.wikipedia.org/wiki/";
	private final static String WIKIPEDIA_API_URL = "https://es.wikipedia.org/api/rest_v1";
	private final static String REGEX = "\\w*html\\/(\\w*)";
	private String titulo;
	private String urlWikipedia;
	private String contenidoArticulo;

	public String busqueda(String terminoBusqueda) {
		return obtenerArticulo(terminoBusqueda);
	}
	
	private String obtenerArticulo(String terminoBusqueda) {
		if(obtenerTituloCompleto(terminoBusqueda) != 200) return "";
		urlWikipedia = WIKIPEDIA_URL + titulo;
		URL url;
		String uri = "/page/summary/";
		
		try {
			url = new URL(WIKIPEDIA_API_URL + uri + titulo);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setRequestProperty("profile", "https://www.mediawiki.org/wiki/Specs/Summary/1.3.6");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser = factory.createParser(connection.getInputStream());
	        
	        while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("extract".equals(fieldName)){
	                    contenidoArticulo = parser.getValueAsString();
	                }
	            }
	        }
	        
	        connection.disconnect();
	        
	        return this.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private int obtenerTituloCompleto(String terminoBusqueda) {
        URL url;
        String uri = "/page/html/";
        
		try {
			url = new URL(WIKIPEDIA_API_URL + uri + terminoBusqueda.replaceAll(" ", "_"));
	        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "text/xml");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setRequestProperty("profile", "https://www.mediawiki.org/wiki/Specs/HTML/1.7.0");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        if(connection.getResponseCode() == 200) {
				Pattern patron =  Pattern.compile(REGEX);
				Matcher matcher = patron.matcher(connection.toString());
				matcher.find();
				titulo = matcher.group(1);
				return connection.getResponseCode();
	        }else {
	        	return connection.getResponseCode();
	        }
 			
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public String toString() {
		return urlWikipedia + "\n" + contenidoArticulo;
	}
	
}
