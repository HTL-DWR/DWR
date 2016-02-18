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
	   
		public ResultSet getDataWithExceptions(String statement) throws Exception{
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
			//	System.out.println("Error in get Data" + e.getMessage());
				throw new Exception(e.getMessage());
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
	   /* F�r Dorf X/Y oder so
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
	   
	   
	   
	   /*
	   * Ausgelagerte Funktionen
	   */
	   
	   public ResultSet getOwnerFromDorfById(int did){
		   ResultSet rs = getData("select owner from dorf where id="+did);
		   return rs;
	   }
	   
	   public ResultSet getCredentials(String username, String password_md5){
		   return getData("select uname,decode(passwd_hash, '" + password_md5 + "', 'true', 'false') from spieler where uname = '" + username + "'");
	   }
	   
	   public void registerUser(String username, String password_md5) throws Exception{
		   insert("BEGIN CREATE_NEW_USER('" + username + "','Dorf von " + username + "' , '" + password_md5 + "'); END;");
	   }
	   
	   public ResultSet getDorf(int did){
		   return getData("select id, name, owner from dorf where id = " + did);
	   }
	   
	   public ResultSet getDorfGeb(int did){
		   return getData("select gt.name, b.lvl from dorf d " +
					"inner join bau b on b.did=d.id inner join geb_typ gt on b.tid = gt.id where d.id = " + did);
	   }
	   
	   public ResultSet getDorfRes(int did){
		   return getData("select r.holz, r.stein, r.lehm from resgruppe r " +
					"inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.id = " + did);
	   }
	   
	   public ResultSet getDorfUnits(int did){
		   return getData("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where t.owner = d.owner and d.id = " + did); 
	   }
	   
	   public ResultSet getDorfReinforcements(int did){
		   return getData("select t.owner, t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did " +
					"where not t.owner = d.owner and d.id= " + did);
	   }
	   
	   public ResultSet getAllUnitsInDorf(int did){
		   return getData("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where d.id = " + did); 
	   }
	   
	   
	   public ResultSet getMap(){
		   return getData("select to_char(d.id), round(t.x, 20) as X , round(t.y,20) as Y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t");
	   }
	   
	   public ResultSet getSpielerDetail(String username){
		   return getData("select s.uname, d.id  from spieler s inner join dorf d on d.owner = s.uname where s.uname = '" + username + "'");
	   }
	   	
	   public void BuildCommand(int did, String gebtyp) throws Exception{
		   getDataWithExceptions("BEGIN BUILD_COMMAND('"+did+"','"+gebtyp+"'); END;");
	   }
	   
	   public void RecruitCommand(int did, int cargo) throws Exception{
		   getDataWithExceptions("BEGIN CREATE_TRUPPENAUSBILDUNG('"+did+"','"+cargo+"');END");
	   }
	   
	   /*
	    * End of Auslagerung
	    */
	   
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
