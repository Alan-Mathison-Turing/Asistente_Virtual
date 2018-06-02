package edu.unlam.asistente.database.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase que administra la clave primaria de la tabla UsuarioChuckFacts. <br>
 */
@Embeddable
public class UsuarioChuckPK implements Serializable {
	/**
	 * Serial version. <br>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id del usuario. <br>
	 */
	@Column(name = "id_usuario")
	private Integer idUsuario;

	/**
	 * Id del fact. <br>
	 */
	@Column(name = "id_fact")
	private Integer idFact;

	/**
	 * Constructor por default. <br>
	 */
	public UsuarioChuckPK() {

	}

	/**
	 * Crea la clave primaria del Usuario-ChuckNorris. <br>
	 * 
	 * @param idUsuario
	 *            Id del usuario. <br>
	 * @param idFact
	 *            Id del fact. <br>
	 */
	public UsuarioChuckPK(Integer idUsuario, Integer idFact) {
		this.idUsuario = idUsuario;
		this.idFact = idFact;
	}

	/**
	 * Devuelve el id del usuario. <br>
	 * 
	 * @return Id del usuario. <br>
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el id del usuario. <br>
	 * 
	 * @param idUsuario
	 *            Id del usuario. <br>
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Devuelve el id del fact. <br>
	 * 
	 * @return Id del fact. <br>
	 */
	public Integer getIdFact() {
		return idFact;
	}

	/**
	 * Establece el id del fact. <br>
	 * 
	 * @param idFact
	 *            Id del fact. <br>
	 */
	public void setIdFact(Integer idFact) {
		this.idFact = idFact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFact == null) ? 0 : idFact.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		UsuarioChuckPK other = (UsuarioChuckPK) obj;
		if (idFact == null) {
			if (other.idFact != null)
				return false;
		} else if (!idFact.equals(other.idFact))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
}
