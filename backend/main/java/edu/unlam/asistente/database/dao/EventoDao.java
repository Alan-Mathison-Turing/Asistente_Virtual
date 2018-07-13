package edu.unlam.asistente.database.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.dao.BaseDao;

public class EventoDao extends BaseDao {

	public EventoDao() {
		super();
	}

	public List<Evento> obtenerEventosPorUsuario(Integer userId) {

		List<Evento> listaEventos = null;
		Session session = null;
		try {
			session = factory.openSession();
			String hql = "select distinct e from Evento e " + "join e.usuarios u " + "where u.usuario = :user";
			Query query = session.createQuery(hql).setParameter("user", userId);

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
	
	public Evento obtenerEventoPorId(Integer id) {
		
		Evento evento = null;
		Session session = null;
		try {
			session = factory.openSession();
			evento = session.get(Evento.class, id);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();;
			}
			return evento;
		}
	}

	public void crearEvento(Evento evento) {

		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(evento);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
