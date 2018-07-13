package edu.unlam.asistente.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.ventana.Chat;

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
	
	public List<Chat> getVentanasChat(){
		return this.chats;
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
		if(chat.esGrupal()) {
			if(chat.esPrivado()) {
				this.salasPrivadas.addElement(chat.getNombre());
			} else {
				this.salasPublicas.addElement(chat.getNombre());
			}
		} 
	}
	
	public void addChat(Chat chat, String[] usuarios) {
		
		String nombreSala;
		if (usuarios[0].equals(nombreUsuario)) {
			nombreSala = usuarios[1]; 
		} else {
			nombreSala = usuarios[0];
		}
		
		chat.setNombre(nombreSala);
		this.chats.add(chat);
	}
	
	public void addMensajeToChat(int idSala, String mensaje, String usuario) {
		
		Chat chatBuscado = this.getChatByIdSala(idSala);
		
		if(chatBuscado != null) {
			chatBuscado.actualizarChat(new MensajeChat(mensaje, usuario));
			System.out.println("idSala: " + idSala + ". Recibe mensaje de: " + usuario + ". Mensaje: " + mensaje);
		}
		
	}
	
	private Chat getChatByIdSala(int idSala) {
		
		for (Chat chatActual : chats) {
			if(chatActual.getidSala() == idSala) {
				return chatActual;
			}
		}
		
		return null;
	}
	
}
