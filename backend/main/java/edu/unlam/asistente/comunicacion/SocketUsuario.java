package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.Socket;

public class SocketUsuario {

	private Socket socket;
	private int idUsuario;
	private Mensaje proximoMensajeBot = null;
	
	public Mensaje getProximoMensajeBot() {
		return proximoMensajeBot;
	}

	public void setProximoMensajeBot(Mensaje proximoMensajeBot) {
		this.proximoMensajeBot = proximoMensajeBot;
	}
	
	public void clearProximoMensajeBot() {
		this.proximoMensajeBot = null;
	}
	
	public boolean hasProximoMensajeBot() {
		return this.proximoMensajeBot != null;
	}

	public SocketUsuario(Socket socket) {
		this.socket = socket;
	}
	
	public void setUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public int getUsuario() {
		return this.idUsuario;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
}
