package edu.unlam.asistente.database.pojo;

/**
 * Clase que administra el fact de Chuck Norris que posee el usuario. <br>
 */
public class UsuarioChuckFacts {
	/**
	 * Id del usuario. <br>
	 */
	private int idUsuario;
	/**
	 * Id del fact. <br>
	 */
	private int idFact;

	/**
	 * Constructor por default. <br>
	 */
	public UsuarioChuckFacts() {

	}

	/**
	 * Crea un fact de Chuck Norris que el usuario recibio. <br>
	 * 
	 * @param idUsuario
	 *            Id del usuario. <br>
	 * @param idFact
	 *            Id del fact. <br>
	 */
	public UsuarioChuckFacts(int idUsuario, int idFact) {
		this.idUsuario = idUsuario;
		this.idFact = idFact;
	}

	/**
	 * Devuelve el id del usuario. <br>
	 * 
	 * @return Id del usuario. <br>
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el id del usuario. <br>
	 * 
	 * @param idUsuario
	 *            Id del usuario. <br>
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Devuelve el id del fact. <br>
	 * 
	 * @return Id del fact. <br>
	 */
	public int getIdFact() {
		return idFact;
	}

	/**
	 * Establece el id del fact. <br>
	 * 
	 * @param idFact
	 *            Id del fact. <br>
	 */
	public void setIdFact(int idFact) {
		this.idFact = idFact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFact;
		result = prime * result + idUsuario;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioChuckFacts other = (UsuarioChuckFacts) obj;
		if (idFact != other.idFact)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		return true;
	}
}
