package deploy;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbschema.Operation;
import dbschema.Permission;
import dbschema.Role;
import dbschema.Session;
import dbschema.UA;
import dbschema.User;
import enums.ACCESS;

public class SecureUMLTranslation {

	private Connection connection;
	private User t_user;
	private Role t_role;
	private Operation t_operation;
	private Permission t_perm;
	private UA t_ua;
	private Session t_session;
	
	public SecureUMLTranslation(Connection conn) throws SQLException {
		this.setConnection(conn);

		t_user = new User(conn);
		t_role = new Role(conn);
		t_operation = new Operation(conn);
		t_perm = new Permission(conn);
		t_ua = new UA(conn);
		t_session = new Session(conn);
	}
	
	
	
	/**
	 * A user can connect with a given role only if the role was assigned to him
	 * and he is currently not connecting to the system.
	 * @param uid
	 * @param rid
	 * @throws SQLException
	 */
	public void connectUser(String uname, String rtitle) throws SQLException{
		boolean isExistingUA = t_ua.UA_exists(uname, rtitle);
		boolean isConnectingU = t_session.Session_existsUser(uname);
		
		if(isExistingUA == true && isConnectingU == false)
			t_session.Session_insert(uname, rtitle);
		
	}
	
	/**
	 * Only users connecting to the system can be disconnected.
	 * @param uid
	 * @throws SQLException
	 */
	public void disconnectUser(String uname) throws SQLException{
		
		boolean isConnectingU = t_session.Session_existsUser(uname);
		
		if(isConnectingU == true)
			t_session.Session_delete(uname);
	}
	
	
	/*************Secure operations
	 * @throws SQLException *************************/

	public ACCESS secureDeposit_validate(String uname) throws SQLException {
		//If the user is connecting
		boolean isConnectingU = t_session.Session_existsUser(uname);
		if (isConnectingU == true) {
			//Get the current role of the user
			String currentRole = t_session.Session_selectRole(uname);
			
			//check the existance of a permission
			boolean isExistingPerm = t_perm.Permission_exists(currentRole, "Deposit_validate_Label");
			if (isExistingPerm == true )
				return ACCESS.granted;
			else
				return ACCESS.denied;
		}
		return ACCESS.denied;

	}
	
	public ACCESS secureDeposit__save(String uname) throws SQLException{
		//If the user is connecting
		boolean isConnectingU = t_session.Session_existsUser(uname);
		if (isConnectingU == true) {
			//Get the current role of the user
			String currentRole = t_session.Session_selectRole(uname);
			
			//check the existance of a permission
			boolean isExistingPerm = t_perm.Permission_exists(currentRole, "Deposit_save_Label");
			if (isExistingPerm == true )
				return ACCESS.granted;
			else
				return ACCESS.denied;
		}
		return ACCESS.denied;
	}

	/************* setter and getter of Connection ******************/
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
