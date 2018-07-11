package edu.unlam.asistente.database.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Sala;
import edu.unlam.asistente.database.pojo.Usuario;

public class SalaDao extends BaseDao{
	
	public SalaDao(){
		super();
	}
	
	public List<Sala> obtenerSalasPorUsuario(Usuario usuario){
		
		List<Sala> listaEventos = null;
		Session session = null;
		try {
			session = factory.openSession();
			String hql = "select distinct s from Sala s " + "join s.usuarios u " + "where u.usuario = :user";
			Query query = session.createQuery(hql).setParameter("user", usuario.getUsuario());

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
}
