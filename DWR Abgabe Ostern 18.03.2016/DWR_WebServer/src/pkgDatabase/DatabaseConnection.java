package pkgDatabase;

import java.sql.*;

public class DatabaseConnection {
 
	   static final String DB_URL = "jdbc:oracle:thin:@192.168.128.151:1521:ora11g"; //intern
	   //static final String DB_URL = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g"; //extern
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
		   String s = "select owner from dorf where id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getCredentials(String username, String password_md5){
		   String s = "select uname,decode(passwd_hash, ? , 'true', 'false') from spieler where uname = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,username);
			stmt.setString(2, password_md5);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public void registerUser(String username, String password_md5) throws Exception{
		   insert("BEGIN CREATE_NEW_USER('" + username + "','Dorf von " + username + "' , '" + password_md5 + "'); END;");
		   String s = "BEGIN CREATE_NEW_USER(?,?,?); END;";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,username);
			stmt.setString(2, "Dorf von "+username);
			stmt.setString(3, password_md5);
			stmt.executeQuery();
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
	   
	   }
	   
	   public ResultSet getDorf(int did){
		   String s = "select id, name, owner from dorf where id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getDorfGeb(int did){
		   String s = "select gt.name, b.lvl from dorf d inner join bau b on b.did=d.id inner join geb_typ gt on b.tid = gt.id where d.id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getDorfRes(int did){
		   String s = "select r.holz, r.stein, r.lehm from resgruppe r " +
					"inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getDorfUnits(int did){ 
		   String s = "select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where t.owner = d.owner and d.id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getDorfReinforcements(int did){
		   String s = "select t.owner, t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did " +
					"where not t.owner = d.owner and d.id= ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet getAllUnitsInDorf(int did){ 
		   String s ="select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where d.id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,did);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   
	   public ResultSet getMap(){
		   return getData("select to_char(d.id), round(t.x, 20) as X , round(t.y,20) as Y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t");
	   }
	   
	   public ResultSet getSpielerDetail(String username){
		   String s ="select s.uname, d.id  from spieler s inner join dorf d on d.owner = s.uname where s.uname = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,username);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   	
	   public void BuildCommand(int did, String gebtyp) throws Exception{
		   String s = "BEGIN BUILD_COMMAND(?,?); END;";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, did);
			stmt.setString(2,gebtyp);
			stmt.executeQuery();
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
	   }
	   
	   public void RecruitCommand(int did, int cargo) throws Exception{
		   getDataWithExceptions("BEGIN CREATE_TRUPPENAUSBILDUNG('"+did+"','"+cargo+"');END");
		   String s = "BEGIN CREATE_TRUPPENAUSBILDUNG(?,?);END";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, did);
			stmt.setInt(2, cargo);
			stmt.executeQuery();
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
	   }
	   
	   public ResultSet getCurrentRekrutEvents(int dorfId) {
			  String s ="select e.dauer,e.beginn,t.schwert,t.reiter,t.bogen,t.lanze " +
						"from event e " +
						"inner join stat_event se on e.id=se.id " +
						"inner join truppe t on t.id=se.cargo " +
						"where se.dorf=?";
			   try{
				conn = createConnection();
				PreparedStatement stmt = null;

				stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1,dorfId);
				ResultSet rs = stmt.executeQuery();
				return rs;
				}catch(Exception e){
					System.out.println("Error in get Data" + e.getMessage());
				}
			  return null;
	   }
	   
	   public ResultSet getCurrentBuildEvents(int dorfId) {
			 String s ="select e.dauer,e.beginn,se.geb_typ,d.name,b.lvl " +
						"from event e " +
						"inner join stat_event se on e.id=se.id " +
						"inner join dorf d on d.id=se.DORF " +
						"inner join bau b on b.did=d.id " +
						"where d.id=? and b.tid = se.GEB_TYP";
			   try{
				conn = createConnection();
				PreparedStatement stmt = null;

				stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1,dorfId);
				ResultSet rs = stmt.executeQuery();
				return rs;
				}catch(Exception e){
					System.out.println("Error in get Data" + e.getMessage());
				}
			  return null;
	   }
	   
	   public ResultSet getBoardPreviewList() {
		   return getData("select id, name from board");
	   }
	   
	   public ResultSet getBoardInforamtion(int boardId) {
		   String s ="select id, name, op, text, post_time from board where id = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,boardId);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
		   
	   }
	   
	   public ResultSet getBoardComments(int boardId) {
		   String s ="select id, op, text, post_time from kommentar where board = ?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,boardId);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null;
	   }
	   
	   public ResultSet checkCredentials (String uname, String passwd_hc)
	   {
		   String s ="select DECODE(COUNT(*),0,'false',1,'true') from spieler where uname= ? and passwd_hash=?";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,uname);
			stmt.setString(2, passwd_hc);
			ResultSet rs = stmt.executeQuery();
			return rs;
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			}
		  return null; 
	   }
	   
	   public void postComment(int boardId, String text, String opName) throws Exception {

		   String s ="BEGIN POST_COMMENT(?,?,?); END;";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,boardId);
			stmt.setString(2,text);
			stmt.setString(3, opName);
			stmt.executeQuery();
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			} 
		
	   }
	   
	   public void newBorad(String name, String text, String op) throws Exception {
			
		   insert("BEGIN NEW_BOARD('" + name + "','" + text + "','" + op + "'); END;");
		   String s ="BEGIN NEW_BOARD(?,?,?); END;";
		   try{
			conn = createConnection();
			PreparedStatement stmt = null;

			stmt = conn.prepareStatement(s, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,name);
			stmt.setString(2,text);
			stmt.setString(3, op);
			stmt.executeQuery();
			}catch(Exception e){
				System.out.println("Error in get Data" + e.getMessage());
			} 
		
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
