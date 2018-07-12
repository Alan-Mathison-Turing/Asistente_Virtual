package edu.unlam.asistente.entidades;

import java.util.ArrayList;

public class Chat {

	private ArrayList<MensajeChat> mensajes;
	private String nombre;
	private boolean esPrivada;
	private int salaId;
	
	public Chat(int salaId, String nombre, Integer esPrivada) {
		this.mensajes = new ArrayList<MensajeChat>();
		this.salaId = salaId;
		this.nombre = nombre;
		this.esPrivada = esPrivada == 1;
	}
	
	public void addMensaje(MensajeChat mensaje) {
		this.mensajes.add(mensaje);
	}
	
	public boolean esPrivada() {
		return this.esPrivada;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
}
