package conversor_unidades;

public interface Unidad {
	public void setNext(Unidad unidad);
	public Unidad getNext();
	public double convertirUnidad(double numero, String desde, String hasta);
}
