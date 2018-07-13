package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		bot = new Bot("argem");
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
				} else if (mensajeRecibido.getType().equals("CHAT_CON")) {
					//TODO: validar que el socket del usuario mensajeRecibido.getMensaje() este activo
					respuesta = new Mensaje("true", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					mensajeEnviar.writeObject(respuesta);
				} else if (mensajeRecibido.getType().equals("CHAT")) {
					System.out.println("Mensaje nuevo recibido");
					String[] partesMensaje = mensajeRecibido.getMensaje().split("\\|",-1);
					int idSala = Integer.valueOf(partesMensaje[0].substring(5));
					String mensaje = partesMensaje[1];
					
					Sala salaActual = this.salaDao.obtenerSalaPorId(idSala);
					Set<Usuario> usuariosEnSala = salaActual.getUsuarios();
					
					
					Mensaje respuestaBot = null;
					//Chequeo si el mensaje es especifico para el bot
					//En el caso de que si lo sea, la respuesta se la mando a toda la sala
					if(mensaje.contains(bot.getNombre())) {
						respuestaBot = new Mensaje("sala:" + idSala + "|" + bot.leerMensaje(mensajeRecibido.getMensaje(), mensajeRecibido.getNombreUsuario()),
								bot.getNombre(), "CHAT_BOT");
					}
					
					respuesta = new Mensaje("sala:" + idSala + "|" + mensaje, mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());

					Integer cantidadUsuariosEnSala = usuariosEnSala.size();
					Integer usuariosContados = 0;
					
					//Recorro la lista de usuarios de la sala para poder enviarles el mensaje del usuario
					//Y la respuesta del bot, en case de que corresponda
					for (SocketUsuario clienteActual : this.clientes) {
						
						int idUsuarioBuscado = clienteActual.getUsuario();
						Iterator<Usuario> iterator = usuariosEnSala.iterator();
						while(iterator.hasNext()) {
					        Usuario siguienteUsuario = iterator.next();
					        if(siguienteUsuario.getId() == idUsuarioBuscado){
					        	usuariosContados++;
					        	ObjectOutputStream mensajeEnviarUsuario = null;
					        	Boolean primerMensajeEnviado = false;
					        	if(idUsuarioBuscado != this.usuario.getId()) {
					        		mensajeEnviarUsuario = new ObjectOutputStream(clienteActual.getSocket().getOutputStream());
					        		mensajeEnviarUsuario.writeObject(respuesta);
					        		primerMensajeEnviado = true;
					        	} else {
					        		mensajeEnviarUsuario = mensajeEnviar;
				        			if(respuestaBot == null) {
				        				mensajeEnviarUsuario.writeObject(new Mensaje("","","CHAT"));
					        		}
					        	}
					        	if(respuestaBot != null) {
					        		if(primerMensajeEnviado) {
					        			clienteActual.setProximoMensajeBot(respuestaBot);
					        		} else {
					        			mensajeEnviarUsuario.writeObject(respuestaBot);
					        		}
					        		
					        	}
					        	break;
					        }
					    }
						
						if(usuariosContados == cantidadUsuariosEnSala) {
							break;
						}
						
					}
					
					
					
					
				} else if(mensajeRecibido.getType().equals("MENSAJE_RECIBIDO")) {
					
					if(this.cliente.hasProximoMensajeBot()) {
						mensajeEnviar.writeObject(this.cliente.getProximoMensajeBot());
						this.cliente.clearProximoMensajeBot();
					} else {
						mensajeEnviar.writeObject(new Mensaje("","","CHAT"));
					}
					
				} else if(mensajeRecibido.getType().equals("CONTACTOS")) {
					if(this.usuario.getContactos().isEmpty()) {
						respuesta = new Mensaje("false",usuario.getUsuario(), mensajeRecibido.getType());
					} else {
						String mensajeTexto = "";
						for (Usuario contacto : this.usuario.getContactos()) {
							mensajeTexto += "" + contacto.getUsuario() + ",";
						}
						mensajeTexto = mensajeTexto.substring(0, mensajeTexto.length() - 1);
						respuesta = new Mensaje(mensajeTexto,usuario.getUsuario(), mensajeRecibido.getType());
					}
					mensajeEnviar.writeObject(respuesta);
				} else if(mensajeRecibido.getType().equals("GET_CHATS")) {
					List<Sala> salasUsuario = this.salaDao.obtenerSalasPorUsuario(this.usuario);
					if (salasUsuario == null || salasUsuario.isEmpty()) {
						respuesta = new Mensaje("false", usuario.getUsuario(), mensajeRecibido.getType());
					} else {
						String mensajeSalas = "";
						for (Sala salaActual : salasUsuario) {
							mensajeSalas += "" + salaActual.getId()
							+ "," + salaActual.getNombre()
							+ "," + salaActual.getDueño().getId()
							+ "," + salaActual.getEsPrivada()
							+ "," + salaActual.getEsGrupal();
							
							if (salaActual.getEsPrivada() == 1 && salaActual.getEsGrupal() == 0) {
								for (Usuario usuarioActual : salaActual.getUsuarios()) {
									mensajeSalas += "," + usuarioActual.getUsuario();
								}
							}
							mensajeSalas += ";";
						}
						mensajeSalas = mensajeSalas.substring(0, mensajeSalas.length() - 1);
						respuesta = new Mensaje(mensajeSalas, usuario.getUsuario(), mensajeRecibido.getType());
						mensajeEnviar.writeObject(respuesta);
					}
				} else if(mensajeRecibido.getType().equals("CREACION_SALA")) {
					
					String[] salaInfo = mensajeRecibido.getMensaje().split(",",-1);
					String nombre = salaInfo[0];
					boolean esPrivada = Boolean.valueOf(salaInfo[1]);
					boolean esGrupal = Boolean.valueOf(salaInfo[2]);
					
					int valEsPrivada, valEsGrupal;
					
					valEsPrivada = esPrivada ? 1 : 0;
					valEsGrupal = esGrupal ? 1 : 0;
					
					Sala salaNueva = new Sala();
					salaNueva.setNombre(nombre);
					salaNueva.setDueño(this.usuario);
					salaNueva.setEsPrivada(valEsPrivada);
					salaNueva.setEsGrupal(valEsGrupal);
					//añado dueño a la tabla de relacion
					salaNueva.getUsuarios().add(this.usuario);
					
					this.salaDao.crearSala(salaNueva);
					
					String mensajeSalaNueva = "" + salaNueva.getId()
					+ "," + salaNueva.getNombre()
					+ "," + salaNueva.getDueño().getId()
					+ "," + salaNueva.getEsPrivada()
					+ "," + salaNueva.getEsGrupal();
					
					respuesta = new Mensaje(mensajeSalaNueva, mensajeRecibido.getNombreUsuario(), "NUEVA_SALA");
					
					if(salaNueva.getEsPrivada() == 0) {
						for (SocketUsuario clienteActual : this.clientes) {
							if (clienteActual.getUsuario() != this.usuario.getId()) {
								ObjectOutputStream outputClienteActual = new ObjectOutputStream(clienteActual.getSocket().getOutputStream());
								outputClienteActual.writeObject(respuesta);
							} else {
								mensajeEnviar.writeObject(respuesta);
							}
						}
					} else {
						mensajeEnviar.writeObject(respuesta);
					}
					
					
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
			//TODO: Sacar el cliente que rompio o se desconecto de los sockets activos
			System.err.println("-- TreadCliente ERROR: ocurrió un error en la gestión de mensajes");
			e.printStackTrace();
		}
		
		
	}
	
}
