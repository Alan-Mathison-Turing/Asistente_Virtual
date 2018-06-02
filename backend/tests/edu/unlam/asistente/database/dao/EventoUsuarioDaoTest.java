package edu.unlam.asistente.database.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.database.dao.EventoUsuarioDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

import org.junit.Assert;


public class EventoUsuarioDaoTest {
	
	List<Evento> listaEventos;
	Usuario user;
	EventoUsuarioDao eventoUsuarioDao;
	
	@Before
	public void setUp() throws SQLException, ParseException {
		
		eventoUsuarioDao = new EventoUsuarioDao();
		
		user = new Usuario();
		user.setId(1L);
		user.setLogin("testUser");
		
		listaEventos = new ArrayList<>();
		listaEventos.add(new Evento(1L, Calendario.getDateFromString("2018-05-22 01:10:08"), "test event 1"));
		listaEventos.add(new Evento(2L, Calendario.getDateFromString("2018-12-30 05:00:00"), "test event 2"));
	}
	
	@Test
	public void obtenerEventosPorUsuarioTest() {
		List<Evento> resultado = eventoUsuarioDao.obtenerEventosPorUsuario(user);
		Assert.assertTrue(listaEventos.equals(resultado));
	}
}