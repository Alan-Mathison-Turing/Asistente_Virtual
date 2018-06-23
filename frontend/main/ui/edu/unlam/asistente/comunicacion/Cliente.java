package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.unlam.asistente.ventana.Chat;

public class Cliente {
	
	private int puerto;
	private String ip;
	private String nombreUsuario;
	private Socket socket;
	
	public Cliente(String ip, int puerto) {
		this.ip = ip;
		this.puerto = puerto;
		this.nombreUsuario = "testUser";
		try {

			Chat chat = new Chat(this);
			
			socket = new Socket(ip, puerto);
			new ThreadEscucha(socket, nombreUsuario, chat).run();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarMensaje(String mensaje) {
		try {
			Mensaje m = new Mensaje(mensaje, nombreUsuario);
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Cliente("localhost", 8080);
	}
}
