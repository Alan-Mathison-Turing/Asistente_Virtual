package edu.unlam.asistente.entidades;

public class MensajeChat {

	private String usuario;
	private String mensaje;
	
	public MensajeChat(String mensaje, String usuario) {
		this.usuario = usuario;
		this.mensaje = mensaje;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getMensaje() {
		return mensaje;
	}
}
