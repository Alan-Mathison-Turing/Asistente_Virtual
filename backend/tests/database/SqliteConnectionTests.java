package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

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
