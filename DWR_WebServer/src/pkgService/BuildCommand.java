package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;


@Path("/BuildCommand")
public class BuildCommand {
	
	static DatabaseConnection connection= null;
	
	
	public BuildCommand(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public String getIsBeingBuild(@QueryParam("dorfid") int did, @QueryParam("GebTyp") String gebtyp) {
		String retVal = null;
		ResultSet rs = null;
		
		try {
			connection.getDataWithExceptions("BEGIN BUILD_COMMAND('"+did+"','"+gebtyp+"'); END;");
			retVal = "Build successful!";
		}
		catch(Exception ex) {
			retVal = "Build failed! " + ex.getMessage();
		}
		return retVal;
	}
	
}
