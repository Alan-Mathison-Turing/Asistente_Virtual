package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
	int puerto;
	ArrayList<SocketUsuario> sockets = new ArrayList<SocketUsuario>();
	
	public Servidor(int puerto) {
		this.puerto = puerto;
		
		try {
			ServerSocket server = new ServerSocket(puerto);
			System.out.println("INFO: Server creado en puerto " + puerto);
			
			new ThreadAceptacion(server, sockets).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	public static void main(String[] args) {
		new Servidor(12346);
	}
}
