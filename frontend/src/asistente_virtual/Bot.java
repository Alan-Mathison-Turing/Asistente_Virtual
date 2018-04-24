package asistente_virtual;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Bot {

	static String MSG_NO_ENTIENDO = "No entiendo lo que me estas diciendo";
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public String leerMensaje(String mensaje) {
		
		String respuesta = "";
		
		switch (mensaje) {
		default : respuesta = MSG_NO_ENTIENDO;
			
		}
		
		return respuesta;
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
	}
	
}
