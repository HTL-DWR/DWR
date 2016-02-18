package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.ResponseObject;
import pkgModel.Spieler;

@Path("/SpielerDetail")
public class SpielerDetail {

	static DatabaseConnection connection= null;
	
	public SpielerDetail(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public ResponseObject getSpielerDetail(@QueryParam("username") String username) {
		
		ResponseObject retVal = null;
		ResultSet rs = null;
		
		retVal.prepareRO();
		Spieler s = null;
		
		try {
			rs = connection.getSpielerDetail(username);
			
			if(rs.next()) {
				s = new Spieler(rs.getString(1));
				s.getDoerfer().add(rs.getInt(2));
				
				while(rs.next()) {
					s.getDoerfer().add(rs.getInt(2));
				}
			}
			else {
				throw new Exception("Player does not exist");	
			}
			retVal.setData(s);
			retVal.setOk(true);
		}
		catch(Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
		}
		
		return retVal;
	}
}
