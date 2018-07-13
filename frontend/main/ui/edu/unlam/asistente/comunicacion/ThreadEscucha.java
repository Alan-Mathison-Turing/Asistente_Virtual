package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;

<<<<<<< HEAD

=======
>>>>>>> entrega_final
import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.entidades.Usuario;
import edu.unlam.asistente.entidades.Chat;
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
					if (msj.getType().equals("CHAT")) {
						
						if(msj.getMensaje().equals("")) {
							continue;
						}
						
						String[] partesMensaje = msj.getMensaje().split("\\|",-1);
						int idSala = Integer.valueOf(partesMensaje[0].substring(5));
						String mensaje = partesMensaje[1];
						String usuarioQueEscribio = msj.getNombreUsuario();
						
						Main.usuario.addMensajeToChat(idSala, mensaje, usuarioQueEscribio);
						
					} else if (msj.getType().equals("LOGIN")) { //mock login
						if(msj.getMensaje().equals("false")) {
							Main.login.loginIncorrecto();
						} else {
							Main.usuario = new Usuario(msj.getNombreUsuario(),  Integer.valueOf(msj.getMensaje()));
							Main.usuario.obtenerContactos();
							Main.usuario.obtenerChats();
							Main.login.dispose();
							Main.home = new Home();
							Main.home.setVisible(true);
						}
					} else if (msj.getType().equals("CHAT_CON")) { //mock abrir chat
						if (Boolean.parseBoolean(msj.getMensaje())) {
							//Chat nuevoChat = new Chat(cliente);
							//Main.listaChats.add(nuevoChat);
							//nuevoChat.setVisible(true);
						}
					} else if(msj.getType().equals("CONTACTOS")) {
						if(msj.getMensaje().equals("false")) {
							
						} else {
							String[] contactostxts = msj.getMensaje().split(",",-1); 
							DefaultListModel<String> contactos = new DefaultListModel<String>();
							for(int i = 0; i < contactostxts.length; i++) {
								contactos.addElement(contactostxts[i]);
							}
							Main.usuario.setContactos(contactos);
						}
					} else if(msj.getType().equals("CHATS")) {
						if(msj.getMensaje().equals("false")) {
							
						} else {
							String[] salasInfo = msj.getMensaje().split(";",-1);
							for(int i = 0; i < salasInfo.length; i++) {
								String[] salaInfo = salasInfo[i].split(",",-1);
								Main.usuario.addChat(new Chat(
										Integer.valueOf(salaInfo[0]), //Id de la sala
										salaInfo[1], //Nombre de la sala
										Integer.valueOf(salaInfo[2]), //Es Privado
										Integer.valueOf(salaInfo[3]) //Es Grupal
										));
							}
							
						}
					}
					else if(msj.getType().equals("SALIR")) {
						
							socket.close();
							System.exit(0);
					

				}
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("ThreadEscucha ERROR: ocurrio un error durante la lectura de mensajes");
			e.printStackTrace();
		}
	}
}
