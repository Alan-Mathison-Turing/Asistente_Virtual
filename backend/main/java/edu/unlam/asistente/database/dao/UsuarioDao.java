package edu.unlam.asistente.database.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Usuario;
import edu.unlam.asistente.database.dao.BaseDao;

public class UsuarioDao extends BaseDao {

	public UsuarioDao() {
		super();
	}

	public Usuario obtenerUsuarioPorLogin(String login) {

		Usuario user = null;
		Session session = null;
		try {
			session = factory.openSession();

			Query query = session.createQuery("from Usuario where usuario =:login")
					.setParameter("login", login);
			user = (Usuario)query.getSingleResult();
			
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
		
		return user;

	}
	
	public boolean checkLogin(String username, String password) {

		Usuario user = null;
		Session session = null;
		try {
			session = factory.openSession();

			Query query = session.createQuery("from Usuario where usuario =:username and password =:password")
					.setParameter("username", username)
					.setParameter("password", password);
			
			return query.getResultList().size() > 0;
			//return query.getFetchSize() > 0;
			
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
			//return false;
		}
		return false;
		
		

	}

	public boolean existePorNombre(String username) {

		Usuario user = null;
		Session session = null;
		try {
			session = factory.openSession();

			Query query = session.createQuery("from Usuario where usuario =:username")
					.setParameter("username", username);
			
			return query.getResultList().size() > 0;
			//return query.getFetchSize() > 0;
			
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
			//return false;
		}
		return false;
		
		

	}
	
	public void guardar(Usuario usuario) {

		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(usuario);

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
