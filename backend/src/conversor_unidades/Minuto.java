package conversor_unidades;

public class Minuto extends Tiempo{

	@Override
	public double toSegundo(double numero) {
		return numero * 60;
	}

	@Override
	public double toMinuto(double numero) {
		return numero;
	}
	
	@Override
	public double toHora(double numero) {
		return numero / 60;
	}
	
	@Override
	public double toDia(double numero) {
		return numero / 1440;
	}
	
}
