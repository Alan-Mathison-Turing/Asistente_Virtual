package edu.unlam.asistente.clima;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;

/**
 * Indica el clima en una ciudad solicitada. <br>
 */
public class ClimaCiudad implements IDecision {
	/**
	 * Siguiente decisión. <br>
	 */
	private IDecision siguienteDecision;
	/**
	 * Regex para saber si esta lloviendo en algún lugar específico. <br>
	 */
	private static final Pattern REGEX_CLIMA_CIUDAD = Pattern.compile("@\\w*(?:\\w|\\s|\\,)*(?:clima)+(?:\\w|\\s)* en ((?:\\w|\\s)+)");

	/**
	 * Lee un mensaje pedido por el usuario.
	 * <p>
	 * En caso de que el mensaje pida si esta lloviendo en algún lugar o en
	 * donde se encuentra, se lo aclara, sino avanza a la siguiente posible
	 * petición. <br>
	 */
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Matcher matcher = REGEX_CLIMA_CIUDAD.matcher(mensaje);
		if (matcher.find()) {
			return new Clima(matcher.group(1)).obtenerClimaActual();
		}
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return this.siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		this.siguienteDecision = decision;
	}
}
