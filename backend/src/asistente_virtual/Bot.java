package asistente_virtual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import calculadora.Calculadora;

public class Bot {

	private String nombre;
	
	public final static String USUARIO = "delucas";	
	static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @" + USUARIO + " Â¿podrÃ­as repetirlo?";
	
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
		Pattern formato_numero = Pattern.compile("[\\d\\.]+");
		String respuesta = "";
		
		if(mensaje.contains("hola") || mensaje.contains("buen") || mensaje.contains("hey")) {
			return respuesta = saludar(mensaje);
		}
		
		if(mensaje.contains("gracias")) {
			return respuesta = agradecer(mensaje);
		}
		
		if(mensaje.contains("hora")) {
			return respuesta = obtenerHora();
		}
		
		if(mensaje.contains("dia es") || mensaje.contains("fecha")) {
			return respuesta = obtenerFechaHoy();
		}
		
		if(mensaje.contains("semana")) {
			return respuesta = obtenerDiaSemanaHoy();
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("dias") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "dias"));
		}

		if(mensaje.contains("que dia sera") && mensaje.indexOf("meses") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "meses"));
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("anos") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "anios"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("ayer") >= 0) {
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-1, "dias"));
		}

		if(mensaje.contains("que dia fue") && mensaje.indexOf("dias") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "dias"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("meses") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "meses"));
		}
		
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("anos") >= 0) {
			int numero = obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "anios"));
		}
		
		if(mensaje.contains("desde")) {
			respuesta = "@" + USUARIO + " entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 365 días";
		}
		
		if(mensaje.contains("faltan")) {
			respuesta = "@" + USUARIO + " faltan 9 días";
		}
		
		if(mensaje.contains("cuanto es")) {
			int posicionInicial;
			if (mensaje.contains("el"))
				posicionInicial = mensaje.indexOf("el") + 2;
			else
				posicionInicial = mensaje.indexOf("es") + 2;
			Calculadora calculadora = new Calculadora(mensaje.substring(posicionInicial, mensaje.length()));
			int resultado = (int)calculadora.resolver();
			return respuesta = "@" + USUARIO + " " + resultado;
		}
		
		return respuesta == "" ? MSG_NO_ENTIENDO : respuesta;
	}


	private int obtenerNumero(String mensaje, Pattern formato_numero) {
		Matcher matcher = formato_numero.matcher(mensaje);
		matcher.find();
		int numero = Integer.parseInt(matcher.group());
		return numero;
	}
	
	// Remueve los tildes del String que recibe.
	private String removerTildes(String texto) {
	    String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
	    // Cadena de caracteres ASCII que reemplazarÃ¡n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto;
	    for (int i = 0; i < original.length(); i++) {
	    // Reemplazamos los caracteres especiales.
	    	output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}
	
	private String saludar(String saludo) {
		Calendar calendario = new GregorianCalendar();
		if(saludo.contains("hola") || saludo.contains("hey")) {
			 return "¡Hola, @" + Bot.USUARIO + "!";
		}
		 
		if(saludo.contains("chau")) {
			 return "Hasta luego @" + Bot.USUARIO;
		}
		 
		if(saludo.contains("buen")) {
				 if(calendario.get(Calendar.HOUR_OF_DAY)>= 13 && calendario.get(Calendar.HOUR_OF_DAY) <= 19 
						 || calendario.get(Calendar.HOUR) > 01 && calendario.get(Calendar.HOUR) <= 07) {
					 return "Buenas tardes @" + Bot.USUARIO;
				 }
				 else {
					 if(calendario.get(Calendar.HOUR_OF_DAY) < 13) {
						 return "Buenos dias @" + Bot.USUARIO;
						 }
					 else
						 return "Buenas noches @" + Bot.USUARIO;
				 }
		}
		return MSG_NO_ENTIENDO;
	}
	 
	private String agradecer(String palabra) {
		 return "No es nada, @" + Bot.USUARIO;
	 }
	
	
	/**
	 * @param cantidad (mayor o menor a cero segun fecha hacia
	 * delante o fecha hacia atras respectivamente)
	 * @param mesesDiasSemanas ("dias"|"meses")
	 * @return string
	 */
	public String fechaDentroDe(int cantidad, String mesesDiasSemanas){
		Calendar cal = Calendar.getInstance();
		try {
			cal.add(obtenerFieldCalendar(mesesDiasSemanas), cantidad);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return formatter.format(cal.getTime());
	}

	/**
	 * @param mesesDiasSemanas ("dias"|"meses"|"anios")
	 * @return int
	 * @throws Exception
	 */
	private int obtenerFieldCalendar(String mesesDiasSemanas) throws Exception {
		int field = -1;
		if("dias".equals(mesesDiasSemanas)){
			field = Calendar.DATE;
		}else if ("meses".equals(mesesDiasSemanas)) {
			field = Calendar.MONTH;
//		}else if ("semanas".equals(mesesDiasSemanas)) {
//			field = Calendar.WEEK_OF_MONTH;
		}else if ("anios".equals(mesesDiasSemanas)) {
			field = Calendar.YEAR;
		}else{
			throw new Exception();
		}
		return field;
	}
	
	/**
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param mesesDiasSemanas ("dias"|"meses"|"anios")
	 * @return int
	 */
	public int tiempoDesdeHasta(String fechaDesde, String fechaHasta, String mesesDiasSemanas){
		
		Calendar dateDesde = new GregorianCalendar();
		Calendar dateHasta = new GregorianCalendar();
		try {
			dateDesde.setTime(formatter.parse(fechaDesde));
			dateHasta.setTime(formatter.parse(fechaHasta));

			int respuesta = 0;
			int field = obtenerFieldCalendar(mesesDiasSemanas);

			if(Calendar.DATE == field || Calendar.WEEK_OF_MONTH == field){
				respuesta = diferenciaEnDias(dateDesde, dateHasta);
//				if(Calendar.WEEK_OF_MONTH == field){
//					respuesta = respuesta/7;
//				}
			}else{
				respuesta = dateHasta.get(Calendar.YEAR) - dateDesde.get(Calendar.YEAR);
				if(Calendar.MONTH == field){
					respuesta = respuesta * 12 + dateHasta.get(Calendar.MONTH) - dateDesde.get(Calendar.MONTH);
				}
			}
			return respuesta;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	

	private int diferenciaEnDias(Calendar fechaDesde, Calendar fechaHasta) {
        int dias = (int) ((fechaHasta.getTimeInMillis() - fechaDesde.getTimeInMillis()) / 86400000);
        return dias;
	}
	 
	public String obtenerFechaHoy() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " hoy es " +  calendarAhora.get(Calendar.DAY_OF_MONTH) + " de " + 
				sdfMes.format(calendarAhora.getTime()) + " de " + calendarAhora.get(Calendar.YEAR);
	}
	
	public String obtenerDiaSemanaHoy() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " hoy es " + sdfDia.format(calendarAhora.getTime());
	}
	
	public String obtenerHora() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " son las " + sfdHora.format(calendarAhora.getTime());
	}

	public String formatearFechaView(String fechaEntrada) {
		SimpleDateFormat formatoSalida = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
		String fechaSalida = "";
		try {
			fechaSalida = formatoSalida.format(formatoEntrada.parse(fechaEntrada));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaSalida;
	}

	
}
