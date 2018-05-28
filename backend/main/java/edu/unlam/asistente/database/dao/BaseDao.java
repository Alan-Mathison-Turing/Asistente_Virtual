package edu.unlam.asistente.database.dao;

import java.sql.SQLException;

import edu.unlam.asistente.database.SqliteConnection;

public abstract class BaseDao {
	
	protected SqliteConnection db;
	
	public BaseDao() throws SQLException {
		db = SqliteConnection.getInstance();
	}

}
