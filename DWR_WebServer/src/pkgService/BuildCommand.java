package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.ResponseObject;
import pkgSessionHandling.SessionDB;


@Path("/BuildCommand")
public class BuildCommand {
	
	static DatabaseConnection connection= null;
	
	
	public BuildCommand(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public ResponseObject getIsBeingBuild(@QueryParam("dorfid") int did, @QueryParam("GebTyp") String gebtyp, @QueryParam("sessionid") int sessionid) {
		ResponseObject retVal = new ResponseObject();
		ResultSet rs = null;
		SessionDB sdb = SessionDB.newInstance();
		
		retVal.prepareRO();
		
		try {
			if(!sdb.checkSession(sessionid)){
				throw new Exception("no such active session");
			}
			System.out.println("++++++++"+did);
			rs= connection.getData("select owner from dorf where id="+did);
			if(!rs.next()){
				throw new Exception ("no such village found");
			}
			if(!rs.getString(1).trim().equals(sdb.getSession_User(sessionid).getUname().trim()));
			
			
			
			connection.getDataWithExceptions("BEGIN BUILD_COMMAND('"+did+"','"+gebtyp+"'); END;");
			retVal.setOk(true);
		}
		catch(Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
		}
		return retVal;
	}
	
}
