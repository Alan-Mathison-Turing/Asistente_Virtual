package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.ChuckNorrisFacts;
import edu.unlam.asistente.database.pojo.Usuario;

/**
 * Clase que administra las distintas consultas de facts sobre Chuck Norris.
 * <br>
 */
public class ChuckNorrisFactDao extends BaseDao {

	/**
	 * Query para obtener un fact de Chuck Norris a un usuario que este no haya
	 * visto. <br>
	 */
	private static final String QUERY_CHUCK_FACT_USUARIO = "SELECT CNF FROM ChuckNorrisFacts CNF WHERE NOT EXISTS (SELECT CN FROM ChuckNorrisFacts CN JOIN CN.usuarios U WHERE U.id = :Id_Usuario AND CNF = CN ) ORDER BY RANDOM()";

	/**
	 * Crea una instancia de facts de Chuck Norris. <br>
	 * 
	 * @throws SQLException
	 *             Si la conexión con la base de datos falla. <br>
	 */
	public ChuckNorrisFactDao() throws SQLException {
		super();
	}

	/**
	 * Devuelve un Chuck Norris fact a un usuario.
	 * <p>
	 * <b><i>Nota</i></b>: Este usuario no puede volver a recibir el mismo fact
	 * hasta que haya recibio todos los facts existentes en la base de datos.
	 * <br>
	 * 
	 * @param usuario
	 *            Usuario que pide el fact. <br>
	 * @return Chuck Norris fact. <br>
	 */
	@SuppressWarnings("unchecked")
	public String obtenerFact(final Usuario usuario) {
		Session session = null;
		String chuckFact = null;
		try {
			session = factory.openSession();
			session.beginTransaction();
			Query<ChuckNorrisFacts> fact = session.createQuery(QUERY_CHUCK_FACT_USUARIO).setParameter("Id_Usuario",
					usuario.getId());
			// Si no hay nada es porque se mostraron todos los facts que podían
			// existir.
			if (!fact.list().iterator().hasNext()) {
				// Le quitamos todos los facts que haya visto.
				usuario.getChuckNorrisFacts().clear();
				session.update(usuario);
				// Volvemos a obtener un fact.
				fact = session.createQuery(QUERY_CHUCK_FACT_USUARIO).setParameter("Id_Usuario", usuario.getId());
			}
			ChuckNorrisFacts chuckNorrisFacts = fact.list().iterator().next();
			usuario.getChuckNorrisFacts().add(chuckNorrisFacts);
			chuckNorrisFacts.getUsuarios().add(usuario);
			session.update(usuario);
			session.update(chuckNorrisFacts);
			session.getTransaction().commit();
			chuckFact = chuckNorrisFacts.getFact();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
			chuckFact = "El fact era tan groso que Chuck Norris no nos deja compartirlo. Intente más tarde.";
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return chuckFact;
	}
}
