package edu.unlam.asistente.entidades;

import javax.swing.DefaultListModel;

import edu.unlam.asistente.cliente.Main;

public class Usuario {

	private String nombreUsuario;
	private int ID;
	private DefaultListModel<String> contactos;
	private DefaultListModel<String> salasPrivadas;
	private DefaultListModel<String> salasPublicas;
	
	public Usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public Usuario(String nombreUsuario, int ID) {
		this.nombreUsuario = nombreUsuario;
		this.ID = ID;
	}
	
	public void obtenerContactos() {
		Main.cliente.obtenerContactosUsuario(this.ID);
	}
	
	public DefaultListModel<String> getContactos(){
		return this.contactos;
	}
	
	public DefaultListModel<String> getSalasPrivadas(){
		return this.salasPrivadas;
	}
	
	public DefaultListModel<String> getSalasPublicas(){
		return this.salasPrivadas;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
