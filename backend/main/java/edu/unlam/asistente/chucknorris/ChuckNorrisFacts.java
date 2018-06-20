package edu.unlam.asistente.chucknorris;

import java.util.Random;

import edu.unlam.asistente.asistente_virtual.IDecision;

public class ChuckNorrisFacts implements IDecision {
	
	//Mock chucknorris facts for only one user
	private static String[] facts = {
		"Chuck Norris no duerme. Espera.",
		"Chuck Norris no lee el periódico, lo estudia.",
		"Chuck Norris no juega a nada, lo gana.",
		"Chuck Norris cuando defeca, deja el aire con olor a Axe.",
		"Chuck Norris invento a su padre.",
		"No existe la teoría de la evolución, tan sólo una lista de las especies que Chuck Norris permite vivir.",
		"Chuck Norris no te pisa un pie, sino el cuello.",
		"Chuck Norris borró la papelera de reciclaje.",
		"Chuck Norris no sale de caza por que implica posibilidad de fallar. Chuck Norris sale a matar.",
		"Chuck Norris va a 1.000.000 km/s cuando esta quieto.",
	};
	
	private static int[] posFactsUtilizada = {
		0,0,0,0,0,0,0,0,0,0
	};
	
	private static int sumaSeleccionados = 0;
	
	private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String respuesta = "";
		if(mensaje.contains("chucknorrisfact")){
			return nextChuckNorrisFact(usuario);
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
	
	private String nextChuckNorrisFact(String usuario) {
		if(sumaSeleccionados == 10) {
			sumaSeleccionados = 0;
			for(int i = 0; i < posFactsUtilizada.length; i++) {
				posFactsUtilizada[i] = 0;
			}
		}
		int next;
		do{
			Random random = new Random();
			next = random.nextInt(9 - 0 + 1);
		} while(posFactsUtilizada[next] != 0);
		sumaSeleccionados++;
		posFactsUtilizada[next] = 1;
		return facts[next];
	}
	
}
