package edu.unlam.asistente.database.dao;

import java.sql.SQLException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public abstract class BaseDao {
	
	protected static SessionFactory factory;
	
	public BaseDao() {
		try {
		Configuration config = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties());
		factory = config.buildSessionFactory(builder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
