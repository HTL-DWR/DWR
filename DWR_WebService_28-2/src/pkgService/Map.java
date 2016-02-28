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

@Path("/Map")
public class Map {
	static DatabaseConnection connection= null;
	
	public Map(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})//@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML}) 
	public NamespaceDWR getMap() {
		NamespaceDWR retVal = new NamespaceDWR(new MapDorfCollection());
		ResultSet rs = null;
		
		try {
			//Name, X & Y (Gerundet)
			rs = connection.getData("select to_char(d.id), round(t.x, 20) as X , round(t.y,20) as Y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t");
	
			while(rs.next()) {
				DorfMap dm = new DorfMap();
				dm.setId(rs.getInt(1));
				dm.setX(Math.abs(rs.getInt(2)));
				dm.setY(Math.abs(rs.getInt(3)));
				
				dm.setClan(null);
				dm.setName(null);
				dm.setOwner(null);
				dm.setTruppen(null);
				
				retVal.getMdc().getDoerfer().add(dm);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			retVal.getMdc().getDoerfer().add(new DorfMap());
			return retVal;
		}
		
		return retVal;
	}
}
