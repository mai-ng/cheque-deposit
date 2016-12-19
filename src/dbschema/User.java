package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private Connection connection;
	private PreparedStatement stmtUser_exists;
	
	
	public User(Connection conn) throws SQLException {
		connection = conn;
		stmtUser_exists = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM secureuml_users  WHERE user_name = ?);");
	}
	
	public boolean User_exists(String uname) throws SQLException {
		stmtUser_exists.setString(1, uname);
		ResultSet res = stmtUser_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
