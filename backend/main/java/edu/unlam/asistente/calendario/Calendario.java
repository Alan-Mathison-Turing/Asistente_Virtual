package edu.unlam.asistente.calendario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.Bot;
import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.database.pojo.Evento;

public class Calendario implements IDecision{

	private IDecision siguienteDecision;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private final static SimpleDateFormat SQLITE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static String FORMATO_MES = "MMMM";
	private final static String FORMATO_DIA_SEMANA = "EEEE";
	private final static String FORMATO_HORA = "HH:mm a";
	private final static SimpleDateFormat sdfDia = new SimpleDateFormat(FORMATO_DIA_SEMANA, new Locale("es", "ES"));
	private final static SimpleDateFormat sdfMes = new SimpleDateFormat(FORMATO_MES, new Locale("es", "ES"));
	private final static SimpleDateFormat sfdHora = new SimpleDateFormat(FORMATO_HORA, new Locale("es", "ES"));
	
	private Calendar calendarAhora;
	
	public static String getStringDeFecha(Date fecha) {
		return  new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy 'a las' HH:mm", new Locale("es", "ES")).format(fecha);
	}
	
	/**
	 * Metodo que evalua si la fecha ingresada por parametro es mayor a "ahora"
	 * @param fecha
	 */
	public static boolean esFechaProxima(Date fecha) {
		Date ahora = Calendar.getInstance().getTime();
		int resultadoComp = ahora.compareTo(fecha);
		return resultadoComp == 0 || resultadoComp > 0 ? false:true;
	}
	
	/**
	 * Metodo que devuelve el numero del mes correspondiente al string ingresado por
	 * parametro
	 * 
	 * @param monthName
	 * @return
	 * @throws ParseException
	 */
	public static int getNumeroDeNombreMes(String monthName) throws ParseException {
			Date date = sdfMes.parse(monthName);
			Calendar aux = Calendar.getInstance();
			aux.setTime(date);
			return aux.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * Metodo que genera un Date a partir de un String obtenido de sqlite
	 * 
	 * @param fecha
	 * @return
	 * @throws ParseException
	 */
	public static Date getFechaParaSqliteDeString(String fecha) throws ParseException {
		
		return SQLITE_FORMATTER.parse(fecha);
	}
	
	/**
	 * Metodo que obtiene la lista de fechas a partir de una de eventos
	 * @param set
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getDateListFromEventList(Set<Evento> set) throws ParseException{
		
		List<Date> resultado = new ArrayList<>();
		for (Evento evento : set) {
			resultado.add(getFechaParaSqliteDeString(evento.getFecha()));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo que obtiene la fecha mas cercana a "ahora" a partir de una lista
	 * 
	 * @param listaFechas
	 * @return
	 */
	public static Date getFechaMasCercanaDeListaFechas(List<Date> listaFechas) {
		
		if (listaFechas == null) {
			return null;
		}
		final long ahora = System.currentTimeMillis();
		List<Date> aux = new ArrayList<Date>(listaFechas);
		ListIterator<Date> itr = aux.listIterator();
		while(itr.hasNext()) {
			if (ahora - itr.next().getTime() > 0 ) {
				itr.remove();
			}			
		}
		
		return Collections.min(aux, new Comparator<Date>() {
		    public int compare(Date d1, Date d2) {
			        long dif1 = Math.abs(d1.getTime() - ahora);
			        long dif2 = Math.abs(d2.getTime() - ahora);
			        return Long.compare(dif1, dif2);
		    }
		});
	}

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String respuesta = "";
		Pattern formato_numero = Pattern.compile("(\\d)");
		if(mensaje.contains("hora")) {
			return respuesta = "@" + usuario + " son las " + obtenerHora();
		}
		
		if(mensaje.contains("dia es") || mensaje.contains("fecha")) {
			return respuesta = "@" + usuario + " hoy es " +  obtenerFechaHoy();
		}
		
		if(mensaje.contains("semana")) {
			return respuesta = "@" + usuario + " hoy es " + obtenerDiaSemanaHoy();
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("dias") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " será el " + formatearFechaView(fechaDentroDe(numero, "dias"));
		}

		if(mensaje.contains("que dia sera") && mensaje.indexOf("meses") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " será el " + formatearFechaView(fechaDentroDe(numero, "meses"));
		}
		
		if(mensaje.contains("que dia sera") && mensaje.indexOf("anos") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " será el " + formatearFechaView(fechaDentroDe(numero, "anios"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("ayer") >= 0) {
			return respuesta = "@" + usuario + " fue " + formatearFechaView(fechaDentroDe(-1, "dias"));
		}

		if(mensaje.contains("que dia fue") && mensaje.indexOf("dias") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " fue " + formatearFechaView(fechaDentroDe(-numero, "dias"));
		}
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("meses") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " fue " + formatearFechaView(fechaDentroDe(-numero, "meses"));
		}
		
		
		if(mensaje.contains("que dia fue") && mensaje.indexOf("anos") >= 0) {
			int numero = (int) Bot.obtenerNumero(mensaje, formato_numero);
			return respuesta = "@" + usuario + " fue " + formatearFechaView(fechaDentroDe(-numero, "anios"));
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
				return respuesta = "@" + usuario + " entre el " + fecha + " y el " + parseCalendarToString(now) + " pasaron " + dias + " días";
			} catch (ParseException e) {
				e.printStackTrace();
				return respuesta == "" ? Bot.MSG_NO_ENTIENDO : respuesta;
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
				return respuesta = "@" + usuario + " faltan " + dias + " días";
			} catch (ParseException e) {
				e.printStackTrace();
				return respuesta == "" ? Bot.MSG_NO_ENTIENDO : respuesta;
			}
			
		}
		
		return  siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
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
	

	public static int diferenciaEnDias(Calendar fechaDesde, Calendar fechaHasta) {
        int dias = (int) ((fechaHasta.getTimeInMillis() - fechaDesde.getTimeInMillis()) / 86400000);
        return dias;
	}
	
	/**
	 * Metodo que devuelve la fecha de hoy, en castellano
	 * @return
	 */
	private String obtenerFechaHoy() {
		calendarAhora = Calendar.getInstance();
		return calendarAhora.get(Calendar.DAY_OF_MONTH) + " de " + 
				sdfMes.format(calendarAhora.getTime()) + " de " + calendarAhora.get(Calendar.YEAR);
	}
	
	/**
	 * Metodo que devuelve el dia de la semana de hoy, en castellano
	 * @return
	 */
	private String obtenerDiaSemanaHoy() {
		calendarAhora = Calendar.getInstance();
		return sdfDia.format(calendarAhora.getTime());
	}
	
	/**
	 * Metodo que devuelve la hora actual, en formato AM/PM
	 * @return
	 */
	private String obtenerHora() {
		calendarAhora = Calendar.getInstance();
		return sfdHora.format(calendarAhora.getTime());
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
	
}
