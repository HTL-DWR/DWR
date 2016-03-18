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
import pkgModel.ResponseObject;

@Path("/Credentials")
public class Credentials {

	static DatabaseConnection connection= null;

	public Credentials(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject check(@QueryParam("username") String username, @QueryParam("password_md5") String password_md5) {
		ResponseObject ret = new ResponseObject();
		ret.prepareRO();
		LoginStatus retVal = null;
		ResultSet rs = null;


		try {
			rs = connection.getCredentials(username, password_md5);

			if(rs.next()) {

				System.out.print(rs.getString(2));

				if(rs.getString(2).equals("false")) {
					retVal = new LoginStatus("Password Falsch", username, "no id");
					ret.setData(retVal);
					ret.setOk(false);
					ret.setErrormsg("Passwort Falsch");
				}
				else if(rs.getString(2).equals("true")){
					retVal = new LoginStatus ("Login Erfolgreich", username, "5555");
					ret.setData(retVal);
					ret.setOk(true);
				}
			}
			else {
				retVal = new LoginStatus("Username Falsch", username, "no id");
				ret.setData(retVal);
				ret.setOk(false);
				ret.setErrormsg("Username Falsch");
			}
			
			
			

		} catch (SQLException e) {
			ret.setOk(false);
			ret.setErrormsg("Internal System Error: Database unreachable"+e.getMessage());
			//e.printStackTrace();
			//return new LoginStatus("Internal System Error: Database unreachable", " ", "no id" );
		}

		//System.out.println(retVal.toString());
		return ret;
	}

	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public ResponseObject registerUser(@QueryParam("username") String username, @QueryParam("password_md5") String password_md5) {

		ResponseObject ro = new ResponseObject();
		ro.prepareRO();
		
		try {
			System.out.println("---in register");
			
			connection.registerUser(username, password_md5);
			
			ro.setOk(true);
			System.out.println("---after set ok: " + ro.isOk());
			
			
			ro.setData("New User created");
			
			System.out.println("---after new user");
		}
		catch(Exception ex) {
			ro.setErrormsg("Internal System Error: " + ex.getMessage());
			System.out.println("---in register error: " + ex.getMessage());
			ro.setOk(false);
		}

		System.out.println("---before return");
		return ro;
	}
}