package edu.unlam.asistente.clima;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.unlam.asistente.json.JsonReads;

/**
 * Clase que administra la obtención de información del clima actual para un
 * ubicación específica. <br>
 */
public class Clima {
	/**
	 * Pagina web de la API. <br>
	 */
	private static final String WEATHER_SEARCH = "http://api.openweathermap.org/data/2.5/weather?q=";
	/**
	 * API key del clima. <br>
	 */
	private static final String WEATHER_API_KEY = "&appid=2ebfe95312b475f2d88f7d67fc3e65a1";
	/**
	 * Indica la unidad de medida de la temperatura, en este caso en Celsius.
	 * <br>
	 */
	private static final String WEATHER_METRIC_CELSIUS = "&units=metric";
	/**
	 * Idioma en el que aparece la descripción del clima. <br>
	 */
	private static final String WEATHER_LANGUAGE = "&lang=es";
	/**
	 * Url para obtener la información. <br>
	 */
	private String url;

	/**
	 * Crea datos para obtener el clima actual de una ciudad. <br>
	 * 
	 * @param ciudad
	 *            Ciudad a buscar información. <br>
	 */
	public Clima(final String ciudad) {
		this.url = new StringBuilder(WEATHER_SEARCH).append(ciudad).append(WEATHER_API_KEY)
				.append(WEATHER_METRIC_CELSIUS).append(WEATHER_LANGUAGE).toString();
	}

	/**
	 * Devuelve información actual del clima en la ciudad especificada. <br>
	 * 
	 * @return Clima actual. <br>
	 */
	public String obtenerClimaActual() {
		try {
			JSONObject json = JsonReads.readJsonFromUrl(this.url);
			StringBuilder clima = new StringBuilder();
			clima.append(" En este momento en ").append(json.get("name"));
			clima.append(" hay un día ").append(this.obtenerCondicion(json));
			clima.append(" con una temperatura de ").append(json.getJSONObject("main").get("temp")).append("°");
			clima.append(" y una humedad del ").append(json.getJSONObject("main").get("humidity")).append("%")
					.append(".");
			clima.append(" La máxima registrada para hoy es de ").append(json.getJSONObject("main").get("temp_max"))
					.append("°");
			clima.append(" con una mínima de ").append(json.getJSONObject("main").get("temp_min")).append("°");
			return clima.append(".").toString();
		} catch (JSONException e) {
			return " La ciudad solicitada no es encuentra disponible o no existe. Controle el nombre por si acaso.";
		} catch (IOException e) {
			return " Ha ocurrido al obtener información sobre el clima. Intente más tarde.";
		}
	}

	/**
	 * Indica si en una ciudad va a llover actualmente. <br>
	 * 
	 * @param ciudad
	 *            Nombre de la ciudad. <br>
	 * @return Si llueve o no en una ciudad. <br>
	 */
	public String obtenerDiaLluvioso() {
		try {
			JSONObject json = JsonReads.readJsonFromUrl(this.url);
			return new StringBuilder(
					((JSONObject) json.getJSONArray("weather").get(0)).get("description").toString().contains("lluvia")
							? " Va" : " No va").append(" a llover en ").append(json.get("name")).append(".").toString();
		} catch (JSONException e) {
			return " La ciudad solicitada no es encuentra disponible o no existe. Controle el nombre por si acaso.";
		} catch (IOException e) {
			return " Ha ocurrido al obtener información sobre el clima. Intente más tarde.";
		}
	}

	private String obtenerCondicion(JSONObject json) {
		StringBuilder condicion = new StringBuilder("con ");
		try {
			JSONObject clima = ((JSONObject) json.getJSONArray("weather").get(0));
			switch (clima.get("main").toString()) {
			case "Thunderstorm":
				condicion.append("con alerta de ").append(clima.get("description"));
				break;
			case "Atomosphere":
				condicion.append("con alerta de ").append(clima.get("description"));
				break;
			case "Rain":
				condicion.append("con alerta de ").append(clima.get("description"));
				break;
			case "Drizzle":
				condicion.append("con alerta de ").append(clima.get("description"));
				break;
			// Para los climas normales no hace falta aclarar.
			default:
				condicion.append(clima.get("description"));
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return condicion.toString();
	}
}
