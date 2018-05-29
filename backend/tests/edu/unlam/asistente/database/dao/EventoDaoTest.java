package edu.unlam.asistente.database.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.dao.EventoDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

import org.junit.Assert;


public class EventoDaoTest {
	
	List<Evento> listaEventos;
	Evento nuevoEvento;
	Usuario user;
	EventoDao eventoDao;
	
	@Before
	public void setUp() throws SQLException, ParseException {
		
		eventoDao = new EventoDao();
		
		user = new Usuario();
		user.setId(1);
		user.setUsuario("testUser");
		
		listaEventos = new ArrayList<>();
		listaEventos.add(new Evento(1, "2018-05-22 01:10:08", "test event 1"));
		listaEventos.add(new Evento(2, "2018-12-30 05:00:00", "test event 2"));
		
		Usuario usuario = new Usuario();
		usuario.setId(2);
		usuario.setUsuario("usuarioTestInsert");
		nuevoEvento = new Evento();
		nuevoEvento.setFecha("2018-05-30 05:00:00");
		nuevoEvento.setDescripcion("test insert");
		nuevoEvento.setUsuarios(new HashSet<Usuario>(Arrays.asList(usuario)));
	}
	
	@Test
	public void obtenerEventosPorUsuarioTest() {
		List<Evento> resultado = eventoDao.obtenerEventosPorUsuario(user);
		Assert.assertTrue(listaEventos.equals(resultado));
	}
	
	@Test
	public void crearEventoTest() {
		eventoDao.createEvent(nuevoEvento);
		Evento aux = eventoDao.obtenerEventoPorId(nuevoEvento.getId());
		Assert.assertEquals(nuevoEvento, aux);
		
	}
}
