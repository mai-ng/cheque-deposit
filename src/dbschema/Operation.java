package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operation {

	private Connection connection;
	private PreparedStatement stmtOperation_exists;
	
	
	public Operation(Connection conn) throws SQLException {
		connection = conn;
		stmtOperation_exists = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM secureuml_users  WHERE user_name = ?);");
	}
	
	public boolean Operation_exists(String uname) throws SQLException {
		stmtOperation_exists.setString(1, uname);
		ResultSet res = stmtOperation_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
