package edu.unlam.asistente.conversor_unidades;

public class Pulgada extends Longitud{

	@Override
	public double toCentimetro(double numero) {
		return numero * 2.54;
	}

	@Override
	public double toMetro(double numero) {
		return numero / 39.3701;
	}

	@Override
	public double toPulgada(double numero) {
		return numero;
	}

	@Override
	public double toPie(double numero) {
		return numero / 12;
	}

	@Override
	public double toKilometro(double numero) {
		return numero / 39370.1;
	}
}
