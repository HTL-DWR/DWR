package pkgDatabase;

import java.sql.*;

public class DatabaseConnection {
 
	   //static final String DB_URL = "jdbc:oracle:thin:@192.168.128.151:1521:ora11g"; //intern
	   static final String DB_URL = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g"; //extern
	   //  Database credentials 
	   String USER = null;
	   String PASS = null;
 
	   public DatabaseConnection(String username, String password){
		   USER  = username;
		   PASS = password;
	   }
	   
	   Connection conn = null;
	   Statement stmt = null;
	
	   
	   public Connection createConnection() throws Exception {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
	   }
	   
	   
	    
	   

		public ResultSet getData(String statement){
			try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(statement,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery();
		//	while (rs.next()) {
		
		//	}
		//	conn.close();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
				return null;
			}
		}
	   
	  
	   
	   public void closeCon(){
		   try {
					conn.close();
	   	  } catch (SQLException e) {
	   		  // TODO Auto-generated catch block
	   		  e.printStackTrace();
	   	  }
	      
		}
	   /* Für Dorf X/Y oder so
	    * SELECT d.name, t.X, t.Y
   			FROM dorf d,
   			TABLE(SDO_UTIL.GETVERTICES(d.location)) t; 
	    * */
	   
	


	   public void insert(String query) throws Exception{
				conn = createConnection();
				PreparedStatement stmt = null;
				stmt = conn.prepareStatement(query);
				stmt.executeQuery();
		
	   }
	   
	   
	   
	      //STEP 5: Extract data from result set
	  /*    while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         int age = rs.getInt("age");
	         String first = rs.getString("first");
	         String last = rs.getString("last");

	         //Display values
	         System.out.print("ID: " + id);
	         System.out.print(", Age: " + age);
	         System.out.print(", First: " + first);
	         System.out.println(", Last: " + last);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();*/	
}	
