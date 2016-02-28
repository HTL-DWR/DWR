package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.DorfMap;
import pkgModel.Truppen;

@Path("/DorfDetailMap")
public class DorfDetailMap {
	static DatabaseConnection connection= null;
	
	public DorfDetailMap(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON}) 
	public DorfMap getDorfDetailMap(@QueryParam("id") int dorfId) {
		DorfMap retVal = null;
		ResultSet rs = null;
		
		try {
			//Id, Name & Owner
			rs = connection.getData("select id, name, owner from dorf where id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no dorfname found");
			}
			
			retVal = new DorfMap();
			retVal.setId(rs.getInt(1));
			retVal.setName(rs.getString(2));
			retVal.setOwner(rs.getString(3));
			
			//(eigene)Truppen
			//TODO: Unterstützung zu den eigenen addieren!!!!
			rs = connection.getData("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where d.id = " + dorfId); 
			
			if(!rs.next()) {
				throw new Exception("no truppen found");
			}
			
			retVal.setTruppen(new Truppen(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return new DorfMap();
		} 
	
		return retVal;
	}
}
