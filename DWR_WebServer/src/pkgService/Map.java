package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.DorfMap;
import pkgModel.MapDorfCollection;
import pkgModel.NamespaceDWR;
import pkgModel.ResponseObject;

@Path("/Map")
public class Map {
	static DatabaseConnection connection= null;
	
	public Map(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})//@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML}) 
	public ResponseObject getMap() {
		ResponseObject retVal = new ResponseObject();
		retVal.prepareRO();
		NamespaceDWR ndwr = new NamespaceDWR();
		ndwr.setMdc(new MapDorfCollection());
		ResultSet rs = null;
		
		try {
			//Name, X & Y (Gerundet)
			rs = connection.getMap();
	
			while(rs.next()) {
				DorfMap dm = new DorfMap();
				dm.setId(rs.getInt(1));
				dm.setX(Math.abs(rs.getInt(2)));
				dm.setY(Math.abs(rs.getInt(3)));
				
				dm.setClan(null);
				dm.setName(null);
				dm.setOwner(null);
				dm.setTruppen(null);
				
				ndwr.getMdc().getDoerfer().add(dm);
			}
			retVal.setData(ndwr);
			retVal.setOk(true);
		}
		catch (Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
		}
		
		return retVal;
	}
}
