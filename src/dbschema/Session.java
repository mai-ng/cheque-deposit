package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {

	private Connection connection;
	
	/**
	 * connect a user with a given role to the system
	 */
	private PreparedStatement stmtSession_insert;
	
	/**
	 * disconnect a user from the system
	 */
	private PreparedStatement stmtSession_delete;
	
	/**
	 * check whether the given user is connecting or not.
	 */
	private PreparedStatement stmtSession_exists;
	
	/**
	 * get the current role of a user who is connecting to the system
	 */
	private PreparedStatement stmtSession_selectRole;	
	
	
	public Session(Connection conn) throws SQLException {
		
		connection = conn;
		
		stmtSession_insert = conn.prepareStatement("INSERT INTO secureuml_session VALUES(?, ?);");
		
		stmtSession_delete = conn.prepareStatement("DELETE FROM secureuml_session WHERE session_user = ?;");
		
		stmtSession_exists = conn.prepareStatement("SELECT EXISTS( SELECT *  FROM secureuml_session  "
				+ "WHERE session_user = ?);");
		
		stmtSession_selectRole = conn.prepareStatement("SELECT session_role FROM secureuml_session "
				+ "WHERE session_user = ?;");
	}
	
	
	public void Session_insert(String uname, String rtitle) throws SQLException{
			stmtSession_insert.setString(1, uname);
			stmtSession_insert.setString(2, rtitle);
			stmtSession_insert.executeUpdate();
		
	}
	
	
	public void Session_delete(String uname) throws SQLException{
			stmtSession_delete.setString(1, uname);
			stmtSession_delete.executeUpdate();
	}
	
	public boolean Session_existsUser(String uname) throws SQLException{
		stmtSession_exists.setString(1, uname);
		ResultSet res = stmtSession_exists.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}
	
	public String Session_selectRole(String uname) throws SQLException{
		stmtSession_selectRole.setString(1, uname);
		ResultSet res = stmtSession_selectRole.executeQuery();
		if (res.next())
			return  res.getString(1);
		else
			return null;
	}
}
