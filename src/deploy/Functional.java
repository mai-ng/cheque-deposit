package deploy;

import java.sql.Connection;
import java.sql.SQLException;

import dbschema.Cheque;
import dbschema.Client;
import dbschema.Deposit;
import enums.EXECUTION;

public class Functional {

	private Connection connection;
	
	private Client t_client;
	private Cheque t_cheque;
	
	private Deposit t_deposit;
	
	public Functional(Connection conn) throws SQLException {
		connection = conn;
		
		setT_client(new Client(conn));
		setT_cheque(new Cheque(conn));
		t_deposit = new Deposit(conn);
	}
	
	public EXECUTION Deposit_validate(int ch, int cl) throws SQLException{
		boolean isExistingCh = t_deposit.Deposit_existsCheque(ch);
		int isCorrectCl = t_deposit.Deposit_selectClient(ch);
		
		if(isExistingCh == true && isCorrectCl == cl){
			t_deposit.Deposit_updateStatus(ch, cl, "validated");
			return EXECUTION.suceeded;
		}
		else
			return EXECUTION.failed;		
	}
	
	public EXECUTION Deposit_save(int ch, int cl) throws SQLException{
		boolean isExistingCh = t_deposit.Deposit_existsCheque(ch);
		int isCorrectCl = t_deposit.Deposit_selectClient(ch);
		
		if(isExistingCh == true && isCorrectCl == cl){
			t_deposit.Deposit_updateStatus(ch, cl, "saved");
			return EXECUTION.suceeded;
		}
		else
			return EXECUTION.failed;		
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Client getT_client() {
		return t_client;
	}

	public void setT_client(Client t_client) {
		this.t_client = t_client;
	}

	public Cheque getT_cheque() {
		return t_cheque;
	}

	public void setT_cheque(Cheque t_cheque) {
		this.t_cheque = t_cheque;
	}
}
