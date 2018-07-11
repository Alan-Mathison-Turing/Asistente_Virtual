package edu.unlam.asistente.imagenes.gif;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.giphy.Giphy;
import edu.unlam.asistente.giphy.entity.search.SearchFeed;
import edu.unlam.asistente.giphy.exception.GiphyException;

/**
 * Clase que administra la obtención de gifs. <br>
 */
public class Gif implements IDecision {
	/**
	 * Regex para buscar gifs. <br>
	 */
	private final static String REGEX_GIF = "@\\w*\\,*\\s*(?:mostrame|quiero|dame|quisiera) \\w* (?:gif) (?:con|de|sobre)(.*)";
	private IDecision siguienteDecision;

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern pattern = Pattern.compile(REGEX_GIF);
		Matcher matcher = pattern.matcher(mensaje);

		if (mensaje.matches(REGEX_GIF)) {
			matcher.find();
			String webURLParseada[] = (this.obtenerGifUrl(matcher.group(1))).split("/");
			String urlGif = "https://i.giphy.com/" + webURLParseada[4] + ".gif";
			
			return urlGif;
		}
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;

	}

	public String obtenerGifUrl(final String descripcion) {
		Giphy giphy = new Giphy("DvlU7s93WEoDQLykn9zNzJg1EPYR63Ci");
		SearchFeed feed = null;
		try {
			feed = giphy.search(descripcion, 1, 0);
		} catch (GiphyException e) {
			e.printStackTrace();
		}

		return feed.getDataList().get(0).getImages().getOriginal().getUrl();
	}
}
