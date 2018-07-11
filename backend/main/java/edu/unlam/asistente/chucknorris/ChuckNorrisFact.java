package edu.unlam.asistente.chucknorris;

import java.sql.SQLException;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.database.dao.ChuckNorrisFactDao;
import edu.unlam.asistente.database.dao.UsuarioDao;

/**
 * Clase que muestra un fact de Chuck Norris. <br>
 */
public class ChuckNorrisFact implements IDecision {
	/**
	 * Regex para pedir un fact de Chuck Norris. <br>
	 */
	private final static String REGEX_CHUCK_NORRIS_FACT = "@\\w*\\,*\\s*.*(?:fact)\\w*.*(?:Chuck Norris|chuck norris).*";

	/**
	 * Indica cuál es la siguiente decisión que debe intentar resolver la
	 * solicitud. <br>
	 */
	private IDecision siguienteDecision;

	/**
	 * Constructor por default. <br>
	 */
	public ChuckNorrisFact() {

	}

	/**
	 * Lee un mensaje pedido por el usuario.
	 * <p>
	 * En caso de que el mensaje pida un fact de Chuck Norris, este le devuelve
	 * un fact, sino avanza a la siguiente posible petición. <br>
	 */
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		if (mensaje.matches(REGEX_CHUCK_NORRIS_FACT)) {
			try {
				return new ChuckNorrisFactDao().obtenerFact(new UsuarioDao().obtenerUsuarioPorLogin(usuario));
			} catch (SQLException e) {
				return new String("Ha ocurrido un error con la base de datos. Reintente más tarde.");
			}
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
