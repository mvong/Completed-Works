package game_logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import packet.UserInfo;

public class JDBCAuthentication {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/animalkingdomuserauthentication";
	
	static final String USER = "root";
	static final String PASS = "root";

	private static Connection conn;
	private static Statement stmt;
	
	public JDBCAuthentication() {
		// open a connection
		System.out.println("Connecting to a selected database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected database successfully...");

		stmt = null;
	}
	
	public int getGamesWon(UserInfo user) {
		boolean result = false;
		Statement statement = null;
		String returnString = "";
		//SELECT B from table_name WHERE A = 'a';
		
		try {
			// execute query
			System.out.println("Updating game stats for " + user.getUsername());
		    statement = conn.createStatement();
		    String sql = "SELECT games_won from " + "akuserauthentication WHERE user_name = '" + user.getUsername() + "'";
		    ResultSet rs = statement.executeQuery(sql);
		    while (rs.next()) {
		        returnString = rs.getString("games_won");
		    }
		    System.out.println("in jdbc got games played for " + user.getUsername() + " : ");
		    System.out.println("games played: " + returnString);
			result = true;
		} catch (SQLException sqle) {
			System.out.println("Error in writeToDatabase");
			sqle.printStackTrace();
		}
		
		if (result) {
			System.out.println("Successfully updated game stats in table");
		}
		else {
			System.out.println("Could not update game stats in table");
		}
		
		return returnString.equals("") ? 0 : Integer.parseInt(returnString);		
	}
	
	public int getGamesPlayed(UserInfo user) {
		boolean result = false;
		Statement statement = null;
		String returnString = "";
		//SELECT B from table_name WHERE A = 'a';
		
		try {
			// execute query
			System.out.println("Updating game stats for " + user.getUsername());
		    statement = conn.createStatement();
		    String sql = "SELECT games_played from " + "akuserauthentication WHERE user_name = '" + user.getUsername() + "'";
		    ResultSet rs = statement.executeQuery(sql);
		    while (rs.next()) {
		        returnString = rs.getString("games_played");
		    }
		    System.out.println("in jdbc got games played for " + user.getUsername() + " : ");
		    System.out.println("games played: " + returnString);
			result = true;
		} catch (SQLException sqle) {
			System.out.println("Error in writeToDatabase");
			sqle.printStackTrace();
		}
		
		if (result) {
			System.out.println("Successfully updated game stats in table");
		}
		else {
			System.out.println("Could not update game stats in table");
		}
		
		return returnString.equals("") ? 0 : Integer.parseInt(returnString);		
	}
	
	public boolean updateGameStats(UserInfo user, int gamesPlayed, int gamesWon) {
		boolean result = false;
		Statement statement = null;
		
		/* example:
		 	UPDATE Customers
			SET ContactName='Alfred Schmidt', City='Hamburg'
			WHERE CustomerName='Alfreds Futterkiste';
		 */
		
		try {
			// execute query
			System.out.println("Updating game stats for " + user.getUsername());
		    statement = conn.createStatement();
		    String sql = "UPDATE akuserauthentication " +
		                 "SET games_played = " + gamesPlayed + ", games_won = " + gamesWon + " WHERE user_name = '" + user.getUsername() + "'";
		    statement.executeUpdate(sql);
		    System.out.println("in jdbc updated stats for " + user.getUsername() + " : ");
		    System.out.println("games played: " + gamesPlayed);
		    System.out.println("games won: " + gamesWon);
			result = true;
		} catch (SQLException sqle) {
			System.out.println("Error in writeToDatabase");
			sqle.printStackTrace();
		}
		
		if (result) {
			System.out.println("Successfully updated game stats in table");
		}
		else {
			System.out.println("Could not update game stats in table");
		}
		
		return result;		
	}
	
	public boolean writeToDatabase(UserInfo user) {
		
		boolean result = false;
		Statement statement = null;
		
		// if database does not contain the user, insert into table
		if (!containsUser(user)) {
			try {
				// execute a query
				System.out.println("Inserting records into the table...");
				statement = conn.createStatement();
				System.out.println("Statement has been assigned");
				
				String sql = "INSERT INTO akuserauthentication (user_name, user_password, games_played, games_won) " +
							 "VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getGamesPlayed() + "', '" + user.getGamesWon() + "')";
				statement.executeUpdate(sql);
				System.out.println("Inserted records into the table...");
				result = true;
				
			} catch (SQLException sqle) {
				System.out.println("Error in writeToDatabase");
				sqle.printStackTrace();
			}
		}

		if (result) {
			System.out.println("Successfully inserted into table");
		}
		else {
			System.out.println("Could not insert into table");
		}
		
		return result;
	}
	
	// if the database contains this username
	public boolean containsUser(UserInfo user)
	{
		boolean resultTF = false;
		ResultSet result = null;
		Statement statement = null;
		
		try {
			// register for jdbc driver
			//Class.forName("com.mysql.jdbc.Driver");
			
						
			statement = conn.createStatement();
		    //"UserAuthenticationTable" is the name of the table, "user_name" is the primary key.
		    String sql = "SELECT * FROM akuserauthentication WHERE user_name = '" + user.getUsername() + "'";
		    result = statement.executeQuery(sql);
		    //There is no need for a loop because the primary key is unique.
		    resultTF = result.next();
			
		} catch (SQLException sqle) {
			System.out.println("Error writing to sql database");
			sqle.printStackTrace();
			
		}
		
		if (resultTF) {
			System.out.println("Successfully found user");
		}
		else {
			System.out.println("Could not find user");
		}
		
		return resultTF;
	}
	
	// checks if the username matches the password
	public boolean isValidUser(UserInfo user) {
		Statement statement = null;
        ResultSet result = null;  
        boolean resultTF = false;
        
        try {
        	// register for jdbc driver
			//Class.forName("com.mysql.jdbc.Driver");
			
			// open a connection
//			System.out.println("Connecting to a selected database...");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			System.out.println("Connected database successfully...");
//			
        	statement = conn.createStatement();
        	String sql = "SELECT * FROM akuserauthentication WHERE user_name = '" + user.getUsername() + "' AND user_password = '" + user.getPassword() + "'";
        	result = statement.executeQuery(sql);
        	resultTF = result.next();        	
        } catch (SQLException sqle) {
        	System.out.println("Error querying in isValidUser");
        	sqle.printStackTrace();
        } 
        
        if (resultTF) {
    		System.out.println("This user has been found");
    	}
    	else {
    		System.out.println("User has not been found");
    	}
        
        return resultTF;
	}
}