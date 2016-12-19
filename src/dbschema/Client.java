package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

	private Connection connection;
	
	private PreparedStatement stmtClient_exists;
	
	
	public Client(Connection conn) throws SQLException {
		connection = conn;
		
		stmtClient_exists = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM clients  WHERE client_id = ?);");
	}
	
	public boolean Client_exists(int nb) throws SQLException {
		stmtClient_exists.setInt(1, nb);
		ResultSet res = stmtClient_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
