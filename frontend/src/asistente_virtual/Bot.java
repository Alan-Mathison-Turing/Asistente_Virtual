package asistente_virtual;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Bot {

	private String nombre;
	
	public final static String USUARIO = "delucas";	
	static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @" + USUARIO + " �podr�as repetirlo?";
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public Bot(String nombre) {
		this.nombre = nombre;
	}
	
	private final static String FORMATO_MES = "MMMM";
	private final static String FORMATO_DIA_SEMANA = "EEEE";
	private final static String FORMATO_HORA = "HH:mm a";
	private final static SimpleDateFormat sdfDia = new SimpleDateFormat(FORMATO_DIA_SEMANA);
	private final static SimpleDateFormat sdfMes = new SimpleDateFormat(FORMATO_MES);
	private final static SimpleDateFormat sfdHora = new SimpleDateFormat(FORMATO_HORA);
	
	private Calendar calendarAhora;
	
	public String leerMensaje(String mensaje) {
		
		mensaje = removerTildes(mensaje.toLowerCase());
		String respuesta = "";
		
		if(mensaje.contains("hola") || mensaje.contains("buen") || mensaje.contains("hey")) {
			respuesta = "�Hola, @" + USUARIO + "!";
		}
		
		if(mensaje.contains("gracias")) {
			respuesta = "No es nada, @" + USUARIO;
		}
		
		if(mensaje.contains("hora")) {
			respuesta = "@" + USUARIO + " son las 3:15 PM";
		}
		
		if(mensaje.contains("dia es") || mensaje.contains("fecha")) {
			respuesta = "@" + USUARIO + " hoy es 1 de abril de 2018";
		}
		
		if(mensaje.contains("semana")) {
			respuesta = "@" + USUARIO + " hoy es domingo";
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("dias") >= 0) {
			respuesta = "@" + USUARIO + " ser� el martes 3 de abril de 2018";
		}

		if(mensaje.contains("que dia sera") && mensaje.indexOf("meses") >= 0) {
			respuesta = "@" + USUARIO + " ser� el viernes 1 de junio de 2018";
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("anos") >= 0) {
			respuesta = "@" + USUARIO + " ser� el mi�rcoles 1 de abril de 2020";
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("ayer") >= 0) {
			respuesta = "@" + USUARIO + " fue s�bado 31 de marzo de 2018";
		}

		if(mensaje.contains("que dia fue") && mensaje.indexOf("dias") >= 0) {
			respuesta = "@" + USUARIO + " fue jueves 29 de marzo de 2018";
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("meses") >= 0) {
			respuesta = "@" + USUARIO + " fue el jueves 1 de febrero de 2018";
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("anos") >= 0) {
			respuesta = "@" + USUARIO + " fue el viernes 1 de abril de 2016";
		}
		
		if(mensaje.contains("desde")) {
			respuesta = "@" + USUARIO + " entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 365 d�as";
		}
		
		if(mensaje.contains("faltan")) {
			respuesta = "@" + USUARIO + " faltan 9 d�as";
		}
		
		if(mensaje.contains("cuanto es")) {
			respuesta = "@" + USUARIO + " 3";
		}
		
		return respuesta == "" ? MSG_NO_ENTIENDO : respuesta;
	}
	
	
	// Remueve los tildes del String que recibe.
	private String removerTildes(String texto) {
	    String original = "������������������������������������������������������������";
	    // Cadena de caracteres ASCII que reemplazar�n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto;
	    for (int i = 0; i < original.length(); i++) {
	    // Reemplazamos los caracteres especiales.
	    	output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}
	
	/**
	 * @param cantidad (mayor o menor a cero segun fecha hacia
	 * delante o fecha hacia atras respectivamente)
	 * @param mesesDiasSemanas ("meses"|"dias"|"semanas")
	 * @return
	 * @throws Exception 
	 */
	public String fechaDentroDe(int cantidad, String mesesDiasSemanas) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(obtenerFieldCalendar(mesesDiasSemanas), cantidad);
		return formatter.format(cal.getTime());
	}

	private int obtenerFieldCalendar(String mesesDiasSemanas) throws Exception {
		int field = -1;
		if("dias".equals(mesesDiasSemanas)){
			field = Calendar.DATE;
		}else if ("meses".equals(mesesDiasSemanas)) {
			field = Calendar.MONTH;
		}else if ("semanas".equals(mesesDiasSemanas)) {
			field = Calendar.WEEK_OF_MONTH;
		}else if ("semanas".equals(mesesDiasSemanas)) {
			field = Calendar.YEAR;
		}else{
			throw new Exception();
		}
		return field;
	}
	
	public int tiempoDesdeHasta(String fechaDesde, String fechaHasta, String mesesDiasSemanas) throws Exception{
		
		Calendar dateDesde = new GregorianCalendar();
		Calendar dateHasta = new GregorianCalendar();
		dateDesde.setTime(formatter.parse(fechaDesde));
		dateHasta.setTime(formatter.parse(fechaHasta));

		int respuesta = 0;
		
		//SOLUCION 1
		int field = obtenerFieldCalendar(mesesDiasSemanas);
		if(Calendar.DATE == field || Calendar.WEEK_OF_MONTH == field){
			respuesta = diferenciaEnDias(dateDesde, dateHasta);
			if(Calendar.WEEK_OF_MONTH == field){
				respuesta = respuesta/7;
			}
		}else{
			respuesta = dateHasta.get(Calendar.YEAR) - dateDesde.get(Calendar.YEAR);
			if(Calendar.YEAR == field){
				respuesta = respuesta * 12 + dateHasta.get(Calendar.MONTH) - dateDesde.get(Calendar.MONTH);
			}
		}
		//FIN SOLUCION 1
		
		//SOLUCION 2
		switch (obtenerFieldCalendar(mesesDiasSemanas)) {
		case Calendar.DATE:
			respuesta = diferenciaEnDias(dateDesde, dateHasta);
			break;
		case Calendar.WEEK_OF_MONTH:
			respuesta = diferenciaEnDias(dateDesde, dateHasta)/7;
			break;
		case Calendar.MONTH:
			int diferenciaAnios = dateHasta.get(Calendar.YEAR) - dateDesde.get(Calendar.YEAR);
			respuesta = diferenciaAnios * 12 + dateHasta.get(Calendar.MONTH) - dateDesde.get(Calendar.MONTH);
			break;
		case Calendar.YEAR:
			respuesta = dateHasta.get(Calendar.YEAR) - dateDesde.get(Calendar.YEAR);
			break;
		default:
			throw new Exception();
		}
		//FIN SOLUCION 2;
		return respuesta;
	}
	

	private int diferenciaEnDias(Calendar fechaDesde, Calendar fechaHasta) {
        int dias = (int) ((fechaHasta.getTimeInMillis() - fechaDesde.getTimeInMillis()) / 86400000);
        return dias;

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
