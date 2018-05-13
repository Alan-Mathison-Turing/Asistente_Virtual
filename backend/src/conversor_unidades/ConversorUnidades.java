package conversor_unidades;

public class ConversorUnidades implements Unidad {

	private Unidad next;
	
	@Override
	public double convertirUnidad(double numero, String desde, String hasta){
		try {
			Masa masa = new Masa();
			Longitud longitud = new Longitud();
			Capacidad capacidad = new Capacidad();
			Tiempo tiempo = new Tiempo();
			this.setNext(masa);
			masa.setNext(longitud);
			longitud.setNext(capacidad);
			capacidad.setNext(tiempo);
			return redondearDecimales(next.convertirUnidad(numero, desde, hasta), 2);		
		}
		catch(Exception e){
			return -1;
		}

	}
	
	@Override
	public void setNext(Unidad unidad) {
		next = unidad;
	}

	@Override
	public Unidad getNext() {
		return next;
	}
	
    private double redondearDecimales(double valorInicial, int cantidadDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, cantidadDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, cantidadDecimales)) + parteEntera;
        return resultado;
    }

}
