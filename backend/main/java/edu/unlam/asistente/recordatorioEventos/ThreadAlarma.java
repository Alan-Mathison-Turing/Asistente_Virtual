package edu.unlam.asistente.recordatorioEventos;

import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.pojo.Evento;

public class ThreadAlarma extends Thread {

	public static Map<Integer, Socket> userSockets;
	private EventoDao eventDao;

	public ThreadAlarma() {
		userSockets = new HashMap<Integer, Socket>();
		eventDao = new EventoDao();
	}

	@Override
	public void run() {

		while (true) {

			for (Integer userId : userSockets.keySet()) {
				List<Evento> eventos = eventDao.obtenerEventosPorUsuario(userId);
				for (Evento evento : eventos) {
					Date fechaEvento;
					try {
						fechaEvento = Calendario.getFechaParaSqliteDeString(evento.getFecha());

						if (!Calendario.esFechaProxima(fechaEvento)) {
							Socket socket = userSockets.get(userId);

							if (socket == null) {
								throw new RuntimeException(
										"-- ThreadAlarma ERROR obteniendo SOCKET: Ocurrió un error intentando informar "
												+ "la alarma del " + "evento " + evento.getId() + " para el usuario" + userId);
							}

						}
					} catch (ParseException e) {
						System.err.println(
								"-- ThreadAlarma ERROR: ocurrió un error al intentar parsear la fecha del siguiente evento: "
										+ "\nID: " + evento.getId() + "\nFecha: " + evento.getFecha() + "\nidUsuario: "
										+ userId);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
