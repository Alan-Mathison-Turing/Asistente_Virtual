package edu.unlam.asistente.cordialidad;

import java.util.Calendar;
import edu.unlam.asistente.asistente_virtual.IDecision;
import java.util.GregorianCalendar;

import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.asistente_virtual.IDecision;

public class Cordialidad implements IDecision{

	private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String respuesta = "";
		if(mensaje.contains("hey") || mensaje.contains("hola") || mensaje.contains("buen")) {
			return respuesta = saludar(mensaje, usuario);
		}
		if(mensaje.contains("chau") || mensaje.contains("adios") || mensaje.contains("hasta luego")){
			return respuesta = despedirse(usuario);
		}
		
		if(mensaje.contains("gracias")) {
			return respuesta = agradecer(usuario);
		}
		
		return  siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}
	
	private String saludar(String saludo, String usuario) {
		Calendar calendario = new GregorianCalendar();
		 if(calendario.get(Calendar.HOUR_OF_DAY)>= 13 && calendario.get(Calendar.HOUR_OF_DAY) <= 19 
				 || calendario.get(Calendar.HOUR) > 01 && calendario.get(Calendar.HOUR) <= 07) {
			 return "Buenas tardes @" + usuario + "!";
		 }
		 else {
			 if(calendario.get(Calendar.HOUR_OF_DAY) < 13) {
				 return "Buenos días @" + usuario + "!";
				 }
			 else
				 return "Buenas noches @" + usuario + "!";
		 }
	}
	 
	private String agradecer(String usuario) {
		 return "No es nada, @" + usuario;
	 }
	
	private String despedirse(String usuario) {
		 return "Hasta luego @" + usuario + "!";
	}
	
}
