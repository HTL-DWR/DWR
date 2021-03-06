package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.DorfMap;
import pkgModel.ResponseObject;
import pkgModel.Truppen;

@Path("/DorfDetailMap")
public class DorfDetailMap {
	static DatabaseConnection connection= null;
	
	public DorfDetailMap(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public ResponseObject getDorfDetailMap(@QueryParam("id") int dorfId) {
		ResponseObject retVal = new ResponseObject();
		ResultSet rs = null;
		
		retVal.prepareRO();
		
		try {
			//Id, Name & Owner
			rs = connection.getDorf(dorfId);
			
			if(!rs.next()) {
				throw new Exception("no dorfname found");
			}
			
			DorfMap dm = new DorfMap();
			dm.setId(rs.getInt(1));
			dm.setName(rs.getString(2));
			dm.setOwner(rs.getString(3));
			
			//(eigene)Truppen
			//TODO: Unterst�tzung zu den eigenen addieren!!!!
			rs = connection.getAllUnitsInDorf(dorfId);
			
			if(!rs.next()) {
				throw new Exception("no truppen found");
			}
			
			dm.setTruppen(new Truppen(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));			
			retVal.setData(dm);
			retVal.setOk(true);
		}
		catch (Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
		} 
	
		return retVal;
	}
}
