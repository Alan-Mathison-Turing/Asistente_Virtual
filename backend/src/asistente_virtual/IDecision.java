package asistente_virtual;

public interface IDecision {

	public String leerMensaje(String mensaje, String usuario);
	public IDecision getSiguienteDecision();
	public void setSiguienteDecision(IDecision decision);
}
