package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.BuildEvent;
import pkgModel.BuildEventList;
import pkgModel.EventList;
import pkgModel.Gebaeude;
import pkgModel.ResponseObject;
import pkgSessionHandling.SessionDB;

@Path("/CurrentBuildEvents")
public class CurrentBuildEvents {

	static DatabaseConnection connection= null;

	public CurrentBuildEvents(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject currentBuildEvent(@QueryParam("dorfId") int dorfId, @QueryParam("sessionid") int sessionId) {
		
		ResponseObject retVal = new ResponseObject();
		retVal.prepareRO();
		ResultSet rs = null;
		BuildEventList bel = new BuildEventList();
		SessionDB sdb = SessionDB.newInstance();
		
		
		try {
			if(!sdb.checkSession(sessionId)){
				throw new Exception("no such active session");
			}
			
			rs = connection.getOwnerFromDorfById(dorfId);
			if(!rs.next()) {
				throw new Exception("dorf doesn't exist");
			}
			
			if(!rs.getString(1).trim().equals(sdb.getSession_User(sessionId).getUname().trim())) {
				throw new Exception ("you have no permission on this village");
			}
			
			
			rs = connection.getCurrentBuildEvents(dorfId);
		
			
			
			while(rs.next()) {
				bel.getBuildEvents().add(new BuildEvent(rs.getInt(1), rs.getString(4), new Gebaeude(rs.getString(3), rs.getInt(5))));
			}
			
			retVal.setData(bel);
			retVal.setOk(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			retVal.setErrormsg(e.getMessage());
			retVal.setOk(false);
		}
		
		return retVal;
	}
}