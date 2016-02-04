package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.ResponseObject;
import pkgSessionHandling.Session;
import pkgSessionHandling.SessionDB;



@Path("/LoginSession")
public class LoginSession {
	
	SessionDB db = null;
	
	public LoginSession(){
		super();
		db=SessionDB.newInstance();
		Session.session_lifetime = 60 * 60 * 1000; // in Millis
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public ResponseObject getSession(@QueryParam("uname") String uname, @QueryParam("passhash") String passhash) {
		int sessionID = 0;
		ResponseObject retVal = new ResponseObject(); 
		
		retVal.prepareRO();
		
		System.out.println("--------------");
		
		sessionID = db.newSession(uname, passhash);
		
		System.out.println("--------------");
		
		retVal.setOk(true);
		retVal.setData(Integer.toString(sessionID));
		return retVal;
	}
	
}
