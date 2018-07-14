package edu.unlam.asistente.database.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.unlam.asistente.database.pojo.Usuario;

@Entity
@Table(name = "eventos")
public class Evento implements java.io.Serializable{
	
	private static final long serialVersionUID = 8088050098108998091L;
	
	private Integer id;
	private String fecha;
	private String descripcion;
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	
	public Evento() {}
	
	
	public Evento(Integer id, String fecha, String descripcion) {
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "fecha", nullable = false)
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarioEventos", joinColumns = { 
		@JoinColumn(name = "id_evento", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_usuario", 
	   	    nullable = false, updatable = false) })
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	//TODO: REVISAR POR QUE SE ROMPE CON HASHCODE
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
//		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
//		return result;
//	}


	@Override
	public String toString() {
		return "Evento [id=" + id + ", fecha=" + fecha + ", descripcion=" + descripcion + ", usuarios=" + usuarios
				+ "]";
	}

	
	
	
}
