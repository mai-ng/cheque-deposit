package deploy;

import java.sql.Connection;
import java.sql.SQLException;

import enums.ACCESS;
import enums.EXECUTION;

public class FilterMachine {

	private Connection connection;
	private SecureUMLTranslation secureuml;
	private ADTranslation secureAD;
	private ActionsHistory hisMachine;
	
	public FilterMachine(Connection conn) throws SQLException {
		connection = conn;
		secureuml = new SecureUMLTranslation(conn);
		secureAD = new ADTranslation(conn);
		hisMachine = new ActionsHistory(conn);
	}
	
	
	public EXECUTION FilterDeposit__save(int cheque, int client, String user) throws SQLException{
		ACCESS staticRight = secureuml.secureDeposit__save(user);
		if(staticRight == ACCESS.granted){
			ACCESS dynRight = secureAD.AD_Deposit_Save(cheque, client, user);
			if(dynRight == ACCESS.granted)
				return hisMachine.ExecutionDeposit_Save(cheque, client, user);
			else
				return EXECUTION.failed;
		}
		else
			return EXECUTION.failed;
	}


	
	/************* setter and getter of Connection ******************/
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
