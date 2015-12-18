package pkgService;


//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.LoginStatus;

@Path("/Credentials")
public class Credentials {
	
	static DatabaseConnection connection= null;
	
	public Credentials(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON}) 
	public LoginStatus check(@QueryParam("username") String username, @QueryParam("password_md5") String password_md5) {
		LoginStatus retVal = null;
		ResultSet rs = null;
		
		
		try {
			rs = connection.getData("select uname,decode(passwd_hash, '" + password_md5 + "', 'true', 'false') from spieler where uname = '" + username + "'");
			
			if(rs.next()) {
				
				System.out.print(rs.getString(2));
				
				if(rs.getString(2).equals("false")) {
					retVal = new LoginStatus("Password Falsch", username, "no id");
				}
				else if(rs.getString(2).equals("true")){
					retVal = new LoginStatus ("Login Erfolgreich", username, "5555");
				}
			}
			else {
				retVal = new LoginStatus("Username Falsch", username, "no id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new LoginStatus("Internal System Error: Database unreachable", " ", "no id" );
		}
		
		System.out.println(retVal.toString());
		return retVal;
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON}) 
	public String registerUser(@QueryParam("username") String username, @QueryParam("password_md5") String password_md5) {
		 
		try {
			connection.insert("BEGIN CREATE_NEW_USER(" + username + ", Dorf von" + username + ", " + password_md5 + "); END;"); 
		}
		catch(Exception ex) {
			return "Internal System Error: " + ex.getMessage();
		}
		
		return "New User created";
	}
}
