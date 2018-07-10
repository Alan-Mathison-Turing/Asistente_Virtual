package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.entidades.Usuario;
import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Home;

public class ThreadEscucha extends Thread {
	private Socket socket;
	private Cliente cliente;

	public ThreadEscucha(Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
	}

	@Override
	public void run() {

		try {
			while (true) {
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				Mensaje msj = (Mensaje) entrada.readObject();
				if (msj != null) {
					if (msj.getType().equals("CHAT")) { //linea directa con bot
						if (msj.getNombreUsuario().equals(cliente.getNombreUsuario())) {
							System.out.println("ThreadEscucha INFO: mensaje recibido " + msj);
							//hardcode siempre primer chat para prueba
							Main.listaChats.get(0).actualizarChat(msj.getMensaje());
							
						}
					} else if (msj.getType().equals("LOGIN")) { //mock login
						if(msj.getMensaje().equals("false")) {
							Main.login.loginIncorrecto();
						} else {
							Main.usuario = new Usuario(msj.getMensaje());
							Main.login.dispose();
							Main.home = new Home();
							Main.home.setVisible(true);
						}
					} else if (msj.getType().equals("CHAT_CON")) { //mock abrir chat
						if (Boolean.parseBoolean(msj.getMensaje())) {
							Chat nuevoChat = new Chat(cliente);
							Main.listaChats.add(nuevoChat);
							nuevoChat.setVisible(true);
						}
					} else if(msj.getType().equals("CONTACTOS")) {
						if(msj.getMensaje().equals("false")) {
							
						} else {
							
						}
					}

				}

			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("ThreadEscucha ERROR: ocurrio un error durante la lectura de mensajes");
			e.printStackTrace();
		}
	}
}
