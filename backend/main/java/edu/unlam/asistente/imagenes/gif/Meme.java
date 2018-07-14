package edu.unlam.asistente.imagenes.gif;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.cliente.Main;

public class Meme implements IDecision {
	/**
	 * Regex para encontrar memes
	 */
	public final static String REGEX_MEME = "@\\w*(?:\\w|\\s|\\,)* \\((\\w*)\\)";
	private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		Pattern pattern = Pattern.compile(REGEX_MEME);
		Matcher matcher = pattern.matcher(mensaje);
		if(mensaje.matches(REGEX_MEME)) {
			matcher.find();
			ImageIcon icon = new ImageIcon("./backend/img/" + matcher.group(1) + ".jpg");
			
//			Main.cliente.enviarMensaje(this.idSala, "./backend/img/" + matcher.group(1) + ".jpg");
			
			//htmlEditorKit.insertHTML(document, document.getLength(), " > Yo: <br/>", 0, 0, null);
			//textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
			//textAreaChat.insertIcon(icon);
			return ("./backend/img/" + matcher.group(1) + ".jpg");
		} else
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

}
