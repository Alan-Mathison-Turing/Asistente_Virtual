package asistente_virtual;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import calculadora.Calculadora;
import conversor_unidades.ConversorUnidad;


public class Bot {

	private String nombre;
	
	public final static String USUARIO = "delucas";	
	static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @" + USUARIO + " ¿podrás repetirlo?";
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public Bot(String nombre) {
		this.nombre = nombre;
	}
	
	private final static String FORMATO_MES = "MMMM";
	private final static String FORMATO_DIA_SEMANA = "EEEE";
	private final static String FORMATO_HORA = "HH:mm a";
	private final static SimpleDateFormat sdfDia = new SimpleDateFormat(FORMATO_DIA_SEMANA, new Locale("es", "ES"));
	private final static SimpleDateFormat sdfMes = new SimpleDateFormat(FORMATO_MES, new Locale("es", "ES"));
	private final static SimpleDateFormat sfdHora = new SimpleDateFormat(FORMATO_HORA, new Locale("es", "ES"));
	
	private Calendar calendarAhora;
	
	public String leerMensaje(String mensaje) {
		
		mensaje = removerTildes(mensaje.toLowerCase());
		Pattern formato_numero = Pattern.compile("(\\d*\\.*\\d)");
		String respuesta = "";
		
		// Ingreso a ConversorUnidad si cumple con el regex.
		if(mensaje.matches("@(\\w*) (?:cuantas|cuantos) (\\w*\\.*) (?:son|hay en) (\\d*\\.*\\d) (\\w+)(?:\\s* \\?|\\.*\\?)?")) {
			mensaje = mensaje.replace("?", "");
			String[] palabras = mensaje.split(" ");
			String hasta = palabras[2];
			String desde = palabras[palabras.length - 1];
	
			DecimalFormat df = new DecimalFormat("#0.00");
			
			double numero = obtenerNumero(mensaje, formato_numero);
			
			ConversorUnidad cu = new ConversorUnidad();

			double resultado = cu.convertirUnidad(numero, diccionario(desde), diccionario(hasta));
			
			if(resultado == -1) return MSG_NO_ENTIENDO;
			if(resultado == -2) return "@" + USUARIO + " las magnitudes no pueden ser negativas.";
			return respuesta = "@" + USUARIO + " " + df.format(numero) + " " + desde + 
						" equivale a " + df.format(resultado) + " " + hasta;				
		}
		
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
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "dias"));
		}

		if(mensaje.contains("que dia sera") && mensaje.indexOf("meses") >= 0) {
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "meses"));
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("anos") >= 0) {
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " será el " + formatearFechaView(fechaDentroDe(numero, "anios"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("ayer") >= 0) {
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-1, "dias"));
		}

		if(mensaje.contains("que dia fue") && mensaje.indexOf("dias") >= 0) {
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "dias"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("meses") >= 0) {
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "meses"));
		}
		
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("anos") >= 0) {
			int numero = (int) obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + USUARIO + " fue " + formatearFechaView(fechaDentroDe(-numero, "anios"));
		}
		
		if(mensaje.contains("desde")) {
			int posicion = mensaje.indexOf("el") + 3;
			String fecha = mensaje.substring(posicion, mensaje.length()-1);
			try {
				Calendar fechaParseada = parseFechaInput(fecha);
				Calendar now = Calendar.getInstance();
				now.set(Calendar.MILLISECOND, 0);
				now.set(Calendar.SECOND, 0);
				now.set(Calendar.MINUTE, 0);
				now.set(Calendar.HOUR, 0);
				int dias = diferenciaEnDias(fechaParseada, now);
				respuesta = "@" + USUARIO + " entre el " + fecha + " y el " + parseCalendarToString(now) + " pasaron " + dias + " días";
			} catch (ParseException e) {
				e.printStackTrace();
				return respuesta == "" ? MSG_NO_ENTIENDO : respuesta;
			}
			
		}
		
		if(mensaje.contains("faltan")) {
			int posicion = mensaje.indexOf("el") + 3;
			try {
				Calendar fechaParseada = parseFechaInput(mensaje.substring(posicion, mensaje.length()-1));
				Calendar now = Calendar.getInstance();
				now.set(Calendar.MILLISECOND, 0);
				now.set(Calendar.SECOND, 0);
				now.set(Calendar.MINUTE, 0);
				now.set(Calendar.HOUR, 0);
				int dias = diferenciaEnDias(now, fechaParseada);
				respuesta = "@" + USUARIO + " faltan " + dias + " días";
			} catch (ParseException e) {
				e.printStackTrace();
				return respuesta == "" ? MSG_NO_ENTIENDO : respuesta;
			}
			
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
	


	private double obtenerNumero(String mensaje, Pattern formato_numero) {
		Matcher matcher = formato_numero.matcher(mensaje);
		matcher.find();
		return Double.parseDouble(matcher.group());
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
		return "¡Hola, @" + Bot.USUARIO + "!"; 
		/*Calendar calendario = new GregorianCalendar();
		saludo = saludo.toLowerCase();
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
		return MSG_NO_ENTIENDO;*/
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
	private String fechaDentroDe(int cantidad, String mesesDiasSemanas){
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
	private int tiempoDesdeHasta(String fechaDesde, String fechaHasta, String mesesDiasSemanas){
		
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
	
	/**
	 * Metodo que devuelve la fecha de hoy, en castellano
	 * @return
	 */
	public String obtenerFechaHoy() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " hoy es " +  calendarAhora.get(Calendar.DAY_OF_MONTH) + " de " + 
				sdfMes.format(calendarAhora.getTime()) + " de " + calendarAhora.get(Calendar.YEAR);
	}
	
	/**
	 * Metodo que devuelve el dia de la semana de hoy, en castellano
	 * @return
	 */
	public String obtenerDiaSemanaHoy() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " hoy es " + sdfDia.format(calendarAhora.getTime());
	}
	
	/**
	 * Metodo que devuelve la hora actual, en formato AM/PM
	 * @return
	 */
	private String obtenerHora() {
		calendarAhora = Calendar.getInstance();
		return "@" + USUARIO + " son las " + sfdHora.format(calendarAhora.getTime());
	}

	private String formatearFechaView(String fechaEntrada) {
		SimpleDateFormat formatoSalida = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
		String fechaSalida = "";
		try {
			fechaSalida = formatoSalida.format(formatoEntrada.parse(fechaEntrada));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaSalida;
	}
	
	private String parseCalendarToString(Calendar cal) {
		SimpleDateFormat format1 = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
		
		return format1.format(cal.getTime());
	}
	
	private Calendar parseFechaInput(String fechaEntrada) throws ParseException {
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);
		fechaEntrada += " de " + ano;
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
		cal.setTime(formatoEntrada.parse(fechaEntrada));
		return cal;
	}


	/**
	 * @param palara: palabra a evaluar.
	 * @return string: retorna palabra asociada en el HashMap si existe.
	 */
	private String diccionario(String palabra) {
	    String singular;
	    HashMap<String,String> diccionario = new HashMap<>();
	    diccionario.put("gr","Gramo");
	    diccionario.put("gr.","Gramo");
	    diccionario.put("grs","Gramo");
	    diccionario.put("grs.","Gramo");
	    diccionario.put("gramo","Gramo");
	    diccionario.put("gramos","Gramo");
	    diccionario.put("kilo","Kilo");
	    diccionario.put("kilos","Kilo");
	    diccionario.put("kg","Kilo");
	    diccionario.put("kg.","Kilo");
	    diccionario.put("kilogramo","Kilo");
	    diccionario.put("kilogramos","Kilo");
	    diccionario.put("onza","Onza");
	    diccionario.put("onzas","Onza");
	    diccionario.put("tonelada","Tonelada");
	    diccionario.put("toneladas","Tonelada");
	    diccionario.put("cm3","Cm3");
	    diccionario.put("cm3.","Cm3");
	    diccionario.put("cm.3","Cm3");
	    diccionario.put("centimetro3","Cm3");
	    diccionario.put("centimetros3","Cm3");
	    diccionario.put("gal","Galon");
	    diccionario.put("gal.","Galon");
	    diccionario.put("galon","Galon");
	    diccionario.put("galones","Galon");
	    diccionario.put("lt","Litro");
	    diccionario.put("lt.","Litro");
	    diccionario.put("lts","Litro");
	    diccionario.put("lts.","Litro");
	    diccionario.put("litro","Litro");
	    diccionario.put("litros","Litro");
	    diccionario.put("mm","Milimetro");
	    diccionario.put("mm.","Milimetro");
	    diccionario.put("mms","Milimetro");
	    diccionario.put("mms.","Milimetro");
	    diccionario.put("milimetro","Milimetro");
	    diccionario.put("milimetros","Milimetro");
	    diccionario.put("cm","Centimetro");
	    diccionario.put("cm.","Centimetro");
	    diccionario.put("cms","Centimetro");
	    diccionario.put("cms.","Centimetro");
	    diccionario.put("centimetro","Centimetro");
	    diccionario.put("centimetros","Centimetro");
	    diccionario.put("mt","Metro");
	    diccionario.put("mt.","Metro");
	    diccionario.put("mts","Metro");
	    diccionario.put("mts.","Metro");
	    diccionario.put("metro","Metro");
	    diccionario.put("metros","Metro");
	    diccionario.put("km","Kilometro");
	    diccionario.put("km.","Kilometro");
	    diccionario.put("kms","Kilometro");
	    diccionario.put("kms.","Kilometro");
	    diccionario.put("kilometro","Kilometro");
	    diccionario.put("kilometros","Kilometro");
	    diccionario.put("pie","Pie");
	    diccionario.put("pies","Pie");
	    diccionario.put("plg","Pulgada");
	    diccionario.put("plg.","Pulgada");
	    diccionario.put("pulg","Pulgada");
	    diccionario.put("pulg.","Pulgada");
	    diccionario.put("pulgada","Pulgada");
	    diccionario.put("pulgadas","Pulgada");
	    diccionario.put("seg","Segundo");
	    diccionario.put("seg.","Segundo");
	    diccionario.put("segs","Segundo");
	    diccionario.put("segs.","Segundo");
	    diccionario.put("segundo","Segundo");
	    diccionario.put("segundos","Segundo");
	    diccionario.put("min","Minuto");
	    diccionario.put("min.","Minuto");
	    diccionario.put("mins","Minuto");
	    diccionario.put("mins.","Minuto");
	    diccionario.put("minuto","Minuto");
	    diccionario.put("minutos","Minuto");
	    diccionario.put("h","Hora");
	    diccionario.put("h.","Hora");
	    diccionario.put("hs","Hora");
	    diccionario.put("hs.","Hora");
	    diccionario.put("hora","Hora");
	    diccionario.put("horas","Hora");
	    diccionario.put("dia","Dia");
	    diccionario.put("dias","Dia");
	    singular = diccionario.get(palabra);
	    return singular != null ? singular : palabra;
	}	
	
}