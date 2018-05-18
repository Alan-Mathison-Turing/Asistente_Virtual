package conversor_unidades;

public class Metro extends Longitud{

	@Override
	public double toCentimetro(double numero) {
		return numero * 100;
	}

	@Override
	public double toMetro(double numero) {
		return numero;
	}

	@Override
	public double toPulgada(double numero) {
		return numero * 39.3701;
	}

	@Override
	public double toPie(double numero) {
		return numero / 0.3048;
	}

	@Override
	public double toKilometro(double numero) {
		return numero / 1000;
	}
}
