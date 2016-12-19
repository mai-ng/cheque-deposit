package deploy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbschema.Cheque;
import dbschema.Client;
import dbschema.Deposit;
import enums.EXECUTION;

public class ActionsHistory {

	private Connection connection;	
	
	private Functional funcMachine;
	private Client t_client;
	private Cheque t_cheque;	
	private Deposit t_deposit;
	
	private PreparedStatement stmtActionHistory_insert;	
	private PreparedStatement stmtActionHistoryOnDeposit_exists;	
	private PreparedStatement stmtActionHistoryOnDeposit_selectUser;
	
	private PreparedStatement stmtLastExecutedAction;
	
	public ActionsHistory(Connection conn) throws SQLException {

		connection = conn;
		
		funcMachine = new Functional(conn);	
		t_client = new Client(conn);
		t_cheque = new Cheque(conn);
		t_deposit = new Deposit(conn);
		
		stmtActionHistory_insert = connection
				.prepareStatement("INSERT INTO ad_actionshistory(actionhistory_operation, actionhistory_deposit, actionhistory_user, actionhistory_time) "
						+ "VALUES(?, ?, ?, ?);");
		
		stmtActionHistoryOnDeposit_exists = connection
				.prepareStatement("SELECT EXISTS( SELECT *  FROM ad_actionshistory  "
				+ "WHERE actionhistory_operation = ? AND actionhistory_deposit = ?);");
		
		
		stmtActionHistoryOnDeposit_selectUser = connection.
				prepareStatement("SELECT actionhistory_user FROM ad_actionshistory "
						+ "WHERE actionhistory_operation = ? AND actionhistory_deposit = ?;");
		
		stmtLastExecutedAction = connection.prepareStatement("SELECT MAX(actionhistory_id) FROM ad_actionshistory");
	}
	
/*************updating operations
	 * @throws SQLException *************************/
	public EXECUTION ExecutionDeposit_Validate(int ch, int cl, String user)
			throws SQLException {
		
		boolean isExistingCh = t_deposit.Deposit_existsCheque(ch);
		int isCorrectCl = t_deposit.Deposit_selectClient(ch);
		
		if(isExistingCh == true && isCorrectCl == cl){
			
			//Get the deposit corresponds to this cheque and this client as the inputs
			int deposit = t_deposit.Deposit_selectId(ch, cl);
			AcHistory_On_Deposit_insert("Deposit__validate_Label", deposit, user);
			return funcMachine.Deposit_validate(ch, cl);
			} 
		else
				return EXECUTION.failed;
	}
	
	
	public EXECUTION ExecutionDeposit_Save(int ch, int cl, String user)
			throws SQLException {
		boolean isExistingCh = t_deposit.Deposit_existsCheque(ch);
		int isCorrectCl = t_deposit.Deposit_selectClient(ch);
		
		if(isExistingCh == true && isCorrectCl == cl){
			
			//Get the deposit corresponds to this cheque and this client as the inputs
			int deposit = t_deposit.Deposit_selectId(ch, cl);
			AcHistory_On_Deposit_insert("Deposit__save_Label", deposit, user);
			return funcMachine.Deposit_validate(ch, cl);
			} 
		else
				return EXECUTION.failed;
	}
	
	
	
/******************
 * Methods of SQL statements
 * @throws SQLException 
 * *****************/	
	
	public void AcHistory_On_Deposit_insert(String ope, int de, String user) throws SQLException{
		//define the moment of the execution by:
		//Get the last number of executed actions in the table ad_actionshistory
		// The order execution of an action should be the same as its actionhistory_id in the table ad_actionshistory
		int exOrder = 0;
		ResultSet orderExecution = stmtLastExecutedAction.executeQuery();
		if(orderExecution.next())
			exOrder	= orderExecution.getInt(1);			
		//execute the query	
		stmtActionHistory_insert.setString(1, ope);
		stmtActionHistory_insert.setInt(2, de);
		stmtActionHistory_insert.setString(3, user);
		stmtActionHistory_insert.setInt(4, exOrder + 1);
		stmtActionHistory_insert.executeUpdate();
	}
	
	
	
	/**
	 * Check if a given operation is completed on a given deposit
	 * @param op
	 * @param de
	 * @return
	 * @throws SQLException
	 */
	public boolean AcHistory_On_Deposit_exists(String op, int de)throws SQLException {			
			stmtActionHistoryOnDeposit_exists.setString(1, op);
			stmtActionHistoryOnDeposit_exists.setInt(2, de);
			ResultSet isExisting = stmtActionHistoryOnDeposit_exists.executeQuery();			

			if (isExisting.next()) {
				return true;
			} else
				return false;
		
	}
	
	
	/**
	 * 
	 * @param op
	 * @param de
	 * @return the user who executed a given operation on a given deposit
	 * @throws SQLException
	 */
	public String AcHistory_On_Deposit_selectUser(String op, int de)throws SQLException {
			//get the user who executed Deposit__validate_Label
			stmtActionHistoryOnDeposit_selectUser.setString(1, op);
			stmtActionHistoryOnDeposit_selectUser.setInt(2, de);
			ResultSet executedUser = stmtActionHistoryOnDeposit_selectUser.executeQuery();

			if (executedUser.next()) {
				return executedUser.getString(1);
			} else
				return null;
		
	}
}
