package edu.unlam.asistente.database.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarioChuckFacts")
public class UsuarioChuckFacts implements Serializable {
	/**
	 * Serial versión. <br>
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioChuckPK id;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public UsuarioChuckPK getId() {
		return id;
	}

	public void setId(UsuarioChuckPK id) {
		this.id = id;
	}
}
