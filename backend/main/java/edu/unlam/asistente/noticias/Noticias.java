package edu.unlam.asistente.noticias;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.unlam.asistente.json.JsonReads;

/**
 * Clase para obtener noticias importantes de distintos medios. <br>
 */
public class Noticias {
	/**
	 * API web para obtener las noticias. <br>
	 */
	private static final String API_WEB = "https://newsapi.org/v2/top-headlines?country=ar&apiKey=";
	/**
	 * Key de la API. <br>
	 */
	private static final String API_KEY = "7f368a9bb27d44f49bfce90fd1ace7ed";

	/**
	 * Obtiene las noticias locales más importantes. <br>
	 * 
	 * @return Noticias actuales. <br>
	 */
	public String obtenerNoticiasActuales() {
		try {
			StringBuilder noticias = new StringBuilder();
			JSONObject json = JsonReads.readJsonFromUrl(API_WEB + API_KEY);
			JSONObject noticia;
			for (int i = 0; i < /* json.getInt("totalResults") */ 5; i++) {
				noticia = ((JSONObject) json.getJSONArray("articles").get(i));
				noticias.append('"').append(noticia.get("title")).append('"');
				noticias.append(" (Fuente: ").append(noticia.getJSONObject("source").get("name")).append("). ");
				noticias.append('\n').append('\r');
			}
			return noticias.toString();
		} catch (IOException | JSONException e) {
			return " Ha ocurrido un error al obtener información sobre las noticias. Intente más tarde.";
		}
	}
}
