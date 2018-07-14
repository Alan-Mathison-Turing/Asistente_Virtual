package edu.unlam.asistente.recordatorioEventos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.comunicacion.AlarmaRequest;
import edu.unlam.asistente.comunicacion.Mensaje;
import edu.unlam.asistente.comunicacion.SocketUsuario;
import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

public class ThreadAlarma extends Thread {

	private static ArrayList<SocketUsuario> userSockets;
	private EventoDao eventDao;
	private Gson gson;

	public ThreadAlarma() {
		userSockets = new ArrayList<SocketUsuario>();
		eventDao = new EventoDao();
		gson = new GsonBuilder().create();
	}
	
	public static synchronized void agregarUserSocket(SocketUsuario socket) {
		userSockets.add(socket);
	}
	
	@Override
	public void run() {

		while (true) {
			Iterator<SocketUsuario> it = userSockets.iterator();
			
			while (it.hasNext()) {
				SocketUsuario socketActual = it.next();
				List<Evento> eventos = eventDao.obtenerEventosPorUsuario(socketActual.getUsuario());
				for (Evento evento : eventos) {
					Date fechaEvento;
					try {
						fechaEvento = Calendario.getFechaParaSqliteDeString(evento.getFecha());

						if (!Calendario.esFechaProxima(fechaEvento)) {
							
							for (Usuario usuario : evento.getUsuarios()) {
								
							ObjectOutputStream mensajeEnviar = new ObjectOutputStream(socketActual.getSocket().getOutputStream());
							AlarmaRequest request = new AlarmaRequest();
							request.setNombreEvento(evento.getDescripcion());
							request.setFecha(evento.getFecha());
							Mensaje respuesta = new Mensaje(gson.toJson(request), usuario.getUsuario(), "ALARMA_EVENTO");
							
							mensajeEnviar.writeObject(respuesta);
							break;
							}
//							if (socket == null) {
//								throw new RuntimeException(
//										"-- ThreadAlarma ERROR obteniendo SOCKET: Ocurrió un error intentando informar "
//												+ "la alarma del " + "evento " + evento.getId() + " para el usuario" + userId);
//							}

						}
					} catch (ParseException | IOException e) {
//						System.err.println(
//								"-- ThreadAlarma ERROR: ocurrió un error al intentar parsear la fecha del siguiente evento: "
//										+ "\nID: " + evento.getId() + "\nFecha: " + evento.getFecha() + "\nidUsuario: "
//										+ userId);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
