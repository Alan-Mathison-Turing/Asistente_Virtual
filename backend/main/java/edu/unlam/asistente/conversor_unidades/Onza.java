package edu.unlam.asistente.conversor_unidades;

public class Onza extends Masa {

	@Override
	public double toGramo(double numero) {
		return numero * 28.3495;
	}

	@Override
	public double toKilo(double numero) {
		return numero / 35.274;
	}

	@Override
	public double toTonelada(double numero) {
		return numero / 35274;
	}

	@Override
	public double toOnza(double numero) {
		return numero;
	}
}
