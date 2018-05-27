package edu.unlam.asistente.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.unlam.asistente.calendario.Calendario;
import edu.unlam.asistente.database.pojo.Evento;
import edu.unlam.asistente.database.pojo.Usuario;

public class EventoUsuarioDao extends BaseDao{

	public EventoUsuarioDao() throws SQLException {
		super();
	}
	
	public List<Evento> obtenerEventosPorUsuario(Usuario user){
		
		List<Evento> listaEventos = new ArrayList<>();
		Statement stmt = null;
		try {
			stmt = super.db.getConnection().createStatement();
			StringBuffer query = new StringBuffer();
			
			query.append("select e.id, e.fecha, e.descripcion ");
			query.append("from eventos e where e.id in( ");
			query.append("select id_evento from usuarioEventos "); 
			query.append("where id_usuario = "+ user.getId() +" );");
			
			ResultSet rs = stmt.executeQuery(query.toString());
			
			Evento evento;
			if (rs != null) {
				while(rs.next()) {
					evento = new Evento();
					evento.setId(rs.getInt("id"));
					Date fecha = Calendario.getDateFromString(rs.getString("fecha"));
					evento.setFecha(fecha);
					evento.setDescripcion(rs.getString("descripcion"));
					listaEventos.add(evento);
				}
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
			return listaEventos;
		}
	}

}
