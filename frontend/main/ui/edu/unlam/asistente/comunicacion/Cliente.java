package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.unlam.asistente.ventana.Chat;
import edu.unlam.asistente.ventana.Login;
import edu.unlam.asistente.ventana.Home;


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

			
			socket = new Socket(ip, puerto);
			new ThreadEscucha(socket, this).start();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizado para enviar mensajes de texto en el chat
	 * @param mensaje
	 */
	public void enviarMensaje(String mensaje) {
		try {
			Mensaje m = new Mensaje(mensaje, nombreUsuario, "CHAT");
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public static void main(String[] args) {
		new Cliente("localhost", 12346);
		
	}
}
