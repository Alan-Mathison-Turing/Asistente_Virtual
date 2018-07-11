package edu.unlam.asistente.clima;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
			return null;
		} catch (IOException | JSONException e) {
			return "Ha ocurrido al obtener información sobre el clima. Intente más tarde.";
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
			return ((JSONObject) json.getJSONArray("weather").get(0)).get("description").toString().contains("lluvia")
					? "va a llover." : "no va a llover.";
		} catch (IOException | JSONException e) {
			return "Ha ocurrido al obtener información sobre el clima. Intente más tarde.";
		}
	}

	public String obtenerHora() {
		try {

			JSONObject json = JsonReads
					.readJsonFromUrl("http://free.currencyconverterapi.com/api/v5/convert?q=USD_ARS&compact=ultra");
			// System.out.println((json.getString("USD_ARS"));
			System.out.println(
					new BigDecimal(Double.parseDouble(json.getString("USD_ARS"))).setScale(2, RoundingMode.HALF_EVEN));
		} catch (IOException | JSONException e) {
			return "Ha ocurrido al obtener información sobre el clima. Intente más tarde.";
		}
		return LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
				.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
}
