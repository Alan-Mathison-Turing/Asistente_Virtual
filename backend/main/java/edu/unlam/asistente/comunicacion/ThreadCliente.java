package edu.unlam.asistente.comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	Gson gson;
	
	public ThreadCliente(SocketUsuario socket,ArrayList<SocketUsuario> clientes) throws IOException, SQLException {
		this.cliente = socket;
		this.clientes = clientes;
		bot = new Bot("argem");
		this.userDao = new UsuarioDao();
		this.salaDao = new SalaDao();
		GsonBuilder builder = new GsonBuilder();
		this.gson = builder.create();
	}
	
	@Override
	public void run() {
		
		ObjectInputStream mensajeOIS;
		
		//PRUEBA QUE SOLO ENVIARA MENSAJES AL ASISTENTE VIRTUAL
		try {
			while(true) {
				mensajeOIS = new ObjectInputStream(this.cliente.getSocket().getInputStream());
				Mensaje mensajeRecibido = (Mensaje) mensajeOIS.readObject();
				
				ObjectOutputStream mensajeEnviar = new ObjectOutputStream(this.cliente.getSocket().getOutputStream());
				Mensaje respuesta;
				
				if (mensajeRecibido.getType().equals("LOGIN")) {
					String nombreUsuario = mensajeRecibido.getNombreUsuario();
					String contrasena = mensajeRecibido.getMensaje();
					
					LoginResponse response = new LoginResponse();
					
					if(userDao.checkLogin(nombreUsuario, contrasena)) {
						this.usuario = userDao.obtenerUsuarioPorLogin(nombreUsuario);
						this.cliente.setUsuario(this.usuario.getId());
						//Devuelve el id del usuario si es que se logueo correctamente
						response.setIdUsuario(this.usuario.getId());
						response.setSuccess(true);
					} else {
						//Caso contrario devuelve false
						response.setIdUsuario(-1);
						response.setSuccess(false);
					}
					
					respuesta = new Mensaje(this.gson.toJson(response), mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					
					mensajeEnviar.writeObject(respuesta);
				} else if (mensajeRecibido.getType().equals("CHAT_CON")) {
					//TODO: validar que el socket del usuario mensajeRecibido.getMensaje() este activo
					respuesta = new Mensaje("true", mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());
					mensajeEnviar.writeObject(respuesta);
				} else if (mensajeRecibido.getType().equals("CHAT")) {
					
					ChatRequest chatRequest = gson.fromJson(mensajeRecibido.getMensaje(), ChatRequest.class);
					
					Sala salaActual = this.salaDao.obtenerSalaPorId(chatRequest.getIdSala());
					Set<Usuario> usuariosEnSala = salaActual.getUsuarios();
					
					Mensaje respuestaBot = null;
					
					ChatResponse response = new ChatResponse();
					response.setIdSala(chatRequest.getIdSala());
					//Chequeo si el mensaje es especifico para el bot
					//En el caso de que si lo sea, la respuesta se la mando a toda la sala
					if(chatRequest.getMensaje().contains(bot.getNombre())) {
						response.setMensaje(bot.leerMensaje(mensajeRecibido.getMensaje(), mensajeRecibido.getNombreUsuario()));
						respuestaBot = new Mensaje(this.gson.toJson(response),
								bot.getNombre(), "CHAT_BOT");
					}
					
					response.setMensaje(chatRequest.getMensaje());
					respuesta = new Mensaje(this.gson.toJson(response), mensajeRecibido.getNombreUsuario(), mensajeRecibido.getType());

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
				        				ChatResponse emptyResponse = new ChatResponse();
				        				emptyResponse.setIdSala(-1);
				        				emptyResponse.setMensaje("");
				        				mensajeEnviarUsuario.writeObject(new Mensaje(this.gson.toJson(emptyResponse),"","CHAT"));
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
						ChatResponse emptyResponse = new ChatResponse();
        				emptyResponse.setIdSala(-1);
        				emptyResponse.setMensaje("");
        				mensajeEnviar.writeObject(new Mensaje(this.gson.toJson(emptyResponse),"","CHAT"));
					}
					
				} else if(mensajeRecibido.getType().equals("CONTACTOS")) {
					
					List<Usuario> contactos = this.usuario.getContactos();
					ContactosResponse response = new ContactosResponse();
					
					if(contactos.isEmpty()) {
						respuesta = new Mensaje(this.gson.toJson(response),usuario.getUsuario(), mensajeRecibido.getType());
					} else {
						for (Usuario contacto : contactos) {
							response.getContactos().add(contacto.getUsuario());
						}
						respuesta = new Mensaje(this.gson.toJson(response),usuario.getUsuario(), mensajeRecibido.getType());
					}
					mensajeEnviar.writeObject(respuesta);
				} else if(mensajeRecibido.getType().equals("GET_CHATS")) {
					List<Sala> salasUsuario = this.salaDao.obtenerSalasPorUsuario(this.usuario);
					
					ChatsResponse response = new ChatsResponse();
					
					if (salasUsuario == null || salasUsuario.isEmpty()) {
						respuesta = new Mensaje(this.gson.toJson(response), usuario.getUsuario(), mensajeRecibido.getType());
					} else {
						for (Sala salaActual : salasUsuario) {
							
							SalaResponseObj salaAgregar = new SalaResponseObj();
							salaAgregar.setId(salaActual.getId());
							salaAgregar.setNombre(salaActual.getNombre());
							salaAgregar.setDueño(salaActual.getDueño().getId());
							salaAgregar.setEsGrupal(salaActual.getEsGrupal());
							salaAgregar.setEsPrivada(salaActual.getEsPrivada());
							for(Usuario usuario : salaActual.getUsuarios()) {
								salaAgregar.getUsuarios().add(usuario.getId());
							}
							
							if (salaActual.getEsPrivada() == 1 && salaActual.getEsGrupal() == 0) {
								int count = 0;
								for (Usuario usuarioActual : salaActual.getUsuarios()) {
									if(count == 0) {
										salaAgregar.setNombreUsuario1(usuarioActual.getUsuario());
									} else {
										salaAgregar.setNombreUsuario2(usuarioActual.getUsuario());
									}
									count++;
								}
							}
							
							response.getSalas().add(salaAgregar);
						}
						respuesta = new Mensaje(this.gson.toJson(response), usuario.getUsuario(), mensajeRecibido.getType());
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
					
					
				} else if(mensajeRecibido.getType().equals("AGREGAR_CONTACTO")) {
					
					String nombreUsuario = mensajeRecibido.getMensaje();
					
					if(this.userDao.existePorNombre(nombreUsuario)) {
						Usuario usuarioContacto = this.userDao.obtenerUsuarioPorLogin(nombreUsuario);
						Sala salaNueva = new Sala();
						salaNueva.setNombre("1a1");
						salaNueva.setDueño(this.usuario);
						salaNueva.setEsPrivada(1);
						salaNueva.setEsGrupal(0);
						salaNueva.getUsuarios().add(this.usuario);
						salaNueva.getUsuarios().add(usuarioContacto);
						
						this.salaDao.crearSala(salaNueva);
						
						this.usuario.getContactos().add(usuarioContacto);
						usuarioContacto.getContactos().add(this.usuario);
						
						this.userDao.guardar(this.usuario);
						this.userDao.guardar(usuarioContacto);
						
						SalaResponseObj salaAgregar = new SalaResponseObj();
						salaAgregar.setId(salaNueva.getId());
						salaAgregar.setDueño(salaNueva.getDueño().getId());
						salaAgregar.setEsPrivada(salaNueva.getEsPrivada());
						salaAgregar.setEsGrupal(salaNueva.getEsGrupal());
						salaAgregar.setNombreUsuario1(mensajeRecibido.getNombreUsuario());
						salaAgregar.setNombreUsuario2(usuarioContacto.getUsuario());
						
						respuesta = new Mensaje(this.gson.toJson(salaAgregar), mensajeRecibido.getNombreUsuario(), "NUEVA_SALA");
						
						mensajeEnviar.writeObject(respuesta);
						
						for (SocketUsuario clienteActual : this.clientes) {
							if(clienteActual.getUsuario() == usuarioContacto.getId()) {
								ObjectOutputStream outputClienteActual = new ObjectOutputStream(clienteActual.getSocket().getOutputStream());
								outputClienteActual.writeObject(respuesta);
								break;
							}
						}
					} else {
						respuesta = new Mensaje("", mensajeRecibido.getNombreUsuario(), "CONTACTO_NO_ENCONTRADO");
						mensajeEnviar.writeObject(respuesta);
					}
					
					
					
				} else if(mensajeRecibido.getType().equals("AGREGAR_CONTACTO_SALA")) {
					
					AgregarContactoSalaRequest request = this.gson.fromJson(mensajeRecibido.getMensaje(), AgregarContactoSalaRequest.class);
					
					String nuevoContacto = request.getContacto();
					int idSala = request.getIdSala();
					
					Usuario contacto = this.userDao.obtenerUsuarioPorLogin(nuevoContacto);
					
					if(contacto != null) {
						
						Sala sala = this.salaDao.obtenerSalaPorId(idSala);
						
						boolean encontrado = false;
						for(Usuario usuarioEnSala : sala.getUsuarios()) {
							if(usuarioEnSala.getId() == contacto.getId()) {
								encontrado = true;
								break;
							}
						}
						
						if(!encontrado) {
							//sala.getUsuarios().add(contacto);
							this.salaDao.agregarUsuario(sala, contacto);
							
							SalaResponseObj salaAgregar = new SalaResponseObj();
							salaAgregar.setId(sala.getId());
							salaAgregar.setDueño(sala.getDueño().getId());
							salaAgregar.setEsPrivada(sala.getEsPrivada());
							salaAgregar.setEsGrupal(sala.getEsGrupal());
							
							respuesta = new Mensaje(this.gson.toJson(salaAgregar), mensajeRecibido.getNombreUsuario(), "NUEVA_SALA");
							
							for(SocketUsuario clienteActual : this.clientes) {
								if(clienteActual.getUsuario() == contacto.getId()) {
									ObjectOutputStream outputClienteActual = new ObjectOutputStream(clienteActual.getSocket().getOutputStream());
									outputClienteActual.writeObject(respuesta);
									break;
								}
							}
							
							respuesta = new Mensaje("", mensajeRecibido.getNombreUsuario(), "CONTACTO_AGREGADO_A_SALA");
							mensajeEnviar.writeObject(respuesta);
							
						} else {
							respuesta = new Mensaje("", mensajeRecibido.getNombreUsuario(), "CONTACTO_YA_EXISTE_EN_SALA");
							mensajeEnviar.writeObject(respuesta);
						}
						
					} else {
						respuesta = new Mensaje("", mensajeRecibido.getNombreUsuario(), "CONTACTO_NO_ENCONTRADO");
						mensajeEnviar.writeObject(respuesta);
					}
					
					
				}
				
			}
		} catch (IOException | ClassNotFoundException e) {
			//TODO: Sacar el cliente que rompio o se desconecto de los sockets activos
			System.err.println("-- TreadCliente ERROR: ocurrió un error en la gestión de mensajes");
			e.printStackTrace();
		}
		
		
	}
	
}
