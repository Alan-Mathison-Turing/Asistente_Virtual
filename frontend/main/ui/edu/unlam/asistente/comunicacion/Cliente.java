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

			Home home = new Home(this);
			Chat chat = new Chat(this);
			Login login = new Login(this);
			socket = new Socket(ip, puerto);
//			new ThreadEscucha(socket, nombreUsuario, chat).run();
			new ThreadEscucha(socket, nombreUsuario, home).run();
			new ThreadEscucha(socket, nombreUsuario, chat, login).run();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarMensaje(String mensaje) {
		try {
			//Mensaje m = new Mensaje(mensaje, nombreUsuario);
			String m = null;
			ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
			salida.writeObject(m);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean abrirChatCon(String usuarioDestino) {
		//TODO: SOLICITAR CONEXION CON "USUARIO DESTINO" PARA CHATEAR
		return false;
	}
	
	public static void main(String[] args) {
		new Cliente("localhost", 8080);
		
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
}
