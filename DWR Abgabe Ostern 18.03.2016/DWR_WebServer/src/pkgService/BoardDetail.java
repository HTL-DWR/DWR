package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.Board;
import pkgModel.Kommentar;
import pkgModel.ResponseObject;
import pkgSessionHandling.SessionDB;

@Path("/BoardDetail")
public class BoardDetail {

	static DatabaseConnection connection= null;

	public BoardDetail(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject getBoardDetail(@QueryParam("id") int id) {
		ResponseObject retVal = new ResponseObject();
		retVal.prepareRO();
		ResultSet rs = null;
		Board board = new Board();

		try {
			rs = connection.getBoardInforamtion(id);

			while(rs.next()) {
				board.setId(rs.getInt(1));
				board.setName(rs.getString(2));
				board.setOp(rs.getString(3));
				board.setText(rs.getString(4));
				board.setPostTime(rs.getTimestamp(5).toString());
			}

			rs = connection.getBoardComments(id);

			while (rs.next()) {
				board.getKommentar().add(new Kommentar(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).toString()));
			}

			retVal.setData(board);
			retVal.setOk(true);
		}
		catch(Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
			retVal.setOk(false);
		}

		return retVal;
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject addBoardDetail(@QueryParam("name") String name, @QueryParam("text") String text, @QueryParam("opName") String op, @QueryParam("sessionid") int sessionid) {
		
		ResponseObject ro = new ResponseObject();
		SessionDB sdb = SessionDB.newInstance();
		
		ro.prepareRO();
		
		System.out.println("--------" + name);
		
		try {
			if(!sdb.checkSession(sessionid)){
				throw new Exception("no such active session");
			}
			connection.newBorad(name, text, op);
			ro.setOk(true);
			ro.setData("Board created!");
		}
		catch(Exception ex) {
			ro.setErrormsg("Internal System Error: " + ex.getMessage());
			ro.setOk(false);
		}

		return ro;
		
	}  
	
}