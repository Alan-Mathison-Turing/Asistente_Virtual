package edu.unlam.asistente.database.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.unlam.asistente.database.pojo.Seed;

public class SeedDao extends BaseDao {

	public SeedDao() {
		super();
	}

	public void crearSeed(Seed seed) {
		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(seed);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void eliminarSeed(Seed seed) {

		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.remove(seed);

			for (Seed seedActual : seed.getUsuario().getUrlSeeds()) {
				if (seedActual.equals(seed)) {
					seed.getUsuario().getUrlSeeds().remove(seed);
					break;
				}
			}

			session.flush();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Seed obtenerSeedPorId(Integer id) {

		Seed seed = null;
		Session session = null;
		try {
			session = factory.openSession();
			seed = session.get(Seed.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
				;
			}
			return seed;
		}
	}



}
