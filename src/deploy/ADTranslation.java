
package deploy;

import java.sql.Connection;
import java.sql.SQLException;

import dbschema.Cheque;
import dbschema.Client;
import dbschema.Deposit;
import enums.ACCESS;

public class ADTranslation {

	private Connection connection;	
	
	private Client t_client;
	private Cheque t_cheque;	
	private Deposit t_deposit;
	
	private ActionsHistory t_actionhistory;
	
	public ADTranslation(Connection conn) throws SQLException {
		connection = conn;		
		
		t_actionhistory = new ActionsHistory(conn);
		t_client = new Client(conn);
		t_cheque = new Cheque(conn);
		t_deposit = new Deposit(conn);
	}

	
	public ACCESS AD_Deposit_Save(int ch, int cl, String user)
			throws SQLException {
		// Get the deposit corresponds to a given cheque and a given client
		int deposit = t_deposit.Deposit_selectId(ch, cl);
				
		// check if the operation Deposit_validate_Label is executed or not?
		boolean isexecutedOp = t_actionhistory.AcHistory_On_Deposit_exists(
				"Deposit__validate_Label", deposit);

		// get the user who executed Deposit_validate_Label
		String executedUser = t_actionhistory.AcHistory_On_Deposit_selectUser(
				"Deposit__validate_Label", deposit);

		if (isexecutedOp == true && executedUser.matches(user)) {
			return ACCESS.granted;
		} else
			return ACCESS.denied;

	}


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
