package dbschema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposit {

	private Connection connection;
	
	private PreparedStatement stmtDeposit_selectId;
	private PreparedStatement stmtDeposit_selectClient;
	private PreparedStatement stmtDeposit_updateStatus;
	private PreparedStatement stmtDeposit_existsCheque;
	
	public Deposit(Connection conn) throws SQLException {
		connection = conn;
		stmtDeposit_selectId = conn.prepareStatement("SELECT deposit_id FROM deposits "
				+ "	WHERE deposit_cheque = ? AND deposit_client = ?;");
		
		stmtDeposit_selectClient = conn.prepareStatement("SELECT deposit_client FROM deposits "
				+ "	WHERE deposit_cheque = ?;");
		
		stmtDeposit_updateStatus = conn
				.prepareStatement("update deposits set deposit_status= ? "
						+ "where deposit_cheque=? and deposit_client=?;");
		stmtDeposit_existsCheque = conn
				.prepareStatement("SELECT EXISTS( SELECT *  FROM deposits  WHERE deposit_cheque = ?);");
	}
	
	
	public boolean Deposit_existsCheque(int cheque)
			throws SQLException {
		stmtDeposit_existsCheque.setInt(1, cheque);
		ResultSet res = stmtDeposit_existsCheque.executeQuery();
		if (res.next() & res.getInt(1) == 1)
			return true;
		else
			return false;
	}

	public int Deposit_selectId(int cheque, int client) throws SQLException{
		stmtDeposit_selectId.setInt(1, cheque);
		stmtDeposit_selectId.setInt(2, client);
		ResultSet depositID = stmtDeposit_selectId.executeQuery();	
		if(depositID.next())
			return depositID.getInt(1);
		else
			return -1;
		
	}
	
	public int Deposit_selectClient(int cheque) throws SQLException{
		stmtDeposit_selectClient.setInt(1, cheque);
		ResultSet client = stmtDeposit_selectClient.executeQuery();	
		if(client.next())
			return client.getInt(1);
		else
			return -1;
		
	}
	
	public void Deposit_updateStatus(int cheque, int client, String stt) throws SQLException{
		stmtDeposit_updateStatus.setString(1, stt);
		stmtDeposit_updateStatus.setInt(2, cheque);
		stmtDeposit_updateStatus.setInt(3, client);
		stmtDeposit_updateStatus.executeUpdate();
	}
}
