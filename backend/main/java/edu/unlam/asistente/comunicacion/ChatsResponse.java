package edu.unlam.asistente.comunicacion;

import java.util.ArrayList;

public class ChatsResponse {
	
	private ArrayList<SalaResponseObj> salas = new ArrayList<SalaResponseObj>();
	
	public ArrayList<SalaResponseObj> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<SalaResponseObj> salas) {
		this.salas = salas;
	}	
	
}
