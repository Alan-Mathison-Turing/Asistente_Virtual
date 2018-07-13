package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ThreadAceptacion extends Thread{
	
	ServerSocket server;
	ArrayList<SocketUsuario> sockets;
	
	public ThreadAceptacion(ServerSocket server, ArrayList<SocketUsuario> sockets) {
		this.server = server;
		this.sockets = sockets;
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				Socket cliente = server.accept();
				System.out.println("INFO: Cliente nuevo aceptado");
				SocketUsuario clienteSocket = new SocketUsuario(cliente);
				sockets.add(clienteSocket);
				System.out.println("INFO: Agrego cliente a lista de sockets");
				
				new ThreadCliente(clienteSocket, sockets).start();
			}
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}
}
