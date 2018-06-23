package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Login;

public class ThreadEscucha extends Thread {
	private Socket socket;
	private String nombre;
	private Chat chat;
	private Login login;

	public ThreadEscucha(Socket socket, String nombre, Chat chat, Login login) {
		this.socket = socket;
		this.nombre = nombre;
		this.chat = chat;
		this.login = login;
		login.setVisible(true);
	}

	@Override
	public void run() {

		try {
			while (true) {
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				Mensaje msj = (Mensaje) entrada.readObject();
				if (msj != null) {
					//meter tipo de mensaje... si tipo de mensaje es para CHAT
					if(msj.type.equals("Mensaje")) {
						if(msj.getNombreUsuario().equals(this.nombre)){
							System.out.println("ThreadEscucha INFO: mensaje recibido " + msj);
							chat.actualizarChat(msj.getMensaje());
	                    }
					}else if(msj.type.equals("login")){
					
					         login.dispose();
					         chat.setVisible(true);
					//si el tipo de mensaje es LOGIN
					//TODO: escribir codigo que cierra ventana de login y abre chat
				}

			}

		}} catch (IOException | ClassNotFoundException e) {
			System.err.println("ThreadEscucha ERROR: ocurrio un error durante la lectura de mensajes");
			e.printStackTrace();
		}
	}
}
