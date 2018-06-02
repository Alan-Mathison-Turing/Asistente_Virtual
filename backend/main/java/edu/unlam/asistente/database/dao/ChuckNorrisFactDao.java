package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.ChuckNorrisFacts;
import edu.unlam.asistente.database.pojo.Usuario;

public class ChuckNorrisFactDao extends BaseDao {

	/**
	 * Query para obtener un fact de Chuck Norris a un usuario que este no haya
	 * visto. <br>
	 */
	private static final String NOT_QUERY_CHUCK_FACT_USUARIO = "SELECT id, fact FROM ChuckNorrisFacts Cnf WHERE NOT EXISTS( SELECT 1 FROM Usuario U, UsuarioChuckFacts Ucf WHERE U.id = Ucf.id_usuario AND Cnf.id = Ucf.id_Fact AND U.id = :id_usuario) ORDER BY RANDOM()";

	private static final String QUERY_CHUCK_FACT_USUARIO = "SELECT id, fact FROM ChuckNorrisFacts Cnf WHERE NOT EXISTS ( SELECT 1 FROM ChuckNorrisFacts CN JOIN Usuario U WHERE Cnf.id = CN.id AND U.id = :Id_Usuario ORDER BY RANDOM() )";

	/**
	 * Query para eliminar todos los facts visto por el usuario para volver a
	 * emepzar de cero. <br>
	 */
	private static final String QUERY_DELETE_FACTS_USUARIO = "DELETE FFROM Usuario U JOIN ChuckNorrisFacts.Usuario Cnu WHERE Cnu.id_usuario = :Id_Usuario";

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
		Transaction transaction = null;
		String chuckFact = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Query<ChuckNorrisFacts> fact = session.createQuery(QUERY_CHUCK_FACT_USUARIO).setParameter("Id_Usuario",
					usuario.getId());
			// Si no hay nada es porque se mostraron todos los facts que podían
			// existir.
			if (fact.list().isEmpty()) {
				Query<Integer> query = session.createQuery(QUERY_DELETE_FACTS_USUARIO).setParameter("Id_Usuario",
						usuario.getId());
				query.executeUpdate();
				// Volvemos a obtener un fact.
				fact = session.createQuery(QUERY_CHUCK_FACT_USUARIO).setParameter("Id_Usuario", usuario.getId());
			}
			ChuckNorrisFacts chuckNorrisFacts = (ChuckNorrisFacts) fact.list().iterator().next();
			usuario.agregarChuckFact(chuckNorrisFacts);			
			session.save(usuario);
			transaction.commit();
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
