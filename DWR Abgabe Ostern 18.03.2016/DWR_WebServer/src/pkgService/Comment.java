package pkgService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.ResponseObject;
import pkgSessionHandling.SessionDB;

@Path("/Comment")
public class Comment {
	
	static DatabaseConnection connection= null;
	
	public Comment(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject postComment(@QueryParam("boardId") int boardId, @QueryParam("text") String text, @QueryParam("opName") String opName, @QueryParam("sessionid") int sessionid) {
		
		ResponseObject ro = new ResponseObject();
		SessionDB sdb = SessionDB.newInstance();
		
		ro.prepareRO();
		
		try {
			if(!sdb.checkSession(sessionid)){
				throw new Exception("no such active session");
			}
			
			connection.postComment(boardId, text, opName);
			ro.setOk(true);
			ro.setData("Comment created!");
		}
		catch(Exception ex) {
			ro.setErrormsg("Internal System Error: " + ex.getMessage());
			ro.setOk(false);
		}

		return ro;
				
	}
	
}
