package edu.unlam.asistente.database.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que administra un fact de Chuck Norris. <br>
 */
@Entity
@Table(name = "chuckNorrisFacts")
public class ChuckNorrisFacts implements Serializable {
	/**
	 * Serial version. <br>
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Id del fact. <br>
	 */
	private Integer id;
	/**
	 * Chuck Norris fact. <br>
	 */
	private String fact;

	/**
	 * Constructor por default. <br>
	 */
	public ChuckNorrisFacts() {

	}

	/**
	 * Crea un Chuck Norris fact. <br>
	 * 
	 * @param id
	 *            Id del fact. <br>
	 * @param fact
	 *            Fact sobre Chuck Norris. <br>
	 */
	public ChuckNorrisFacts(Integer id, String fact) {
		this.id = id;
		this.fact = fact;
	}

	/**
	 * Devuelve el id del Chuck Norris fact. <br>
	 * 
	 * @return Id del fact. <br>
	 */
	@Id
	@Column(name = "id", columnDefinition = "INTEGER NOT NULL")
	public Integer getId() {
		return id;
	}

	/**
	 * Establece el id del Chuck Norris fact. <br>
	 * 
	 * @param id
	 *            Id del fact. <br>
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve el fact de Chuck Norris. <br>
	 * 
	 * @return Fact. <br>
	 */
	@Column(name = "fact", columnDefinition = "TEXT NOT NULL")
	public String getFact() {
		return fact;
	}

	/**
	 * Establece el fact de Chuck Norris. <br>
	 * 
	 * @param fact
	 *            Fact. <br>
	 */
	public void setFact(String fact) {
		this.fact = fact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ChuckNorrisFacts other = (ChuckNorrisFacts) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
