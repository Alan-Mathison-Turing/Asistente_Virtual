package conversor_unidades;

public class Litro extends Capacidad{

	@Override
	public double toCm3(double numero) {
		return numero * 1000;
	}

	@Override
	public double toGalon(double numero) {
		return numero / 4.54609;
	}

	@Override
	public double toLitro(double numero) {
		return numero;
	}

}
