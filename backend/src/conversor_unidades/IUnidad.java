package conversor_unidades;

public interface IUnidad {
	public void setNext(IUnidad unidad);
	public IUnidad getNext();
	public double convertirUnidad(double numero, String desde, String hasta);
}
