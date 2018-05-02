package asistente_virtual;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Bot {

	static String MSG_NO_ENTIENDO = "No entiendo lo que me estas diciendo";
	 public final static String USUARIO = "José Java";
		
	 public final static String mensaje_boot_hola= "Hola";
	 public final static String mensaje_boot_chau= "Chau";
	 public final static String mensaje_boot_buenos= "Buen";
	 public final static String mensaje_boot="Gracias";
	 public final static String mensaje_boot_hey="Hey";
	 Calendar calendario = new GregorianCalendar();
	 
	public String leerMensaje(String mensaje) {
		
		String respuesta = "";
		
		switch (mensaje) {
		default : respuesta = MSG_NO_ENTIENDO;
			
		}
		
		return respuesta;
	}
	
	
	private String saludar(String saludo) {
		 		
		 if(saludo.toUpperCase().contains(mensaje_boot_hola.toUpperCase()) || saludo.toUpperCase().contains(mensaje_boot_hey.toUpperCase())) {
			 return mensaje_boot_hola +" " + Bot.USUARIO;
		 }
		 
		 if(saludo.toUpperCase().contains(mensaje_boot_chau.toUpperCase())) {
			 return mensaje_boot_chau + " " + Bot.USUARIO;
		 }
		 
		if(saludo.toUpperCase().contains(mensaje_boot_buenos.toUpperCase())) {
				 if(calendario.get(Calendar.HOUR_OF_DAY)>= 13 &&  calendario.get(Calendar.HOUR_OF_DAY) <=19 || calendario.get(Calendar.HOUR)>01 && calendario.get(Calendar.HOUR)<=07) {
					 return "Buenas tardes " + Bot.USUARIO;
				 }
				 else {
					 if(calendario.get(Calendar.HOUR_OF_DAY) < 13) {
						 return "Buenos dias " + Bot.USUARIO;
						 }
					 else
						 return "Buenas noches " + Bot.USUARIO;
				 }
		}				 
		  return "Disculpe " + Bot.USUARIO + ", no entiendo lo que me dice...";
	 }
	 
	 private String agradecer(String palabra) {
		 if(palabra.toUpperCase().contains(mensaje_boot.toUpperCase())) {
			 return "De nada, estoy para ayudar " + Bot.USUARIO;
		 }
		 
		 return "Hasta luego " + Bot.USUARIO;
		 
	 }

	 
	 
	
}
