package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Login;
import edu.unlam.asistente.ventana.Home;

public class ThreadEscucha extends Thread {
	private Socket socket;
	private List<Chat> listaChats;
	private Login login;
	private Home home;
	private Cliente cliente;

	public ThreadEscucha(Socket socket, Cliente cliente) {
		
		this.socket = socket;
		this.cliente = cliente;
		
		home = new Home(cliente);
		listaChats = new ArrayList<>();
		login = new Login(cliente);
		
		login.setVisible(true);
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
							listaChats.get(0).actualizarChat(msj.getMensaje());
							
						}
					} else if (msj.getType().equals("LOGIN")) { //mock login
						if (Boolean.parseBoolean(msj.getMensaje())) {
							login.dispose();
							home.setVisible(true);
						}
					} else if (msj.getType().equals("CHAT_CON")) { //mock abrir chat
						if (Boolean.parseBoolean(msj.getMensaje())) {
							Chat nuevoChat = new Chat(cliente);
							listaChats.add(nuevoChat);
							nuevoChat.setVisible(true);
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
