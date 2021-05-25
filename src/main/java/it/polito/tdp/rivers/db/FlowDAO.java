package it.polito.tdp.rivers.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;


public class FlowDAO {
	
	
public List<Flow> getRiverFlow(River river) {
		
		final String sql = "SELECT  f.day as day, f.flow as flow, f.river as id, r.name as name "
				+ "FROM flow f, river r "
				+ "WHERE  f.river = r.id && r.id =? "
				+ "ORDER BY DAY ASC ";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				River r = new River(res.getInt("id"), res.getString("name"));
				
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"), r));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}

public int getConteggio(River river) {
	String sql = "SELECT COUNT(*) as cnt "
			+ "FROM flow f, river r "
			+ "WHERE  f.river = r.id && r.id = ?";
	
	int conteggio =0;
	
	
	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, river.getId());
		ResultSet res = st.executeQuery();

		if (res.first()) {
			conteggio = res.getInt("cnt");
			
		}

		conn.close();
		
	} catch (SQLException e) {
		//e.printStackTrace();
		throw new RuntimeException("SQL Error");
	}

	
	return conteggio;
	
	
}


}


