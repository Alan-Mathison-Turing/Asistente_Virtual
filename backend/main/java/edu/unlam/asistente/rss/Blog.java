package edu.unlam.asistente.rss;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.rss.api.Feed;
import edu.unlam.asistente.rss.api.FeedMessage;
import edu.unlam.asistente.rss.api.RSSFeedParser;

public class Blog implements IDecision {

	private IDecision siguienteDecision;
	private ArrayList<Feed> feedResults;
	private String urlSeed;
	
	private final static String ADD_SEED_REGEX = "@\\w*\\,* (?:agrega|suma) \\D* (?:blog)\\s*(https?:\\/\\/www\\.\\w*\\.\\w*(?:\\.?\\w*\\/?\\w*\\/?\\w*))";
	private final static String GET_SEED_INFO_REGEX = "@\\w*\\,? (?:quiero|dame|informame|informa)\\s*(?:informacion|info|novedades|noticias)\\s*\\w*\\s*\\w* (?:blogs?)";
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		
		Pattern patronRSS =  Pattern.compile(ADD_SEED_REGEX);
		Matcher matcherRSS = patronRSS.matcher(mensaje);
		
		if(mensaje.matches(ADD_SEED_REGEX)) {
			matcherRSS.find();
			String url = "";
			if(agregarSeed(usuario, url) == 1) {
				return "@" + usuario + ", se agregó el blog a tus favoritos exitósamente.";
			} else {
				return "@" + usuario + ", no se pudo agregar el blog, revisa la URL enviada.";
			}
		}
		
		if(mensaje.matches(GET_SEED_INFO_REGEX)) {
			Blog rss = new Blog();
			String result = "";
			// test: https://www.20minutos.com.mx/rss/cultura/
			/*try {
				//List<RSS> feedList = new RSSDao().obtenerUsuarioSeeds(new UsuarioDao().obtenerUsuarioPorLogin(usuario));
				
				for(RSS feed : feedList) {
					rss.buscarNovedades(feed.getUrlSeed());
					result += rss.toString();	
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			return result;
		}
		
		return siguienteDecision.leerMensaje(mensaje, usuario);
	}

	@Override
	public IDecision getSiguienteDecision() {
		return siguienteDecision;
	}

	@Override
	public void setSiguienteDecision(IDecision decision) {
		siguienteDecision = decision;
	}
	
	public int agregarSeed(String usuario, String url) {
		// TODO: Lógica para agregar seeds a un usuario, recordar controlar que la misma no exista para él. 
		
		return -1;
	}
	
	public void buscarNovedades(String url) {
		RSSFeedParser parser = new RSSFeedParser(url);
		Feed feed = parser.readFeed();

		feedResults = new ArrayList<Feed>();
		
		int contador = 0;
        for (FeedMessage message : feed.getMessages()) {
        	feedResults.add(new Feed(message.getTitle(), message.getLink()));
        	if(++contador == 3) break;
        }
		
	}
    
	// constructor por default
	public Blog() {}
	
	// constructor para generar listado de urls de un usuario
	public Blog(String urlSeed) {
		this.urlSeed = urlSeed;
	}
	
    @Override
    public String toString() {
    	String parser = "";
    	
    	for(Feed f : feedResults) {
    		parser += "<i>" + f.getTitle() + "</i>\n" 
    				+ "<a href=\"" + f.getLink() + "\"><u><b>" + f.getLink() + "</b></u></a>\n\n";    	
    	}
    	
    	return parser;
    }

}
