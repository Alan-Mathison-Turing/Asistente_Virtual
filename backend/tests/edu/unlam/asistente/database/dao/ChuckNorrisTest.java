package edu.unlam.asistente.database.dao;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.pojo.Usuario;

public class ChuckNorrisTest {

	private Usuario usuario;
	private ChuckNorrisFactDao chuckNorrisFactDao;

	@Before
	public void setUp() throws SQLException, ParseException {

		this.chuckNorrisFactDao = new ChuckNorrisFactDao();

		this.usuario = new Usuario();
		this.usuario.setId(1);
		this.usuario.setUsuario("testUser");
	}

	@Test
	public void testNuevoFact() {
		System.out.println(this.chuckNorrisFactDao.obtenerFact(this.usuario));
	}

}
