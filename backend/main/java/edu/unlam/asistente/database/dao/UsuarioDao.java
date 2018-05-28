package edu.unlam.asistente.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.unlam.asistente.database.pojo.Usuario;

public class UsuarioDao extends BaseDao{
	
	public UsuarioDao() throws SQLException {
		super();
	}
	
	public Usuario obtenerUsuarioPorLogin(String login) {
		
		Usuario user = new Usuario();
		Statement stmt = null;
		try {
			 stmt = super.db.getConnection().createStatement();
			String query = "SELECT ID, USUARIO FROM USUARIOS WHERE USUARIO = '" + login + "';";
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs != null && rs.next()) {
				user.setId(rs.getInt("id"));
				user.setLogin(rs.getString("usuario"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return user;
		}
	}

}
