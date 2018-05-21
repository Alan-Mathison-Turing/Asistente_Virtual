package conversor_unidades;

public class Galon extends Capacidad{

	@Override
	public double toCm3(double numero) {
		return numero * 4546.09;
	}

	@Override
	public double toGalon(double numero) {
		return numero;
	}

	@Override
	public double toLitro(double numero) {
		return numero * 4.54609;
	}

}
