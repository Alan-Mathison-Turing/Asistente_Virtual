package database.dao;

import java.sql.SQLException;

import database.SqliteConnection;

public abstract class BaseDao {
	
	protected SqliteConnection db;
	
	public BaseDao() throws SQLException {
		db = SqliteConnection.getInstance();
	}

}
