package edu.unlam.asistente.database.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 3873258900375429710L;

	private Integer id;
	private String usuario;
	private Set<Evento> eventos = new HashSet<Evento>(0);
	/**
	 * Chuck Norris facts que recibió el usuario. <br>
	 */
	private Set<ChuckNorrisFacts> chuckNorrisFacts = new HashSet<ChuckNorrisFacts>();
	
	private Set<Seed> urlSeeds = new HashSet<Seed>();


	public Usuario() {
	}

	private List<Usuario> contactos = new ArrayList<Usuario>();
	private Set<Sala> salas = new HashSet<Sala>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "usuario", nullable = false)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
	public Set<Seed> getUrlSeeds() {
		return urlSeeds;
	}

	public void setUrlSeeds(Set<Seed> urlSeeds) {
		this.urlSeeds = urlSeeds;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "usuarios")
	public Set<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	/**
	 * Devuelve los Chuck Norris facts del usuario. <br>
	 * 
	 * @return Chuck Norris facts. <br>
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuarios")
	public Set<ChuckNorrisFacts> getChuckNorrisFacts() {
		return chuckNorrisFacts;
	}

	public void setChuckNorrisFacts(Set<ChuckNorrisFacts> chuckNorrisFacts) {
		this.chuckNorrisFacts = chuckNorrisFacts;
	}

	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.ALL}, targetEntity=Usuario.class)
	@JoinTable(name="usuarioUsuario",
	joinColumns=@JoinColumn(name="id_usuario"),
	inverseJoinColumns=@JoinColumn(name="id_contacto"))
	public List<Usuario> getContactos() {
		return contactos;
	}
	
	public void setContactos(List<Usuario> contactos) {
		this.contactos = contactos;
	}
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "usuarios")
	public Set<Sala> getSalas() {
		return salas;
	}
	
	public void setSalas(Set<Sala> salas) {
		this.salas = salas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	/**
	 * Agrega un fact al usuario. <br>
	 * 
	 * @param chuckNorrisFacts
	 *            Fact de Chuck Norris. <br>
	 */
	public void agregarFact(ChuckNorrisFacts chuckNorrisFacts) {
		this.chuckNorrisFacts.add(chuckNorrisFacts);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
