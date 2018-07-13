package edu.unlam.asistente.recordatorioEventos;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
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
	private final static String REGEX = "@(?:\\w*) (?:agendame|recordame|anotame) \\w* \\bevento\\b (\\w+) \\w* (?:(?:(\\d{1,2}) \\bde\\b (\\w*) \\bde\\b"
			+ " (\\d{4}))|((\\d{1,2})\\/(\\d{2})\\/(\\d{4}))) .* (\\d{1,2})\\:(\\d{2})";
	
	public static String getRegex() {
		return REGEX;
	}
	
	public GestionRecordatorio() {};
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		
		Pattern patron =  Pattern.compile(REGEX);
		Matcher matcher = patron.matcher(mensaje);
		
		String respuesta = null;
		
		if (mensaje.matches(REGEX)) {
			Evento evento = armarEventoConRegex(matcher);
			if (evento == null) {
				respuesta = "@" + usuario + " necesito más información para guardar este evento o algún dato es incorrecto, por favor intentalo de nuevo.";
			}
			else {
				respuesta = guardarRecordatorioYNotificarResultado(evento, usuario);
			}
		} else if ((mensaje.contains("cuando") || mensaje.contains("en que momento")) &&
				mensaje.contains("proximo evento")) {
			respuesta = mostrarProximoEvento(usuario);
		} else if ((mensaje.contains("cuanto") && mensaje.contains("proximo evento"))){
			respuesta = mostrarDiasProximoEvento(usuario);
		}
		
		if (respuesta != null) {
			return respuesta;
		}
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}
	
	

	/**
	 * Metodo que extrae grupos de un Matcher y genera un evento en caso de ser valida la entrada
	 * 
	 * @param matcher debe contener previamente cargado un pattern
	 * @return evento resultante o null
	 */
	public Evento armarEventoConRegex(Matcher matcher) {
		
		String descripcion = null;
		String dia = null;
		String mes = null;
		String año = null;
		String horas = null;
		String minutos = null;
		
		//Extraigo todos los grupos del regex
		while(matcher.find()) {
			descripcion = matcher.group(1);
			
			if (matcher.group(2) != null) {
				dia = matcher.group(2);
			} else if(matcher.group(6) != null) {
				dia = matcher.group(6);
			}
			
			if (matcher.group(3) != null) {
				mes = matcher.group(3);
			} else if (matcher.group(7) != null) {
				mes = matcher.group(7);
			}
			
			if (matcher.group(4) != null) {
				año = matcher.group(4);
			} else if (matcher.group(8) != null) {
				año = matcher.group(8);
			}
			
			horas = matcher.group(9);
			minutos = matcher.group(10);
		}
		
		//Valido que ninguno sea nulo
		if(descripcion == null || dia == null || mes == null || año == null || horas == null || minutos == null) {
			return null;
		}
		
		Evento evento = new Evento();
		evento.setDescripcion(descripcion);
		
		//armo string de fecha
		StringBuilder fechaBuilder = new StringBuilder();
		
		int añoNum = Integer.parseInt(año);
		if (añoNum < 1900 || añoNum > 9999) {
			return null;
		}
		fechaBuilder.append(año + "-");
		
		int mesNro;
		if (mes.matches("[0-9]+")) {
			mesNro = Integer.parseInt(mes);
			if (mesNro < 1 || mesNro > 12) {
				return null;
			}
			fechaBuilder.append(mes + "-");
		} else {
			try {
				mesNro = Calendario.getNumeroDeNombreMes(mes);
				if (mesNro < 1 || mesNro > 12) {
					return null;
				}
				if (mesNro < 10) {
					fechaBuilder.append("0" + mesNro + "-");
				} else {
					fechaBuilder.append(mesNro + "-");
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		int diaNum = Integer.parseInt(dia);
		if (diaNum < 32 && diaNum > 0) {
			if (diaNum < 10) {
				fechaBuilder.append("0" + diaNum + " ");
			} else {
				fechaBuilder.append(diaNum + " ");
			}
		} else
			return null;
		
		
		int horaNum = Integer.parseInt(horas);
		if (horaNum < 10) {
			fechaBuilder.append("0" + horaNum + ":");
		} else {
			fechaBuilder.append(horaNum + ":");
		}
		
		fechaBuilder.append(minutos + ":00");
		
		//valido fecha
		Date fechaEvento;
		try {
			fechaEvento = Calendario.getFechaParaSqliteDeString(fechaBuilder.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		//valido que la fecha sea mayor al instante en que se quiere agendar
		if(!Calendario.esFechaProxima(fechaEvento)) {
			return null;
		}
		
		evento.setFecha(fechaBuilder.toString());
		
		return evento;
	}
	
	
	private String mostrarProximoEvento(String usuario) {
		UsuarioDao usuarioDao;
		StringBuilder respuesta = new StringBuilder("@" + usuario);
		try {
			usuarioDao = new UsuarioDao();
			
			Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
			
			if (user == null) {
				respuesta.append(": Ocurrió un error al intentar guardar tu recordatorio");
				System.err.println("---ERROR en GestionRecordatorio/mostrarProximoEvento : El usuario " + usuario +
						" no existe---");
				return respuesta.toString();
			}
			
			if (user.getEventos() != null && user.getEventos().size() > 0) {
				
				String fechaRespuesta;
				if (user.getEventos().size() > 1) {
					
					List<Date> listaFechas = Calendario.getDateListFromEventList(user.getEventos());
					Date fechaProximoEvento = Calendario.getFechaMasCercanaDeListaFechas(listaFechas);
					fechaRespuesta = Calendario.getStringDeFecha(fechaProximoEvento);
				} else {
					
					fechaRespuesta = Calendario.getStringDeFecha(
							Calendario.getFechaParaSqliteDeString(
									user.getEventos().iterator().next().getFecha()));
				}
				
				respuesta.append(" tu próximo evento será el "+ fechaRespuesta);
			} else {
				respuesta.append(" no tiene eventos agendados");
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return respuesta.toString();
	}
	
	private String mostrarDiasProximoEvento(String usuario) {
		UsuarioDao usuarioDao;
		StringBuilder respuesta = new StringBuilder("@" + usuario);
		try {
			usuarioDao = new UsuarioDao();
			
			Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
			
			if (user == null) {
				respuesta.append(": Ocurrió un error al intentar guardar tu recordatorio");
				System.err.println("---ERROR en GestionRecordatorio/mostrarDiasProximoEvento : El usuario " + usuario +
						" no existe---");
				return respuesta.toString();
			}
			
			if (user.getEventos() != null && user.getEventos().size() > 0) {
				
				Calendar fechaProximoEvento = Calendar.getInstance();
				
				if (user.getEventos().size() > 1) {
					List<Date> listaFechas = Calendario.getDateListFromEventList(user.getEventos());
					fechaProximoEvento.setTime(Calendario.getFechaMasCercanaDeListaFechas(listaFechas));
					
				} else {
					fechaProximoEvento.setTime(Calendario.getFechaParaSqliteDeString(
									user.getEventos().iterator().next().getFecha()));
				}
				respuesta.append(" tu próximo evento será dentro de "+ Calendario.diferenciaEnDias(Calendar.getInstance(), 
						fechaProximoEvento) +" días");
			} else {
				respuesta.append(" no tiene eventos agendados");
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return respuesta.toString();
	}

	private String guardarRecordatorioYNotificarResultado(Evento evento, String usuario) {
		UsuarioDao usuarioDao;
		EventoDao eventoDao;
		StringBuilder respuesta = new StringBuilder("@" + usuario);
		usuarioDao = new UsuarioDao();
		Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
		
		if (user == null) {
			respuesta.append(": Ocurrio un error al intentar guardar tu recordatorio");
			System.err.println("---ERROR en GestionRecordatorio/guardarRecordatorioYNotificarResultado : El usuario "
					+ usuario + " no existe---");
			return respuesta.toString();
		}
		
		evento.setUsuarios( new HashSet<Usuario>(Arrays.asList(user)));
		
		eventoDao = new EventoDao();
		eventoDao.crearEvento(evento);
		respuesta.append(" tu alarma fue guardada existosamente!");
		System.out.println("--INFO: GestionRecordatorio/guardarRecordatorioYNotificarResultado : "
				+ "finalizó correctamente dando de alta el evento"+ evento +" para el usuario "+ usuario +"--");
		
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
