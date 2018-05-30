package edu.unlam.asistente.recordatorio_eventos;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

public class GestionRecordatorio implements IDecision{
	
	private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String mensajeMin = mensaje.toLowerCase();
		if (mensajeMin.contains("recordame") || mensajeMin.contains("haceme acordar") || mensajeMin.contains("recuerdame")) {
			return guardarRecordatorioYNotificarResultado(mensajeMin, usuario);
		}
		if (mensajeMin.contains("cuando sera mi proximo evento")) {
			return mostrarProximoEvento(usuario);
		}
		
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}

	private String mostrarProximoEvento(String usuario) {
		UsuarioDao usuarioDao;
		EventoDao eventoDao;
		StringBuilder respuesta = new StringBuilder("@" + usuario);
		try {
			usuarioDao = new UsuarioDao();
			
			Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
			
			if (user == null) {
				respuesta.append(": Ocurrio un error al intentar guardar tu recordatorio");
				return respuesta.toString();
			}
			
			if (user.getEventos() != null && user.getEventos().size() > 0) {
				if (user.getEventos().size() > 1) {
					List<Date> listaFechas = Calendario.getDateListFromEventList(user.getEventos());
					Date fechaProximoEvento = Calendario.getNearestDateFromList(listaFechas);
					respuesta.append("tu proximo evento será el "+ fechaProximoEvento);
				} else {
					respuesta.append("tu proximo evento será el "+ user.getEventos().iterator().next().getFecha());
				}
			} else {
				respuesta.append(" no tiene eventos agendados");
			}
			
			
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return respuesta.toString();
	}

	private String guardarRecordatorioYNotificarResultado(String mensaje, String usuario) {
		UsuarioDao usuarioDao;
		EventoDao eventoDao;
		StringBuilder respuesta = new StringBuilder("@" + usuario);
		try {
			usuarioDao = new UsuarioDao();
			Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
			
			if (user == null) {
				respuesta.append(": Ocurrio un error al intentar guardar tu recordatorio");
				return respuesta.toString();
			}
			
			
			Evento evento = new Evento();
			evento.setDescripcion(mensaje);
			evento.setFecha(mensaje);
			evento.setUsuarios( new HashSet<Usuario>(Arrays.asList(user)));
			
			eventoDao = new EventoDao();
			eventoDao.crearEvento(evento);
			respuesta.append(" tu alarma fue guardada existosamente!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return respuesta.toString();
	}

	@Override
	public IDecision getSiguienteDecision() {
		return this.siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}

}
