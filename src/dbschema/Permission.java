package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permission {
	private Connection connection;
	
	/**
	 * check whether the given role is allowed to execute a given operation
	 */
	private PreparedStatement stmtPermission_exists;
	
	public Permission(Connection conn) throws SQLException {

		connection = conn;
		stmtPermission_exists = conn.prepareStatement("SELECT EXISTS(SELECT *  FROM secureuml_permissions  "
				+ "WHERE permission_role = ? AND permission_operation= ?);");
	}
	
	public boolean Permission_exists(String role, String ope) throws SQLException{
		stmtPermission_exists.setString(1, role);
		stmtPermission_exists.setString(2, ope);
		ResultSet res = stmtPermission_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
}
