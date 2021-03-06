package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadAceptacion extends Thread{
	
	ServerSocket server;
	ArrayList<Socket> sockets;
	
	public ThreadAceptacion(ServerSocket server, ArrayList<Socket> sockets) {
		this.server = server;
		this.sockets = sockets;
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				Socket cliente = server.accept();
				System.out.println("INFO: Cliente nuevo aceptado");
				sockets.add(cliente);
				System.out.println("INFO: Agrego cliente a lista de sockets");
				
				new ThreadCliente(cliente, sockets).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
