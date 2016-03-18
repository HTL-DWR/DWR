package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.BoardPreview;
import pkgModel.BoardPreviewList;
import pkgModel.ResponseObject;

@Path("/BoardList")
public class BoardList {
	
	static DatabaseConnection connection= null;
	
	public BoardList(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseObject getBoardList() {
		ResponseObject retVal = new ResponseObject();
		retVal.prepareRO();
		ResultSet rs = null;
		BoardPreviewList bpl = new BoardPreviewList();
		
		try{
			rs = connection.getBoardPreviewList();
			
			while(rs.next()) {
				bpl.getBoardPreviews().add(new BoardPreview(rs.getInt(1), rs.getString(2)));
			}
			
			retVal.setData(bpl);
			retVal.setOk(true);
		}
		catch(Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
			retVal.setOk(false);
		}
		
		return retVal;
	}
	
	
}
