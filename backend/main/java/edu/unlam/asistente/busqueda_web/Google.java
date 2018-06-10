package edu.unlam.asistente.busqueda_web;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;




/*
 * Clase utilizada para retornar la primer página encontrada para el término de búsqueda.
 * Cumple la misma función que el botón "Voy a tener suerte" de www.google.com
 */

// Google API Key: AIzaSyAXE9MOeoJ6B_57bYQajtm7NwJFS5UUSXU

public class Google {

	//private final static String GOOGLE_URL = "https://www.google.com/search?btnI&q=";
	private final static String GOOGLE_URL = "https://www.google.com/search?btnI&q=";
	
	private String urlPrimerResultado;
	private String contenidoArticulo;
	
	public String busqueda(String terminoBusqueda) {
		URL url;
	
		try {
			url = new URL(GOOGLE_URL +  URLEncoder.encode(terminoBusqueda, "UTF-8"));
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/4.76");
	        connection.setConnectTimeout(40000);
	        connection.setInstanceFollowRedirects(false);
	        connection.setDoOutput(false);
	        connection.connect();

            urlPrimerResultado = connection.getHeaderField("location");
            
            connection.disconnect();
   
            return this.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String toString() {
		return urlPrimerResultado;
	}
	
}
