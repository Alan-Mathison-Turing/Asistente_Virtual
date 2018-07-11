package edu.unlam.asistente.clima;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;

/**
 * Clase indica si esta lloviendo en este preciso momento en donde se encuentra
 * o en algún lugar particular. <br>
 */
public class DiaLluvioso implements IDecision {
	/**
	 * Siguiente decisión. <br>
	 */
	private IDecision siguienteDecision;
	/**
	 * Regex para saber si esta lloviendo en algún lugar específico. <br>
	 */
	private static final String REGEX_LLUVIA_CIUDAD = "@\\w*\\,*\\s.*(?:lloviendo|lluvia|llueve)\\s*\\w*\\s*(\\w*\\s*\\w*)\\s*\\?*";

	/**
	 * Lee un mensaje pedido por el usuario.
	 * <p>
	 * En caso de que el mensaje pida si esta lloviendo en algún lugar o en
	 * donde se encuentra, se lo aclara, sino avanza a la siguiente posible
	 * petición. <br>
	 */
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern patron = Pattern.compile(REGEX_LLUVIA_CIUDAD);
		Matcher matcher = patron.matcher(mensaje);
		if (mensaje.matches(REGEX_LLUVIA_CIUDAD)) {
			return new Clima(matcher.group(1)).obtenerDiaLluvioso();
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
