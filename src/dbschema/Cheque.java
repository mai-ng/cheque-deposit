package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cheque {

	private Connection connection;
	
	private PreparedStatement stmtCheque_exists;
	
	public Cheque(Connection conn) throws SQLException {
		connection = conn;
		
		stmtCheque_exists = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM cheques  WHERE cheque_id = ?);");
	}
	
	public boolean Cheque_exists(int nb) throws SQLException {
		stmtCheque_exists.setInt(1, nb);
		ResultSet res = stmtCheque_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
