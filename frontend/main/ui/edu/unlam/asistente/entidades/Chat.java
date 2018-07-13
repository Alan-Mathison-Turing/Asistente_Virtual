package edu.unlam.asistente.entidades;

import java.util.ArrayList;

public class Chat {

	private ArrayList<MensajeChat> mensajes;
	private String nombre;
	private boolean esPrivada;
	private boolean esGrupal;
	private int salaId;
	
	public Chat(int salaId, String nombre, Integer esPrivada, Integer esGrupal) {
		this.mensajes = new ArrayList<MensajeChat>();
		this.salaId = salaId;
		this.nombre = nombre;
		this.esPrivada = esPrivada == 1;
		this.esGrupal = esGrupal == 1;
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
	
	public boolean esGrupal() {
		return this.esGrupal;
	}
	
	public int getSalaId() {
		return this.salaId;
	}
	
}
