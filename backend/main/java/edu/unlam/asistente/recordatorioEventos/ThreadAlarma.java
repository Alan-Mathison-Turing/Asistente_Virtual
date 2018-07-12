package edu.unlam.asistente.recordatorioEventos;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.pojo.Evento;

public class ThreadAlarma extends Thread{
	
	public static Map<Integer, Socket> userSockets;
	private EventoDao eventDao;
	
	public ThreadAlarma() {
		userSockets = new HashMap<Integer, Socket>();
		eventDao = new EventoDao();
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			for (Integer userId : userSockets.keySet()) {
				List<Evento> eventos = eventDao.obtenerEventosPorUsuario(userId);
				
			}
		}
	}
}
