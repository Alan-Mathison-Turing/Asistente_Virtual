package edu.unlam.asistente.comunicacion;

import java.util.HashSet;
import java.util.Set;

public class SalaResponseObj {
	
	private Integer id;
	private String nombre;
	private int dueño;
	private Integer esPrivada;
	private Integer esGrupal;
	private Set<Integer> usuarios = new HashSet<Integer>(0);
	private String nombreUsuario1 = "";
	private String nombreUsuario2 = "";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDueño() {
		return dueño;
	}
	public void setDueño(int dueño) {
		this.dueño = dueño;
	}
	public Integer getEsPrivada() {
		return esPrivada;
	}
	public void setEsPrivada(Integer esPrivada) {
		this.esPrivada = esPrivada;
	}
	public Integer getEsGrupal() {
		return esGrupal;
	}
	public void setEsGrupal(Integer esGrupal) {
		this.esGrupal = esGrupal;
	}
	public Set<Integer> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<Integer> usuarios) {
		this.usuarios = usuarios;
	}
	public String getNombreUsuario1() {
		return nombreUsuario1;
	}
	public void setNombreUsuario1(String nombreUsuario1) {
		this.nombreUsuario1 = nombreUsuario1;
	}
	public String getNombreUsuario2() {
		return nombreUsuario2;
	}
	public void setNombreUsuario2(String nombreUsuario2) {
		this.nombreUsuario2 = nombreUsuario2;
	}
	
}
