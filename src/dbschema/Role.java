package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Role {

	private Connection connection;
	private PreparedStatement stmtRole_exists;
	
	
	public Role(Connection conn) throws SQLException {
		connection = conn;
		stmtRole_exists = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM secureuml_users  WHERE user_name = ?);");
	}
	
	public boolean Role_exists(String uname) throws SQLException {
		stmtRole_exists.setString(1, uname);
		ResultSet res = stmtRole_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
