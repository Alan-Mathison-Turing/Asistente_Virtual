package edu.unlam.asistente.leyes_robotica;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.asistente_virtual.IDecision;

public class LeyesRobotica implements IDecision {

private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String respuesta = "";
		if(mensaje.contains("leyes de la robotica")){
			return leyes(usuario);
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
	
	private String leyes(String usuario) {
		return  "@" + usuario + " Las leyes de la robotica son 3."
				+"1) Un robot no hará daño a un ser humano, ni permitirá con su inacción que sufra daño."
				+"2) Un robot debe cumplir las órdenes dadas por los seres humanos, a excepción de aquellas que entrasen en conflicto con la primera ley."
				+"3) Un robot debe proteger su propia existencia en la medida en que esta protección no entre en conflicto con la primera o con la segunda ley.";
	}
	
}
