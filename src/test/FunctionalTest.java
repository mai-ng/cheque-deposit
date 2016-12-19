package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import deploy.Functional;

public class FunctionalTest {
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
	      
	      Functional func = new Functional(conn);
	      func.Deposit_validate(1,10);
	/*      
	      if(func.Client_exists(20))
	    	  System.out.println("Client is existing!");
	      else
	    	  System.out.println("Client does NOT exist!");
	      
	      
	      if(func.Cheque_exists(4))
	    	  System.out.println("Cheque is existing!");
	      else
	    	  System.out.println("Cheque does NOT exist!");
	      
	      
	      if(func.Deposit_existsCheque(3))
	    	  System.out.println("Cheque is deposited!");
	      else
	    	  System.out.println("Cheque does NOT be deposited!");
	      
	   */   
	      
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	   System.out.println("Goodbye!");
	}//end main

}
