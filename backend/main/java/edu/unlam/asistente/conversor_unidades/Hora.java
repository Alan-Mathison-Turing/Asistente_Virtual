package edu.unlam.asistente.conversor_unidades;

public class Hora extends Tiempo {

	@Override
	public double toSegundo(double numero) {
		return numero * 3600;
	}
		
	@Override
	public double toMinuto(double numero) {
		return numero * 60;
	}
	
	@Override
	public double toHora(double numero) {
		return numero;
	}
	
	@Override
	public double toDia(double numero) {
		return numero / 24;
	}
	
}
