package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.unlam.asistente.cliente.Main;
import edu.unlam.asistente.entidades.Usuario;
//import edu.unlam.asistente.entidades.Chat;
import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Home;
import edu.unlam.asistente.ventana.Login;

public class ThreadEscucha extends Thread {
	private Socket socket;
	private Cliente cliente;
	private Gson gson;

	public ThreadEscucha(Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
		GsonBuilder builder = new GsonBuilder();
		this.gson = builder.create();
	}

	@Override
	public void run() {

		try {
			while (true) {
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				Mensaje msj = (Mensaje) entrada.readObject();
				if (msj != null) {
					if (msj.getType().equals("CHAT")) {
						
						ChatInput response = this.gson.fromJson(msj.getMensaje(), ChatInput.class);
						
						if(response.getMensaje().equals("")) {
							continue;
						}
						
						int idSala = response.getIdSala();
						String mensaje = response.getMensaje();
						String usuarioQueEscribio = msj.getNombreUsuario();
						
						Main.usuario.addMensajeToChat(idSala, mensaje, usuarioQueEscribio);
						
						Main.cliente.mensajeRecibido(); //Esto lo usa por si necesita tambien saber la respuesta del bot
						
						
					} else if (msj.getType().equals("CHAT_BOT")) {
						
						ChatInput response = this.gson.fromJson(msj.getMensaje(), ChatInput.class);
						
						if(response.getMensaje().equals("")) {
							continue;
						}
						
						int idSala = response.getIdSala();
						String mensaje = response.getMensaje();
						String usuarioQueEscribio = msj.getNombreUsuario();
						
						Main.usuario.addMensajeToChat(idSala, mensaje, usuarioQueEscribio);
						
						
					} 
					
					else if (msj.getType().equals("LOGIN")) {
						LoginInput serverLoginResponse = gson.fromJson(msj.getMensaje(), LoginInput.class);
						if(!serverLoginResponse.getSuccess()) {
							Main.login.loginIncorrecto();
						} else {
							Main.usuario = new Usuario(msj.getNombreUsuario(),  serverLoginResponse.getIdUsuario());
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
						
						ContactosInput response = this.gson.fromJson(msj.getMensaje(), ContactosInput.class);
						
						if(!response.getContactos().isEmpty()) {
							DefaultListModel<String> contactos = new DefaultListModel<String>();
							for(String contacto : response.getContactos()) {
								contactos.addElement(contacto);
							}
							Main.usuario.setContactos(contactos);
						}
					} else if(msj.getType().equals("GET_CHATS")) {
						
						ChatsInput response = this.gson.fromJson(msj.getMensaje(), ChatsInput.class);
						
						if(!response.getSalas().isEmpty()) {
							for(SalaResponse salaInfo : response.getSalas()) {
								
								int idSala = salaInfo.getId(); //Id de la sala
								String nombreSala = salaInfo.getNombre(); //Nombre de la sala
								int ownerId = salaInfo.getDueño(); //ownerId
								int esPrivado = salaInfo.getEsPrivada(); //Es Privado
								int esGrupal = salaInfo.getEsGrupal(); //Es Grupal
								
								if(esPrivado == 1 && esGrupal == 0) {
									Main.usuario.addChat(new Chat(
											idSala,
											nombreSala,
											ownerId,
											esPrivado,
											esGrupal
											));
								} else {
									String[] usuarios = 
											new String[] {salaInfo.getNombreUsuario1(), salaInfo.getNombreUsuario2()};
									
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
								
								Main.home.showDialogUsuarioEncontrado(true);
								
								Main.usuario.addChat(new Chat(
										idSala,
										nombreSala,
										ownerId,
										esPrivado,
										esGrupal),
										usuarios);
							}
							
						}
					} else if(msj.getType().equals("CONTACTO_NO_ENCONTRADO")) {
						Main.home.showDialogUsuarioEncontrado(false);
					}
					else if(msj.getType().equals("SALIR")) {
						
							socket.close();
							Main.home.dispose();
							Main.login = new Login();
							Main.login.setVisible(true);
					

				}
				}

			} 
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("ThreadEscucha ERROR: ocurrio un error durante la lectura de mensajes");
			e.printStackTrace();
		}
	}
}
