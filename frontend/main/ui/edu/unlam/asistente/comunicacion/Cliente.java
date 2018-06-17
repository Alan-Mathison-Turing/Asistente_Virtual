package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.Socket;

public class Cliente {
	
	private int puerto;
	private String ip;
	
	public Cliente(String ip, int puerto) {
		this.ip = ip;
		this.puerto = puerto;
		
		try {
			Socket socket = new Socket(ip, puerto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void enviarMensaje(String mensaje) {
		
	}
}
