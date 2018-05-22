package database.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import org.junit.Assert;

import database.entity.Usuario;

public class UsuarioDaoTest {
	
	Usuario testUser;
	UsuarioDao userDao;
	
	@Before
	public void setUp() throws SQLException {
		testUser = new Usuario();
		testUser.setId(1L);
		testUser.setLogin("testUser");
		
		userDao = new UsuarioDao();
	}
	
	@Test
	public void obtenerUsuarioPorLoginTest() {
		
		Assert.assertEquals(testUser, userDao.obtenerUsuarioPorLogin("testUser"));
	}
}
