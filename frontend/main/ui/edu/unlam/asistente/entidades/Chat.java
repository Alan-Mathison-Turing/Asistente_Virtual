package edu.unlam.asistente.entidades;

import java.util.ArrayList;

public class Chat {

	private ArrayList<MensajeChat> mensajes;
	private int salaId;
	
	public Chat(int salaId) {
		this.mensajes = new ArrayList<MensajeChat>();
		this.salaId = salaId;
	}
	
	public void addMensaje(MensajeChat mensaje) {
		this.mensajes.add(mensaje);
	}
	
}
