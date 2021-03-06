package edu.unlam.asistente.comunicacion;

/**
 * Clase destinada a la comunicacion entre cliente(frontend) y servidor(backend) 
 *
 */
public class Mensaje implements java.io.Serializable {
	
	private static final long serialVersionUID = 7487715337063242056L;
	private String mensaje;
	private String nombreUsuario;
	
	public Mensaje(String mensaje, String nombreUsuario) {
		this.mensaje = mensaje;
		this.nombreUsuario = nombreUsuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
