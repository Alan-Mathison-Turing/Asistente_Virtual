package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import edu.unlam.asistente.ventana.Chat;

public class ThreadEscucha extends Thread{
	private Socket socket;
	private String nombre;
	private Chat chat;

	public ThreadEscucha(Socket socket, String nombre, Chat chat) {
		this.socket = socket;
		this.nombre = nombre;
		this.chat = chat;
		chat.setVisible(true);
	}

	@Override
	public void run() {

		try {
			while (true) {
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				Mensaje msj = (Mensaje) entrada.readObject();
				if (msj != null) {
					System.out.println("ThreadEscucha INFO: mensaje recibido " + msj);
					chat.actualizarChat(msj.getMensaje());
				}
				
			}

		} catch (IOException | ClassNotFoundException e) {
			System.err.println("ThreadEscucha ERROR: ocurrio un error durante la lectura de mensajes");
			e.printStackTrace();
		}
	}
}
