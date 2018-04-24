package asistente_virtual;

public class Bot {

	static String MSG_NO_ENTIENDO = "No entiendo lo que me estas diciendo";
	
	public String leerMensaje(String mensaje) {
		
		String respuesta = "";
		
		switch (mensaje) {
		default : respuesta = MSG_NO_ENTIENDO;
			
		}
		
		return respuesta;
	}
	
}
