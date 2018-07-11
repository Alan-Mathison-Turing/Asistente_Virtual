package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.unlam.asistente.database.pojo.Usuario;

/**
 * Test de facts de Chuck Norris. <br>
 */
public class ChuckNorrisFactDaoTest {
	/**
	 * Usuario de prueba. <br>
	 */
	private Usuario usuario;
	/**
	 * Instancia del fact. <br>
	 */
	private ChuckNorrisFactDao chuckNorrisFactDao;
	/**
	 * Cantidad de facts existentes en la base de datos. <br>
	 */
	private static final int CANTIDAD_FACTS = 50;

	@Before
	public void setUp() throws SQLException {
		this.chuckNorrisFactDao = new ChuckNorrisFactDao();
		this.usuario = new Usuario();
		this.usuario.setId(1);
		this.usuario.setUsuario("testUser");
	}

	@Test
	public void testNuevoFact() {
		System.out.println(this.chuckNorrisFactDao.obtenerFact(this.usuario));
	}

	@Ignore
	public void testTodosLosFacts() {
		for (int i = 0; i < CANTIDAD_FACTS; i++) {
			System.out.println(this.chuckNorrisFactDao.obtenerFact(this.usuario));
		}
	}
}
