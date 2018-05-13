package conversor_unidades;

public interface IUnidad {
	public void setNextUnidad(IUnidad unidad);
	public IUnidad getNextUnidad();
	public double convertirUnidad(double numero, String desde, String hasta);
}
