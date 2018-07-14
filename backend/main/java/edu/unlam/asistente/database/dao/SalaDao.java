package edu.unlam.asistente.database.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Usuario;

public class SalaDao extends BaseDao {

	public SalaDao() {
		super();
	}

	public Sala obtenerSalaPorId(Integer id) {

		Sala sala = null;
		Session session = null;
		try {
			session = factory.openSession();
			sala = session.get(Sala.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
				;
			}
			return sala;
		}
	}

	public List<Sala> obtenerSalasPorUsuario(Usuario usuario) {

		List<Sala> listaEventos = null;
		Session session = null;
		try {
			session = factory.openSession();
			String hql = "select s from Sala s, in(s.usuarios) u " + "where u.id = :userId";
			Query query = session.createQuery(hql).setParameter("userId", usuario.getId());

			listaEventos = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return listaEventos;
		}
	}

	public void crearSala(Sala sala) {

		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.clear();
			session.saveOrUpdate(sala);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void eliminarSala(Sala sala) {
		
		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.remove(sala);
			
			for (Usuario user : sala.getUsuarios()) {
				user.getSalas().remove(sala);
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
	
	public void agregarUsuario(Sala sala, Usuario contacto) {

		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			sala.getUsuarios().add(contacto);
			
			session.update(sala);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
