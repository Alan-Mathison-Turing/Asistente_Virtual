package edu.unlam.asistente.conversor_unidades;

public class Centimetro extends Longitud {

	@Override
	public double toCentimetro(double numero) {
		return numero;
	}

	@Override
	public double toMetro(double numero) {
		return numero / 100;
	}

	@Override
	public double toPulgada(double numero) {
		return numero / 2.54;
	}

	@Override
	public double toPie(double numero) {
		return numero / 30.48;
	}

	@Override
	public double toKilometro(double numero) {
		return numero / 100000;
	}

}
