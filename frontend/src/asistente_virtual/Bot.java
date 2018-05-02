package asistente_virtual;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Bot {

	static String MSG_NO_ENTIENDO = "No entiendo lo que me estas diciendo";
	
	private final static String FORMATO_MES = "MMMM";
	private final static String FORMATO_DIA_SEMANA = "EEEE";
	private final static String FORMATO_HORA = "HH:mm a";
	private final static SimpleDateFormat sdfDia = new SimpleDateFormat(FORMATO_DIA_SEMANA);
	private final static SimpleDateFormat sdfMes = new SimpleDateFormat(FORMATO_MES);
	private final static SimpleDateFormat sfdHora = new SimpleDateFormat(FORMATO_HORA);
	
	private Calendar calendarAhora;
	
	public String leerMensaje(String mensaje) {
		
		String respuesta = "";
		
		switch (mensaje) {
		default : respuesta = MSG_NO_ENTIENDO;
			
		}
		
		return respuesta;
	}
	
	public String obtenerFechaHoy() {
		calendarAhora = Calendar.getInstance();
		return "hoy es " +  calendarAhora.get(Calendar.DAY_OF_MONTH) + " de " + 
				sdfMes.format(calendarAhora.getTime()) + " de " + calendarAhora.get(Calendar.YEAR);
	}
	
	public String obtenerDiaSemanaHoy() {
		calendarAhora = Calendar.getInstance();
		return "hoy es " + sdfDia.format(calendarAhora.getTime());
	}
	
	public String obtenerHora() {
		calendarAhora = Calendar.getInstance();
		return "son las " + sfdHora.format(calendarAhora.getTime());
	}
	
}
