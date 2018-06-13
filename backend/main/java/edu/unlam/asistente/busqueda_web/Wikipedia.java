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
 * y retornará el link al artículo en wikipedia junto con un breve extracto.
 */

public class Wikipedia {

	private final static String WIKIPEDIA_URL = "https://es.wikipedia.org/wiki/";
	private final static String WIKIPEDIA_API_URL = "https://es.wikipedia.org/api/rest_v1";
	private String urlWikipedia;
	private String contenidoArticulo;

	public String busqueda(String terminoBusqueda) {
		return obtenerArticulo(terminoBusqueda);
	}
	
	private String obtenerArticulo(String terminoBusqueda) {
		URL url;
		String uri = "/page/summary/";
		
		try {
			url = new URL(WIKIPEDIA_API_URL + uri + terminoBusqueda);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setRequestProperty("profile", "https://www.mediawiki.org/wiki/Specs/Summary/1.3.6");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        if(connection.getResponseCode() != 200) {
	        	connection.disconnect();
	        	return "";
	        }
	        
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser = factory.createParser(connection.getInputStream());
	        
	        while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("extract_html".equals(fieldName)){
	                    contenidoArticulo = parser.getValueAsString();
	                }
	                if("canonical".equals(fieldName)) {
	                	urlWikipedia = WIKIPEDIA_URL + parser.getValueAsString();
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
	

	
	@Override
	public String toString() {
		return urlWikipedia + "\n" + contenidoArticulo;
	}
	
}
