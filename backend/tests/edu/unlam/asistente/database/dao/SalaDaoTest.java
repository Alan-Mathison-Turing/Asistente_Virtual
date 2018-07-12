package edu.unlam.asistente.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Usuario;

public class SalaDaoTest {
	
	private Usuario user;
	private Sala sala1;
	private Sala sala2;
	private Sala salaNueva;
	private SalaDao salaDao;
	List<Sala> listaSalas;
	
	@Before
	public void setUp() {
		
		salaDao = new SalaDao();
		
		user = new Usuario();
		user.setUsuario("testUser");
		user.setId(1);
		
		sala1 = new Sala();
		sala1.setId(1);
		sala1.setNombre("testSala1");
		sala1.setDueño(user);
		sala1.setEsPrivada(1);
		
		sala2 = new Sala();
		sala2.setId(2);
		sala2.setNombre("testSala2");
		sala2.setDueño(user);
		sala2.setEsPrivada(0);
		
		listaSalas = new ArrayList<Sala>();
		listaSalas.add(sala1);
		listaSalas.add(sala2);
		
		salaNueva = new Sala();
		salaNueva.setNombre("testInsert");
		salaNueva.setDueño(user);
		salaNueva.setEsPrivada(1);
	}
	
	@Test
	public void obtenerSalasPorUsuarioTest() {
		
		List<Sala> respuesta = salaDao.obtenerSalasPorUsuario(user);
		Assert.assertTrue(listaSalas.equals(respuesta));
	}
	
	@Test
	public void crearEliminarSalaTest() {
		
		salaDao.crearSala(salaNueva);
		Sala aux = salaDao.obtenerSalaPorId(salaNueva.getId());
		Assert.assertEquals(salaNueva, aux);
		salaDao.eliminarSala(salaNueva);
		aux = salaDao.obtenerSalaPorId(salaNueva.getId());
		Assert.assertNull(aux);
	}
}
