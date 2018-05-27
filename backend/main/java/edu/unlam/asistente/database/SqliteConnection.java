package edu.unlam.asistente.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {

	private static SqliteConnection instance;
    private Connection connection;
    private String url = "jdbc:sqlite:database/database.sqlite";
    
    private SqliteConnection() throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public Connection getConnection() {
        return connection;
    }

    public static SqliteConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new SqliteConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new SqliteConnection();
        }

        return instance;
    }
	
}
