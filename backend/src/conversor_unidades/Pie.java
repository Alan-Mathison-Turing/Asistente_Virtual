package conversor_unidades;

public class Pie extends Longitud{

	@Override
	public double toCentimetro(double numero) {
		return numero * 30.48;
	}

	@Override
	public double toMetro(double numero) {
		return numero * 0.3048;
	}

	@Override
	public double toPulgada(double numero) {
		return numero * 12;
	}

	@Override
	public double toPie(double numero) {
		return numero;
	}

	@Override
	public double toKilometro(double numero) {
		return numero / 3280.84;
	}
}
