package edu.unlam.asistente.recordatorio_eventos;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.*;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

public class GestionRecordatorio implements IDecision{
	
	private IDecision siguienteDecision;
	
	public GestionRecordatorio() {};
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String mensajeMin = mensaje.toLowerCase();
		String nombre_mensaje;
		String regex="rec(a|e|i|o|u|d|m|g|t|r)*|agend(a|b|c|d|r|m|e|o|u|l)*";
		String regex_evento= "casamiento|cumpleaños|cumple|examen|parcial|final";
		Pattern patron =  Pattern.compile(regex);
		Matcher m= patron.matcher(mensajeMin);
		String auxiliar;
		//if (mensajeMin.contains("recordame") || mensajeMin.contains("haceme acordar") || mensajeMin.contains("recuerdame") || mensajeMin.contains("agenda") || mensajeMin.contains("agendame"))
		if(m.find()){
			patron =  Pattern.compile(regex_evento);
			m= patron.matcher(mensajeMin);
			nombre_mensaje=m.group();
			auxiliar="Evento" + nombre_mensaje + "fecha";
			
			return guardarRecordatorioYNotificarResultado(nombre_mensaje, usuario);
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
			if(mensaje.em)
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
