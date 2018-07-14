package edu.unlam.asistente.financiera.moneda;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;

/**
 * Clase para obtener el valor actual de alguna moneda solicitada. <br>
 */
public class ValorMoneda implements IDecision {
	/**
	 * Regex del valor de alguna moneda. <br>
	 */
	private static final Pattern REGEX_VALOR_MONEDA = Pattern
			.compile("@\\w*(?:\\w|\\s|\\,)*(?:valor|cotizacion)+(?:\\w|\\s)* (?:del|de la) (\\w+)");
	/**
	 * Indica cuál es la siguiente decisión que debe intentar resolver la
	 * solicitud. <br>
	 */
	private IDecision siguienteDecision;

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Matcher matcher = REGEX_VALOR_MONEDA.matcher(mensaje);
		if (matcher.find()) {
			String moneda = matcher.group();
			return new StringBuilder(" Su valor es de ").append(new Moneda().leerMoneda(moneda)).toString();
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
}
