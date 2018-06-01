package edu.unlam.asistente.database.pojo;

/**
 * Clase que administra un fact de Chuck Norris. <br>
 */
public class ChuckNorrisFact {
	/**
	 * Id del fact. <br>
	 */
	private int id;
	/**
	 * Chuck Norris fact. <br>
	 */
	private String fact;

	/**
	 * Constructor por default. <br>
	 */
	public ChuckNorrisFact() {

	}

	/**
	 * Crea un Chuck Norris fact. <br>
	 * 
	 * @param id
	 *            Id del fact. <br>
	 * @param fact
	 *            Fact sobre Chuck Norris. <br>
	 */
	public ChuckNorrisFact(int id, String fact) {
		this.id = id;
		this.fact = fact;
	}

	/**
	 * Devuelve el id del Chuck Norris fact. <br>
	 * 
	 * @return Id del fact. <br>
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el id del Chuck Norris fact. <br>
	 * 
	 * @param id
	 *            Id del fact. <br>
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el fact de Chuck Norris. <br>
	 * 
	 * @return Fact. <br>
	 */
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
		result = prime * result + ((fact == null) ? 0 : fact.hashCode());
		result = prime * result + id;
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
		ChuckNorrisFact other = (ChuckNorrisFact) obj;
		if (fact == null) {
			if (other.fact != null)
				return false;
		} else if (!fact.equals(other.fact))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
