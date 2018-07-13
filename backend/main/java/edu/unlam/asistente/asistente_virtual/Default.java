package edu.unlam.asistente.asistente_virtual;

public class Default implements IDecision {

	private IDecision siguienteDecision;
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		return String.format(Bot.MSG_NO_ENTIENDO, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		throw new RuntimeException("No se puede agregar otro comportamiento");
	}

}
