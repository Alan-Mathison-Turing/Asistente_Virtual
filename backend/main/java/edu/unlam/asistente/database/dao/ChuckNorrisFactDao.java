package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

public class ChuckNorrisFactDao extends BaseDao {

	/**
	 * Crea una instancia de facts de Chuck Norris. <br>
	 * 
	 * @throws SQLException
	 *             Si la conexión con la base de datos falla. <br>
	 */
	public ChuckNorrisFactDao() throws SQLException {
		super();
	}

	/**
	 * Devuelve un Chuck Norris fact a un usuario.
	 * <p>
	 * <b><i>Nota</i></b>: Este usuario no puede volver a recibir el mismo fact
	 * hasta que haya recibio todos los facts existentes en la base de datos.
	 * <br>
	 * 
	 * @param idUsuario
	 *            Id del usuario. <br>
	 * @return Chuck Norris fact. <br>
	 */
	public String obtenerFact(final int idUsuario) {
		return null;
	}

}
