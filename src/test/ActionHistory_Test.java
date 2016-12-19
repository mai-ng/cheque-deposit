package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import deploy.ADTranslation;
import deploy.ActionsHistory;
import enums.ACCESS;
import enums.EXECUTION;

public class ActionHistory_Test {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/chequedeposit06oct15";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	   
public static void main(String[] args) throws Exception {
		
		Connection conn = null;
		//Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	    //STEP 4: Test ADMachine
	      ActionsHistory hisAc = new ActionsHistory(conn);
	      
/*	      Scanner keyboard = new Scanner(System.in);
	      System.out.println("Enter the user to check: ");	      
	      String usr = keyboard.nextLine();
	      
	      EXECUTION executed = hisAc.ExecutionDeposit_Validate(1, 10, usr);
	      if(executed == EXECUTION.suceeded)
	    	  System.out.println("Execution of Deposit_Validate is stored in database !");
	      else
	    	  System.out.println("Execution of Deposit_Validate failed to store in database!");*/
	      
	      String executedusr = hisAc.AcHistory_On_Deposit_selectUser("Deposit__validate_Label", 3);
	      System.out.println("Executed user: " + executedusr);
	      
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	   //System.out.println("Goodbye!");
	}//end main
}
