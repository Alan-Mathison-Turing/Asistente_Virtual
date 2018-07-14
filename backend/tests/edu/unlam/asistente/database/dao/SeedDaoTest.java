package edu.unlam.asistente.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Seed;
import edu.unlam.asistente.database.pojo.Usuario;

public class SeedDaoTest {
	private Usuario user;
	private Seed seed1;
	private Seed seed2;
	private SeedDao seedDao;
	List<Seed> listaSeeds;
	
	@Before
	public void setUp() {
		
		seedDao = new SeedDao();
		
		user = new Usuario();
		user.setUsuario("testUser");
		user.setId(1);
		
		seed1 = new Seed();
		seed1.setId(1);
		seed1.setUrl("https://www.20minutos.com.mx/rss/cultura/");
		seed1.setUsuario(user);
		seed1.setActive(1);
		
		seed2 = new Seed();
		seed2.setId(2);
		seed2.setUrl("https://www.20minutos.com.mx/rss/cultura/");
		seed2.setUsuario(user);
		seed2.setActive(1);
		
	}
	
	@Test
	public void obtenerSeedsPorUsuarioTest() {
		Seed respuesta = seedDao.obtenerSeedPorId(seed1.getId());
		Assert.assertTrue(listaSeeds.equals(respuesta));
	}
	
	@Test
	public void crearEliminarSeedTest() {
		
		seedDao.crearSeed(seed2);;
		Seed aux = seedDao.obtenerSeedPorId(seed2.getId());
		Assert.assertEquals(seedDao, aux);
		seedDao.eliminarSeed(seed2);
		aux = seedDao.obtenerSeedPorId(seed2.getId());
		Assert.assertNull(aux);
	}
}
