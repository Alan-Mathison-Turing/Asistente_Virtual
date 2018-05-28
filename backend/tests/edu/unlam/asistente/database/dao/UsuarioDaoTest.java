package edu.unlam.asistente.database.dao;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.dao.UsuarioDao;
import edu.unlam.asistente.database.pojo.Usuario;

import java.sql.SQLException;

import org.junit.Assert;

public class UsuarioDaoTest {
	
	Usuario testUser;
	UsuarioDao userDao;
	
	@Before
	public void setUp() throws SQLException {
		testUser = new Usuario();
		testUser.setId(1L);
		testUser.setUsuario("testUser");
		
		userDao = new UsuarioDao();
	}
	
	@Test
	public void obtenerUsuarioPorLoginTest() {
		
		Assert.assertEquals(testUser, userDao.obtenerUsuarioPorLogin("testUser"));
	}
}
