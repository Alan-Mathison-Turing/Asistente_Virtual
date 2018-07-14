package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.unlam.asistente.cliente.Main;


public class Cliente {
	
	private int puerto;
	private String ip;
	private String nombreUsuario;
	private Socket socket;
	
	public Cliente() {
	}
	
	public void createSocket(String ip, int puerto) throws IOException {
		if(this.socket == null) {
			this.ip = ip;
			this.puerto = puerto;
			this.socket = new Socket(this.ip, this.puerto);
			new ThreadEscucha(socket, this).start();
		}
	}
	
	public void cerrarSocket() {
		try {
			
			Mensaje m = new Mensaje("", nombreUsuario, "SALIR");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/cerrarSocket ERROR: ocurrio un error intentando cerrar Socket: \n "
					+ "nombre de Usuario: " + nombreUsuario );
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado para enviar mensajes de texto en el chat
	 * @param mensaje
	 */
	public void enviarMensaje(String mensaje) {
		try {
			Mensaje m = new Mensaje(mensaje, Main.usuario.getNombreUsuario(), "CHAT");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/enviarMensaje ERROR: ocurrio un error intentando enviar el siguiente mensaje: '" 
					+ mensaje.substring(0, 150) + "...' \n del usuario: " + nombreUsuario);
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado para enviar los datos del usuario al server solicitando autenticacion
	 * 
	 * @param nombreUsuario
	 * @param contraseña
	 */
	public void solicitarAutenticacion(String nombreUsuario, String contraseña) {
		try {
			Mensaje m = new Mensaje(contraseña, nombreUsuario, "LOGIN");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/solicitarAutenticacion ERROR: ocurrio un error intentando autenticar al usuario" 
									+ nombreUsuario);
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado para solicitar la creacion de un nuevo chat al servidor
	 * @param usuarioDestino
	 */
	public void abrirChatCon(String usuarioDestino) {
		try {
			Mensaje m = new Mensaje(usuarioDestino, nombreUsuario, "CHAT_CON");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/abrirChatCon ERROR: ocurrio un error intentando abrir CHAT de usuario " + nombreUsuario
										+ "con " + usuarioDestino);
			e.printStackTrace();
		}
	}
	
	public void obtenerContactosUsuario(int idUsuario) {
		try {
			Mensaje m = new Mensaje("" + idUsuario, nombreUsuario, "CONTACTOS");
 			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/ObtenerContactos ERROR: ocurrio un error intentando Obtener contactos del usuario" + idUsuario);
			e.printStackTrace();
		}
	}
	
	public void obtenerChatsUsuario(int idUsuario) {
		try {
			Mensaje m = new Mensaje("" + idUsuario, nombreUsuario, "GET_CHATS");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/ObtenerChats ERROR: ocurrio un error intentando Obtener contactos del usuario" + idUsuario);
			e.printStackTrace();
		}
	}
	
	public void mensajeRecibido() {
		try {
			Mensaje m = new Mensaje("", nombreUsuario, "MENSAJE_RECIBIDO");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/Mensaje Recibido ERROR: Ocurrio un error informando que un mensaje fue recibido correctamente");
			e.printStackTrace();
		}
	}
	
	public void crearNuevaSala(String nombre, boolean esPrivada, boolean esGrupal) {
		try {
			String mensajeTxt = ""
					+ nombre + ","
					+ String.valueOf(esPrivada) + ","
					+ String.valueOf(esGrupal);
			
			Mensaje m = new Mensaje(mensajeTxt, nombreUsuario, "CREACION_SALA");
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
		} catch (IOException e) {
			System.err.println("-- Cliente/Creacion de nueva sala ERROR");
			e.printStackTrace();
		}
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
}
