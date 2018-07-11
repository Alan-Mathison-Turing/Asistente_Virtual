package edu.unlam.asistente.conversor_unidades;

public class Cm3 extends Capacidad{

	@Override
	public double toCm3(double numero) {
		return numero;
	}

	@Override
	public double toGalon(double numero) {
		return numero / 4546.09;
	}

	@Override
	public double toLitro(double numero) {
		return numero / 1000;
	}

}
