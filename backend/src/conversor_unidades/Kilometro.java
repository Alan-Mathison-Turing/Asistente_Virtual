package conversor_unidades;

public class Kilometro extends Longitud{
	
	@Override
	public double toCentimetro(double numero) {
		return numero * 100000;
	}

	@Override
	public double toMetro(double numero) {
		return numero * 1000;
	}

	@Override
	public double toPulgada(double numero) {
		return numero * 39370.1;
	}

	@Override
	public double toPie(double numero) {
		return numero * 3280.84;
	}

	@Override
	public double toKilometro(double numero) {
		return numero;
	}
}
