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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "salas")
public class Sala implements java.io.Serializable{
	
	private static final long serialVersionUID = -681750487458884246L;
	private Integer id;
	private String nombre;
	private Usuario dueño;
	private Integer esPrivada;
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	
	public Sala() {}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@ManyToOne
    @JoinColumn(name = "dueño")
	public Usuario getDueño() {
		return dueño;
	}
	public void setDueño(Usuario dueño) {
		this.dueño = dueño;
	}
	
	@Column(name = "es_privada", nullable = false)
	public Integer getEsPrivada() {
		return esPrivada;
	}
	public void setEsPrivada(Integer esPrivada) {
		this.esPrivada = esPrivada;
	}
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarioSalas", joinColumns = { 
		@JoinColumn(name = "id_sala", nullable = false, updatable = false) }, 
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
		Sala other = (Sala) obj;
		if (dueño == null) {
			if (other.dueño != null)
				return false;
		} else if (!dueño.equals(other.dueño))
			return false;
		if (esPrivada == null) {
			if (other.esPrivada != null)
				return false;
		} else if (!esPrivada.equals(other.esPrivada))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	

}
