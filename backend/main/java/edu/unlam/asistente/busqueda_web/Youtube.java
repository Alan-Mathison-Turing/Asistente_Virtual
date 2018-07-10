package edu.unlam.asistente.busqueda_web;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Youtube {

	private final static String YOUTUBE_KEY = "AIzaSyAgnWeCMRhohRG9Af_cQNpbognP2-XkZbU";
	private final static String YOUTUBE_SEARCH = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video&videoDefinition=high&key=" + YOUTUBE_KEY + "&q=";
	private final static String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";

	
	private String videoId;
	
	public String busqueda(String terminoBusqueda) {
		URL url;
		
		try {
			url = new URL(YOUTUBE_SEARCH + URLEncoder.encode(terminoBusqueda, "UTF-8"));
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("charset", "UTF-8");
	        connection.setConnectTimeout(40000);
	        connection.connect();
	        
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser = factory.createParser(connection.getInputStream());
	        JsonToken jsonToken;
	        while(!parser.isClosed()){
	            jsonToken = parser.nextToken();
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
	        	return YOUTUBE_WATCH_URL + videoId;
	        }	        
	        
		} catch (IOException e) {
			e.printStackTrace();
        	return "No encontré el video que buscabas, ¿podrías ser más específico?";
		}
		
	}

	
}
