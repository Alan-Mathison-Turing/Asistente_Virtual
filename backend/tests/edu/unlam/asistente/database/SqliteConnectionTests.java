package edu.unlam.asistente.database;

import edu.unlam.asistente.database.SqliteConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class SqliteConnectionTests {

	static SqliteConnection conn;
	
	@BeforeClass
	public static void setConnection() throws SQLException {
		conn = SqliteConnection.getInstance();
	}
	
	@Test
	public void connectionSuccessful() {
		Assert.assertNotNull(conn);
	}
	
	@Test
	public void getUsuario() throws SQLException {
		Statement smt = conn.getConnection().createStatement();
		ResultSet rs = smt.executeQuery("SELECT * FROM usuarios");
		Assert.assertNotNull(rs);
	}
}
