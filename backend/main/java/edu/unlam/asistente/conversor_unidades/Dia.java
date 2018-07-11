package edu.unlam.asistente.conversor_unidades;

public class Dia extends Tiempo {

	@Override
	public double toSegundo(double numero) {
		return numero * 86400;
	}
	
	@Override
	public double toMinuto(double numero) {
		return numero * 1440;
	}
	
	@Override
	public double toHora(double numero) {
		return numero * 24;
	}
	
	@Override
	public double toDia(double numero) {
		return numero;
	}
}
