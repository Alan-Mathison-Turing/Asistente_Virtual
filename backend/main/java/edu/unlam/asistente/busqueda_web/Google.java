package edu.unlam.asistente.busqueda_web;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/*
 * Clase utilizada para retornar la primer página encontrada para el término de búsqueda.
 * Cumple la misma función que el botón "Voy a tener suerte" de www.google.com
 */

public class Google {

	private final static String GOOGLE_KEY = "AIzaSyAgnWeCMRhohRG9Af_cQNpbognP2-XkZbU";
	private final static String CX = "001277225422276918147:okw_xih_z8k";
	private final static String GOOGLE_SEARCH = "https://www.googleapis.com/customsearch/v1/siterestrict?key=" + GOOGLE_KEY + "&cx=" + CX + "&gl=ar&num=1";
	
	private String urlPrimerResultado;
	private String contenidoArticulo;
	
	public String busqueda(String terminoBusqueda) {
		URL url;
		
		try {
			url = new URL(GOOGLE_SEARCH + "&q=" + URLEncoder.encode(terminoBusqueda, "UTF-8"));
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser = factory.createParser(connection.getInputStream());
	        
	        while(!parser.isClosed()){
	            JsonToken jsonToken = parser.nextToken();
	            if(JsonToken.FIELD_NAME.equals(jsonToken)){
	                String fieldName = parser.getCurrentName();
	                jsonToken = parser.nextToken();
	                if("htmlSnippet".equals(fieldName)){
	                    contenidoArticulo = parser.getValueAsString();
	                }
	                if("link".equals(fieldName)) {
	                	urlPrimerResultado = parser.getValueAsString();
	                }
	            }
	        }
	        
	        connection.disconnect();
	        
	        if(urlPrimerResultado == null) {
	        	return "No encontré lo que buscabas, ¿podrías ser más específico?";
	        } else {
	        	return this.toString();
	        }	        
	        
		} catch (IOException e) {
			e.printStackTrace();
        	return "No encontré lo que buscabas, ¿podrías ser más específico?";
		}
		
	}

	@Override
	public String toString() {
		return "<a href=\"" + urlPrimerResultado + "\"><u>" + urlPrimerResultado + "</u></a><br/>" + contenidoArticulo;
	}
	
}
