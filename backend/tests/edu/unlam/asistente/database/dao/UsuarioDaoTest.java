package edu.unlam.asistente.database.dao;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

public class UsuarioDaoTest {
	
	private Usuario testUser;
	private UsuarioDao userDao;
	
	@Before
	public void setUp() throws SQLException {
		testUser = new Usuario();
		testUser.setId(1);
		testUser.setUsuario("testUser");
		
		Set<Evento> listaEventos = new HashSet<>();
		listaEventos.add(new Evento(1, "2018-05-22 01:10:08", "test event 1"));
		listaEventos.add(new Evento(2, "2018-12-30 05:00:00", "test event 2"));
		
		testUser.setEventos(listaEventos);
		userDao = new UsuarioDao();
	}
	
	@Test
	public void obtenerUsuarioPorLoginTest() {
		Usuario user = userDao.obtenerUsuarioPorLogin("testUser");
		Assert.assertEquals(testUser, user);
	}
	
	@Test
	public void obtenerUsuarioPorLoginConContactosTest() {
		Usuario user = userDao.obtenerUsuarioPorLogin("testUser");
		Assert.assertTrue(!user.getContactos().isEmpty());
		Assert.assertEquals("usuario2", user.getContactos().get(0).getUsuario());
		Assert.assertEquals("admin", user.getContactos().get(1).getUsuario());
	}
	
	@Test
	public void obtenerUsuarioPorLoginSinContactosTest() {
		Usuario user = userDao.obtenerUsuarioPorLogin("nomUsuario");
		Assert.assertTrue(user.getContactos().isEmpty());
	}
}
