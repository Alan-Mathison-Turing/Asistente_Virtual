package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unlam.asistente.database.pojo.Usuario;
import edu.unlam.asistente.database.dao.BaseDao;

public class UsuarioDao extends BaseDao {

	public UsuarioDao() throws SQLException {
		super();
	}

	public Usuario obtenerUsuarioPorLogin(String login) {

		Usuario user = null;
		Session session = null;
		try {
			session = factory.openSession();

			Query query = session.createQuery("from edu.unlam.asistente.database.pojo.Usuario where usuario =:login")
					.setParameter("login", login);

			user = (Usuario) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			return user;
		}
		
		

	}

}
