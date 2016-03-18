package pkgService;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.EventList;
import pkgModel.MapDorfCollection;
import pkgModel.NamespaceDWR;
import pkgModel.RekrutEvent;
import pkgModel.RekrutEventList;
import pkgModel.ResponseObject;
import pkgModel.Truppen;
import pkgSessionHandling.SessionDB;

@Path("/CurrentRekrutEvents")
public class CurrentRekrutEvents {
	static DatabaseConnection connection= null;

	public CurrentRekrutEvents(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject currentRekrutEvents(@QueryParam("dorfId") int dorfId, @QueryParam("sessionid") int sessionId) {
				
		ResponseObject retVal = new ResponseObject();
		retVal.prepareRO();
		ResultSet rs = null;
		RekrutEventList rel = new RekrutEventList();
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
			
			rs = connection.getCurrentRekrutEvents(dorfId);
		
			while(rs.next()) {
				rel.getRekrutEvents().add(new RekrutEvent(rs.getInt(1), new Truppen(rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6))));
			}
			
			retVal.setData(rel);
			retVal.setOk(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			retVal.setErrormsg(e.getMessage());
			retVal.setOk(false);
		}
		
		return retVal;
	}
}