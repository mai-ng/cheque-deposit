package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import deploy.SecureUMLTranslation;
import enums.ACCESS;

public class OrBACTest {
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
	      
	    //STEP 4: Test OrBACMachine
	      SecureUMLTranslation orbac = new SecureUMLTranslation(conn);
	      
	      Scanner keyboard = new Scanner(System.in);
	      System.out.println("Enter the user to check: ");
	      
	      String usr = keyboard.nextLine();
	      
	      ACCESS res = orbac.secureDeposit_validate(usr);
	      if(res == ACCESS.granted)
	    	  System.out.println("Permission of " + usr + " to validate a deposit is VALID!");
	      else
	    	  System.out.println("Permission of " + usr + " to validate a deposit is INVALID!");
	      
	      //System.out.println("Current role of " + usr + " is: " + orbac.Session_getCurrentRoleOfUser(usr));
	      
	      
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
