package edu.unlam.asistente.financiera.acciones;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.json.JsonReads;

/**
 * Clase para obtener el valor actual de las acciones de una compañía
 * solicitada. <br>
 */
public class Acciones implements IDecision {
	/**
	 * Regex del valor de alguna acción. <br>
	 */
	private static final Pattern REGEX_VALOR_ACCIONES = Pattern
			.compile("@\\w*(?:\\w|\\s|\\,)*(?:acciones)+(?:\\w|\\s)* (?:de)* (\\w+)");
	/**
	 * Key de la API. <br>
	 */
	private static final String API_KEY = "0KGU7XE0FXDFRZHW";
	/**
	 * Indica cuál es la siguiente decisión que debe intentar resolver la
	 * solicitud. <br>
	 */
	private IDecision siguienteDecision;

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Matcher matcher = REGEX_VALOR_ACCIONES.matcher(mensaje);
		if (matcher.find()) {
			return this.obtenerAccion(matcher.group(1));
		}
		return this.siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return this.siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		this.siguienteDecision = decision;
	}

	/**
	 * Devuelve las acciones de una empresa en tiempo real. <br>
	 * 
	 * @param empresa
	 *            Nombre de la empresa (BCBA). <br>
	 * @return Acciones de la empresa en tiempo real. <br>
	 */
	private String obtenerAccion(final String empresa) {
		try {
			JSONObject json = (JSONObject) JsonReads.readJsonFromUrl(
					new StringBuilder("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=")
							.append(empresa).append("&apikey=").append(API_KEY).toString())
					.getJSONObject("Time Series (Daily)")
					.get(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
							.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			StringBuilder acciones = new StringBuilder(" Las acciones a la fecha de ").append(empresa.toUpperCase())
					.append(": ");
			acciones.append("Acciones en la apertura: U$S ").append(json.get("1. open"));
			acciones.append(". Alta: U$S ").append(json.get("2. high"));
			acciones.append(". Baja: U$S ").append(json.get("3. low"));
			acciones.append(". Acciones al cierre: U$S ").append(json.get("4. close"));
			acciones.append(". Cierre redondeado: U$S ").append(json.get("5. adjusted close"));
			return acciones.append(".").toString();
		} catch (JSONException e) {
			return " El nombre de la empresa (BCBA) no esta bien escrito, por favor escribalo correctamente.";
		} catch (IOException e) {
			return " Ha ocurrido un problema al obtener los datos financieros. Intente más tarde.";
		}
	}
}
