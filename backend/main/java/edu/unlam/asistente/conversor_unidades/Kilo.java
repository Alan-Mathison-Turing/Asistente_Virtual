package edu.unlam.asistente.conversor_unidades;

public class Kilo extends Masa {
	
	@Override
	public double toGramo(double numero) {
		return numero * 1000;
	}

	@Override
	public double toKilo(double numero) {
		return numero;
	}

	@Override
	public double toTonelada(double numero) {
		return numero / 1000;
	}

	@Override
	public double toOnza(double numero) {
		return numero * 35.274;
	}
}
