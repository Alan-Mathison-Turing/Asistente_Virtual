package edu.unlam.asistente.noticias;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.clima.Clima;
import edu.unlam.asistente.financiera.moneda.Moneda;

/**
 * Clase que administra las noticias generales que puede ver un usuario. <br>
 */
public class NoticiasActaules implements IDecision {
	/**
	 * Siguiente decisión. <br>
	 */
	private IDecision siguienteDecision;
	/**
	 * Regex para obtener noticias. <br>
	 */
	private static final Pattern REGEX_NOTICIAS = Pattern.compile("@\\w*\\s*(?:\\w|\\s)*(?:noticias|noticia)+");

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		if (REGEX_NOTICIAS.matcher(mensaje).find()) {
			return obtenerNoticiasGenerales(usuario);
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

	/**
	 * Obtiene las noticias generales. <br>
	 * 
	 * @return
	 */
	private String obtenerNoticiasGenerales(final String usuario) {
		StringBuilder noticias = new StringBuilder();
		noticias.append(" Estas son algunas noticias: ");
		noticias.append(" Son las ").append(this.getHoraLocal()).append(".");
		noticias.append(new Clima("Buenos Aires").obtenerClimaActual());
		noticias.append(" El valor del dolar es de ").append(new Moneda().leerMoneda("dolar")).append(".");
		noticias.append(" Algunas noticias de medios locales: ").append(new Noticias().obtenerNoticiasActuales());
		return noticias.toString();
	}

	/**
	 * Devuelve la hora local. <br>
	 * 
	 * @return Hora local. <br>
	 */
	private String getHoraLocal() {
		return LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
				.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
}
