package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.Socket;

public class SocketUsuario {

	private Socket socket;
	private int idUsuario;
	
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
