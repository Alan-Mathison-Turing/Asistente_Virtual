package edu.unlam.asistente.rss;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.unlam.asistente.asistente_virtual.IDecision;
import edu.unlam.asistente.database.dao.SeedDao;
import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Seed;
import edu.unlam.asistente.database.pojo.Usuario;
import edu.unlam.asistente.rss.api.Feed;
import edu.unlam.asistente.rss.api.FeedMessage;
import edu.unlam.asistente.rss.api.RSSFeedParser;

public class Blog implements IDecision {

	private IDecision siguienteDecision;
	private ArrayList<Feed> feedResults;
	private String mensajeOriginal;
	
	private final static String ADD_SEED_REGEX = "@\\w*,? (?:agrega|agregame) \\w* (?:blog) ((?:https?:\\/\\/)?(?:[\\da-z\\.-]+)\\.(?:[a-z\\.]{2,6})(?:[\\/\\w \\.-]*)*\\/?$)";
	private final static String GET_SEED_INFO_REGEX = "@\\w*\\,? (?:quiero|dame|informame|informa)\\s*(?:informacion|info|novedades|noticias)\\s*\\w*\\s*\\w* (?:blogs?)";
	
	@Override
	public String leerMensaje(String mensaje, String usuario) {
		
		Pattern patronRSS =  Pattern.compile(ADD_SEED_REGEX);
		Matcher matcherRSS = patronRSS.matcher(this.mensajeOriginal);
		
		if(this.mensajeOriginal.matches(ADD_SEED_REGEX)) {
			matcherRSS.find();
			String url = matcherRSS.group(1);
			agregarSeed(usuario, url);
			
			return "@" + usuario + ", se agregó el blog a tus favoritos exitósamente.";
		}
		
		if(mensaje.matches(GET_SEED_INFO_REGEX)) {
			Blog rss = new Blog();
			String result = "";
			UsuarioDao usuarioDao = new UsuarioDao();
			Usuario user = usuarioDao.obtenerUsuarioPorLogin(usuario);
			
			Set<Seed> feedList = user.getUrlSeeds();
			
			for(Seed seed : feedList) {
				
				rss.buscarNovedades(seed.getUrl());
				result += rss.toString();	
			}
		
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
	
	public Blog() {
		
	}
	
	public Blog(String mensajeOriginal) {
		this.mensajeOriginal = mensajeOriginal;
	}
	
	public void agregarSeed(String usuario, String url) {
		Seed seed = new Seed();
		Usuario user = new Usuario();
		UsuarioDao userDao = new UsuarioDao();
		user = userDao.obtenerUsuarioPorLogin(usuario);
		seed.setUrl(url);
		seed.setActive(1);
		seed.setUsuario(user);

		SeedDao seadDao = new SeedDao();
		
		seadDao.crearSeed(seed);
		
	}
    	
    @Override
    public String toString() {
    	String parser = "";
    	
    	for(Feed f : feedResults) {
    		parser += "<i>" + f.getTitle() + "</i><br/>" 
    				+ "<a href=\"" + f.getLink() + "\"><u><b>" + f.getLink() + "</b></u></a><br/><br/>";    	
    	}
    	
    	return parser;
    }

}
