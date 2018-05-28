package edu.unlam.asistente.database.pojo;

import java.util.HashSet;
import java.util.Set;
import edu.unlam.asistente.database.pojo.Evento;

public class Usuario {
	
	long id;
	String usuario;
	Set<Evento> eventos = new HashSet<Evento>(0);
	
	public Usuario() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Set getEventos() {
		return eventos;
	}

	public void setEventos(Set eventos) {
		this.eventos = eventos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventos == null) ? 0 : eventos.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (eventos == null) {
			if (other.eventos != null)
				return false;
		} else if (!eventos.equals(other.eventos))
			return false;
		if (id != other.id)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	
	
	
}
