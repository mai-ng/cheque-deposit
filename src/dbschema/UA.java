package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UA {
	private Connection connection;
	
	/**
	 * check whether the given user has the given role
	 */
	private PreparedStatement stmtUA_exists;
	
	public UA(Connection conn) throws SQLException {
		connection = conn;
		
		stmtUA_exists = conn.prepareStatement("SELECT EXISTS( SELECT *  FROM secureuml_permittedusersroles  "
				+ "WHERE userrole_user = ? AND userrole_role=?);");
	}
	
	public boolean UA_exists(String uname, String rtitle) throws SQLException{
		stmtUA_exists.setString(1, uname);
		stmtUA_exists.setString(2, rtitle);
		ResultSet res = stmtUA_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
