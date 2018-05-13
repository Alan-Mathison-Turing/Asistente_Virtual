package conversor_unidades;

public class ConversorUnidad implements IUnidad {

	private IUnidad nextUnidad;
	
	@Override
	public double convertirUnidad(double numero, String desde, String hasta){
		try {
			Masa masa = new Masa();
			Longitud longitud = new Longitud();
			Capacidad capacidad = new Capacidad();
			Tiempo tiempo = new Tiempo();
			this.setNextUnidad(masa);
			masa.setNextUnidad(longitud);
			longitud.setNextUnidad(capacidad);
			capacidad.setNextUnidad(tiempo);
			return redondearDecimales(nextUnidad.convertirUnidad(numero, desde, hasta), 2);		
		}
		catch(Exception e){
			return -1;
		}

	}
	
	@Override
	public void setNextUnidad(IUnidad unidad) {
		nextUnidad = unidad;
	}

	@Override
	public IUnidad getNextUnidad() {
		return nextUnidad;
	}
	
    private double redondearDecimales(double valorInicial, int cantidadDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = (int)Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, cantidadDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, cantidadDecimales))  + parteEntera;
        return resultado;
    }

}
