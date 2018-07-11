package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import edu.unlam.asistente.asistente_virtual.Bot;

public class ThreadCliente extends Thread{
	
	ArrayList<Socket> clientes;
	Socket cliente;
	Bot bot;
	
	public ThreadCliente(Socket socket,ArrayList<Socket> clientes) throws IOException {
		this.cliente = socket;
		this.clientes = clientes;
		bot = new Bot("testBot");
		System.out.println("INFO: Socket de cliente creado");
	}
	
	@Override
	public void run() {
		
		ObjectInputStream mensajeOIS;
		
		//PRUEBA QUE SOLO ENVIARA MENSAJES AL ASISTENTE VIRTUAL
		try {
			while(true) {
				mensajeOIS = new ObjectInputStream(this.cliente.getInputStream());
				Mensaje mensajeRecibido = (Mensaje) mensajeOIS.readObject();
				System.out.println("INFO: Mensaje recibido");
				Iterator<Socket> iteratorClientes = this.clientes.iterator();
				
				while(iteratorClientes.hasNext()){
					Socket clienteAEnviar = iteratorClientes.next();
					if(clienteAEnviar.isClosed()) {
						iteratorClientes.remove();
						continue;
					}
					//Para chat de grupo?
//					if(this.cliente.equals(clienteAEnviar)) {
//						continue;
//					}
					
					ObjectOutputStream mensajeEnviar = new ObjectOutputStream(clienteAEnviar.getOutputStream());
					Mensaje respuesta;
					
					if (mensajeRecibido.getType().equals("LOGIN")) { //mock para login
						respuesta = new Mensaje("true", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
						mensajeEnviar.writeObject(respuesta);
					} else if (mensajeRecibido.getType().equals("CHAT_CON")) { //mock para abrir chat
						respuesta = new Mensaje("true", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
						mensajeEnviar.writeObject(respuesta);
					} else if (mensajeRecibido.getType().equals("CHAT")) { //para mensajes de chat linea directa con bot
						respuesta = new Mensaje(bot.leerMensaje(mensajeRecibido.getMensaje(), mensajeRecibido.getNombreUsuario()),
								mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
						mensajeEnviar.writeObject(respuesta);
					}
					
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("-- TreadCliente ERROR: ocurrió un error en la gestión de mensajes");
			e.printStackTrace();
		}
		
		
	}
}
