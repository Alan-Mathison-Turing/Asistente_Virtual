package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;

import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.entidades.Usuario;
//import edu.unlam.asistente.entidades.Chat;
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
					if (msj.getType().equals("CHAT")) {
						
						if(msj.getMensaje().equals("")) {
							continue;
						}
						
						String[] partesMensaje = msj.getMensaje().split("\\|",-1);
						int idSala = Integer.valueOf(partesMensaje[0].substring(5));
						String mensaje = partesMensaje[1];
						String usuarioQueEscribio = msj.getNombreUsuario();
						
						Main.usuario.addMensajeToChat(idSala, mensaje, usuarioQueEscribio);
						
						Main.cliente.mensajeRecibido(); //Esto lo usa por si necesita tambien saber la respuesta del bot
						
						
					} else if (msj.getType().equals("CHAT_BOT")) {
						
						if(msj.getMensaje().equals("")) {
							continue;
						}
						
						String[] partesMensaje = msj.getMensaje().split("\\|",-1);
						int idSala = Integer.valueOf(partesMensaje[0].substring(5));
						String mensaje = partesMensaje[1];
						String usuarioQueEscribio = msj.getNombreUsuario();
						
						Main.usuario.addMensajeToChat(idSala, mensaje, usuarioQueEscribio);
						
						
					} 
					
					else if (msj.getType().equals("LOGIN")) {
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
					} else if (msj.getType().equals("CHAT_CON")) {
						if (Boolean.parseBoolean(msj.getMensaje())) {
							//Chat nuevoChat = new Chat(cliente);
							//Main.listaChats.add(nuevoChat);
							//nuevoChat.setVisible(true);
						}
					} else if(msj.getType().equals("CONTACTOS")) {
						if(!msj.getMensaje().equals("false")) {
							String[] contactosTxts = msj.getMensaje().split(",",-1); 
							DefaultListModel<String> contactos = new DefaultListModel<String>();
							for(int i = 0; i < contactosTxts.length; i++) {
								contactos.addElement(contactosTxts[i]);
							}
							Main.usuario.setContactos(contactos);
						}
					} else if(msj.getType().equals("GET_CHATS")) {
						if(!msj.getMensaje().equals("false")) {
							String[] salasInfo = msj.getMensaje().split(";",-1);
							for(int i = 0; i < salasInfo.length; i++) {
								String[] salaInfo = salasInfo[i].split(",",-1);
								
								int idSala = Integer.valueOf(salaInfo[0]); //Id de la sala
								String nombreSala = salaInfo[1]; //Nombre de la sala
								int ownerId = Integer.valueOf(salaInfo[2]); //ownerId
								int esPrivado = Integer.valueOf(salaInfo[3]); //Es Privado
								int esGrupal = Integer.valueOf(salaInfo[4]); //Es Grupal
								
								
								if (salaInfo.length == 5) {
									Main.usuario.addChat(new Chat(
											idSala,
											nombreSala,
											ownerId,
											esPrivado,
											esGrupal
											));
								} else {
									
									String[] usuarios = 
											new String[] {salaInfo[5], salaInfo[6]};
									
									Main.usuario.addChat(new Chat(
											idSala,
											nombreSala,
											ownerId,
											esPrivado,
											esGrupal),
											usuarios);
								}
								
							}
							
						}
					} else if(msj.getType().equals("NUEVA_SALA")) {
						if(!msj.getMensaje().equals("false")) {
							String[] salaInfo = msj.getMensaje().split(",",-1);
							Main.usuario.addChat(new Chat(
									Integer.valueOf(salaInfo[0]), //Id de la sala
									salaInfo[1], //Nombre de la sala
									Integer.valueOf(salaInfo[2]), //ownerId
									Integer.valueOf(salaInfo[3]), //Es Privado
									Integer.valueOf(salaInfo[4]) //Es Grupal
									));
							
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
