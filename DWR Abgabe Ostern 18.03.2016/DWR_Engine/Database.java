package Engine;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;

public class Database {
	 // JDBC driver name and database URL
	//   static final String JDBC_DRIVER =  "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:oracle:thin:@192.168.128.151:1521:ora11g"; //intern
	   //static final String DB_URL = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g"; //extern

	   //static final String configFile = "./config.cfg";
	   //int EUTimer = 10;
	   //int RUTimer = 10;
	   //  Database credentials 
	   String USER = null;
	   String PASS = null;
	
	   /*
	   public void updateConfig(){
		   File f = new File(configFile);
		   f.delete();
		   try {
			   		f.createNewFile();
					FileOutputStream fos = null;
					fos = new FileOutputStream(f);
					ObjectOutputStream oos = null;
				    oos = new ObjectOutputStream(fos);
				    oos.writeInt(EUTimer);
				    oos.writeInt(RUTimer);
				    oos.close();
				    fos.close();
					
				} catch (IOException e) {
					System.out.println("Config create Error! " +e.getMessage());
				}
	   }
	   */
	   
	   public Database(String username, String password){
		   USER  = username;
		   PASS = password;
		   
		   /*File f = new File(configFile);
		   if(!f.exists()){
			   try {
				    f.createNewFile();
					FileOutputStream fos = null;
					fos = new FileOutputStream(f);
					ObjectOutputStream oos = null;
				    oos = new ObjectOutputStream(fos);
				    oos.writeInt(EUTimer);
				    oos.writeInt(RUTimer);
				    oos.close();
				    fos.close();
					
				} catch (IOException e) {
					System.out.println("Config create Error! " +e.getMessage());
				}
		   }
		   FileInputStream fis = null;
		   try {
			fis = new FileInputStream(f);
			ObjectInputStream ois = null;
			ois = new ObjectInputStream(fis);
			EUTimer = ois.readInt();
			RUTimer = ois.readInt();
			ois.close();
			fis.close();
		} catch (Exception e) {
			System.out.println("Config read Error! " +e.getMessage());
		}*/
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
				System.out.println("Error in get Data");
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
	   /* F�r Dorf X/Y oder so
	    * SELECT d.name, t.X, t.Y
   			FROM dorf d,
   			TABLE(SDO_UTIL.GETVERTICES(d.location)) t; 
	    * */
	   
	/*   public ResultSet getKommentare(String boardName){
		   return getData("select kommentar.text from board inner join kommentar on kommentar.board=board.id where board.name ='" + boardName + "'");
	   }
	   
	   public ResultSet getRessourcesFromVillage(int villageId){
		   return getData("select r.name,r.ANZAHL from resourcen_gruppen r inner join dorf d on d.id=r.did where d.id = '" + villageId + "'");
	   }
	   
	   public ResultSet getRessourcenMovementEvent(int eventId){
		   return getData("select rd.name,rd.anzahl from bew_event e inner join resourcen_gruppen rd on rd.id=e.CARGO where e.ID= '"+eventId+"'");
	   }
	   
	   public ResultSet getNewVillageRessourcesInRessourceMovementEvent(int eventId){
		   return getData("select mov_res_name, SUM(anzahl) from (select movres.name as mov_res_name,movres.anzahl  from bew_event e inner join resourcen_gruppen movres on movres.id=e.CARGO where e.ID= '"+ eventId +"' union all select rd.name as dest_res_name,rd.ANZAHL from bew_event e inner join resourcen_gruppen rd on rd.did=e.NACH_DORF where e.ID='"+ eventId +"') group by mov_res_name");
	   }
	   
	   public ResultSet getTroopCount(int villageId){
		   return getData("select t.name,t.ANZAHL from truppen_einheiten t inner join dorf d on t.did=d.id where d.id='"+ villageId +"'");
	   }
	   
	   public ResultSet getTroopMovementEvent(int eventId){
		   return getData("select d.name as von,d2.name as nach,t.name,t.anzahl from BEW_EVENT e inner join dorf d on d.id=e.von_dorf inner join dorf d2 on d2.id=e.nach_dorf inner join truppen_einheiten t on t.id=e.cargo where e.id='"+ eventId + "'");
	   }


*/


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

