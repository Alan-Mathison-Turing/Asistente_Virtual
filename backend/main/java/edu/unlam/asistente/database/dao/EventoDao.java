package edu.unlam.asistente.database.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;
import edu.unlam.asistente.database.dao.BaseDao;

public class EventoDao extends BaseDao {

	public EventoDao() throws SQLException {
		super();
	}

	public List<Evento> obtenerEventosPorUsuario(Usuario user) {

		List<Evento> listaEventos = null;
		Session session = null;
		try {
			session = factory.openSession();
			String hql = "select distinct e from Evento e " + "join e.usuarios u " + "where u.usuario = :user";
			Query query = session.createQuery(hql).setParameter("user", user.getUsuario());

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

	public void createEvent(Evento evento) {

		Session session = null;
		try {
			session = factory.openSession();
			session.saveOrUpdate(evento);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
