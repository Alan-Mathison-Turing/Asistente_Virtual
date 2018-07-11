package edu.unlam.asistente.database.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class BaseDao {

	protected static SessionFactory factory;

	public BaseDao() {
		try {
			Configuration config = new Configuration().configure();
			factory = config.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("-- BaseDao ERROR: ocurrió un error al intentar conectarse a la base de datos.");
			e.printStackTrace();
		}
	}

}
