package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.database.dao.SalaDao;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Usuario;

public class ThreadCliente extends Thread{
	
	ArrayList<SocketUsuario> clientes;
	SocketUsuario cliente;
	Bot bot;
	private Usuario usuario;
	private UsuarioDao userDao;
	private SalaDao salaDao;
	
	public ThreadCliente(SocketUsuario socket,ArrayList<SocketUsuario> clientes) throws IOException, SQLException {
		this.cliente = socket;
		this.clientes = clientes;
		bot = new Bot("testBot");
		System.out.println("INFO: Socket de cliente creado");
		this.userDao = new UsuarioDao();
		this.salaDao = new SalaDao();
	}
	
	@Override
	public void run() {
		
		ObjectInputStream mensajeOIS;
		
		//PRUEBA QUE SOLO ENVIARA MENSAJES AL ASISTENTE VIRTUAL
		try {
			while(true) {
				mensajeOIS = new ObjectInputStream(this.cliente.getSocket().getInputStream());
				Mensaje mensajeRecibido = (Mensaje) mensajeOIS.readObject();
				System.out.println("INFO: Mensaje recibido");
				
				ObjectOutputStream mensajeEnviar = new ObjectOutputStream(this.cliente.getSocket().getOutputStream());
				Mensaje respuesta;
				
				if (mensajeRecibido.getType().equals("LOGIN")) {
					String nombreUsuario = mensajeRecibido.getNombreUsuario();
					String contrasena = mensajeRecibido.getMensaje();
					if(userDao.checkLogin(nombreUsuario, contrasena)) {
						this.usuario = userDao.obtenerUsuarioPorLogin(nombreUsuario);
						this.cliente.setUsuario(this.usuario.getId());
						//Devuelve el id del usuario si es que se logueo correctamente
						respuesta = new Mensaje("" + this.usuario.getId(), mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					} else {
						//Caso contrario devuelve false
						respuesta = new Mensaje("false", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					}
					
					mensajeEnviar.writeObject(respuesta);
				} else if (mensajeRecibido.getType().equals("CHAT_CON")) { //mock para abrir chat
					respuesta = new Mensaje("true", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					mensajeEnviar.writeObject(respuesta);
				} else if (mensajeRecibido.getType().equals("CHAT")) { //para mensajes de chat linea directa con bot
					respuesta = new Mensaje(bot.leerMensaje(mensajeRecibido.getMensaje(), mensajeRecibido.getNombreUsuario()),
							mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					mensajeEnviar.writeObject(respuesta);
				} else if(mensajeRecibido.getType().equals("CONTACTOS")) {
					if(this.usuario.getContactos().isEmpty()) {
						respuesta = new Mensaje("",usuario.getUsuario(), mensajeRecibido.getType());
					} else {
						String mensajeTexto = "";
						for(int i = 0; i < this.usuario.getContactos().size(); i++) {
							mensajeTexto += "" + this.usuario.getContactos().get(i).getUsuario() + ",";
						}
						mensajeTexto = mensajeTexto.substring(0, mensajeTexto.length() - 1);
						respuesta = new Mensaje(mensajeTexto,usuario.getUsuario(), mensajeRecibido.getType());
					}
					mensajeEnviar.writeObject(respuesta);
				} else if(mensajeRecibido.getType().equals("CHATS")) {
					//TODO: Obtener las salas y devolver los ids de la sala concatenado
					List<Sala> salasUsuario = this.salaDao.obtenerSalasPorUsuario(this.usuario);
					//Ejemplo:
					String mensajeSalas = "";
					for(int i = 0; i < salasUsuario.size(); i++) {
						Sala salaActual = salasUsuario.get(i);
						mensajeSalas += "" + salaActual.getId()
								+ "," + salaActual.getNombre()
								+ "," + salaActual.getEsPrivada()
								+ ";";
					}
					mensajeSalas = mensajeSalas.substring(0, mensajeSalas.length() - 1);
					respuesta = new Mensaje(mensajeSalas, usuario.getUsuario(), mensajeRecibido.getType());
					mensajeEnviar.writeObject(respuesta);
				}
				
				/*
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
					
					
					
					
				}*/
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("-- TreadCliente ERROR: ocurrió un error en la gestión de mensajes");
			e.printStackTrace();
		}
		
		
	}
}
