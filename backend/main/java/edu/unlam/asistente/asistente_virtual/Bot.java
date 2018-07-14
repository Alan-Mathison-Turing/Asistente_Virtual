package edu.unlam.asistente.asistente_virtual;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.busqueda_web.BusquedaWeb;
import edu.unlam.asistente.calculadora.Calculadora;
import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.chucknorris.ChuckNorrisFact;
import edu.unlam.asistente.clima.ClimaCiudad;
import edu.unlam.asistente.clima.DiaLluvioso;
import edu.unlam.asistente.conversor_unidades.ConversorUnidad;
import edu.unlam.asistente.cordialidad.Cordialidad;
import edu.unlam.asistente.financiera.acciones.Acciones;
import edu.unlam.asistente.financiera.moneda.ValorMoneda;
import edu.unlam.asistente.imagenes.gif.Gif;
import edu.unlam.asistente.imagenes.gif.Meme;
import edu.unlam.asistente.leyes_robotica.LeyesRobotica;
import edu.unlam.asistente.noticias.NoticiasActaules;
import edu.unlam.asistente.recordatorioEventos.GestionRecordatorio;
import edu.unlam.asistente.rss.Blog;

public class Bot implements IDecision {

	private String nombre;
	private IDecision siguienteDecision;

	public final static String MSG_NO_ENTIENDO = "Disculpa... no entiendo el pedido, @%s ¿podrás repetirlo?";

	public Bot(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}

	@Override
	public String leerMensaje(String mensaje, String usuario) {
		String mensajeOriginal = mensaje;
		mensaje = removerTildes(mensaje.toLowerCase());
		
		Cordialidad cordialidad = new Cordialidad();
		LeyesRobotica leyesRobotica = new LeyesRobotica();
		ChuckNorrisFact chuckFacts = new ChuckNorrisFact();
		Calculadora calculadora = new Calculadora();
		ConversorUnidad conversorUnidad = new ConversorUnidad();
		Calendario calendario = new Calendario();
		GestionRecordatorio gestionRecordatorio = new GestionRecordatorio();
		BusquedaWeb busquedaWeb = new BusquedaWeb();
		ClimaCiudad climaCiudad = new ClimaCiudad();
		DiaLluvioso diaLluvioso = new DiaLluvioso();
		NoticiasActaules noticias = new NoticiasActaules();
		Gif gif = new Gif();
		Blog blog = new Blog(mensajeOriginal);
		Acciones acciones = new Acciones();
		ValorMoneda valorMoneda = new ValorMoneda();
		Meme meme = new Meme();
		Default def = new Default();

		this.setSiguienteDecision(climaCiudad);
		// SI PREGUNTAN POR BUENOS AIRES PIENSA QUE LO ESTOY SALUDANDO EN VEZ DE PREGUNTAR POR EL CLIMA.
		climaCiudad.setSiguienteDecision(diaLluvioso);
		diaLluvioso.setSiguienteDecision(cordialidad);
		cordialidad.setSiguienteDecision(leyesRobotica);
		leyesRobotica.setSiguienteDecision(chuckFacts);
		chuckFacts.setSiguienteDecision(calculadora);
		calculadora.setSiguienteDecision(gestionRecordatorio);
		gestionRecordatorio.setSiguienteDecision(conversorUnidad);
		conversorUnidad.setSiguienteDecision(busquedaWeb);
		busquedaWeb.setSiguienteDecision(gif);
		gif.setSiguienteDecision(noticias);
		noticias.setSiguienteDecision(acciones);
		acciones.setSiguienteDecision(valorMoneda);
		valorMoneda.setSiguienteDecision(blog);
		blog.setSiguienteDecision(calendario);
		calendario.setSiguienteDecision(meme);
		/*
		 * @def debe quedar siempre al final dado que es el que mostrará el mensaje por default.
		 */
		meme.setSiguienteDecision(def);

		return siguienteDecision.leerMensaje(mensaje, usuario);

	}

	// Obtiene el número contenido en un mensaje que matchee el regex.
	public static double obtenerNumero(String mensaje, Pattern formato_numero) {
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
	
	public String getNombre() {
		return this.nombre;
	}
}