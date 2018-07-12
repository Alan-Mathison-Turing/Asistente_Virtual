package edu.unlam.asistente.entidades;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.unlam.asistente.cliente.Main;

public class Usuario {

	private String nombreUsuario;
	private int ID;
	private DefaultListModel<String> contactos;
	private DefaultListModel<String> salasPrivadas;
	private DefaultListModel<String> salasPublicas;
	private ArrayList<Chat> chats;
	
	public Usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
		init();
	}
	
	public Usuario(String nombreUsuario, int ID) {
		this.nombreUsuario = nombreUsuario;
		this.ID = ID;
		init();
	}
	
	private void init() {
		this.contactos = new DefaultListModel<String>();
		this.salasPrivadas = new DefaultListModel<String>();
		this.salasPublicas = new DefaultListModel<String>();
		this.chats = new ArrayList<Chat>();
	}
	
	public void obtenerContactos() {
		Main.cliente.obtenerContactosUsuario(this.ID);
	}
	
	public void obtenerChats() {
		Main.cliente.obtenerChatsUsuario(this.ID);
	}
	
	public DefaultListModel<String> getContactos(){
		return this.contactos;
	}
	
	public DefaultListModel<String> getSalasPrivadas(){
		return this.salasPrivadas;
	}
	
	public DefaultListModel<String> getSalasPublicas(){
		return this.salasPublicas;
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
	
	public void setContactos(DefaultListModel<String> contactos) {
		this.contactos.removeAllElements();
		for(int i = 0; i < contactos.size(); i++) {
			this.contactos.addElement(contactos.get(i));
		}
	}
	
	public void addChat(Chat chat) {
		this.chats.add(chat);
		if(chat.esPrivada()) {
			this.salasPrivadas.addElement(chat.getNombre());
		} else {
			this.salasPublicas.addElement(chat.getNombre());
		}
	}
	
}
