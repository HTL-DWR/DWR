package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
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
	public Spieler getSpielerDetail(@QueryParam("username") String username) {
		
		Spieler retVal = null;
		ResultSet rs = null;
		
		try {
			rs = connection.getData("select s.uname, d.id  from spieler s inner join dorf d on d.owner = s.uname where s.uname = '" + username + "'");
			
			if(rs.next()) {
				retVal = new Spieler(rs.getString(1));
				retVal.getDoerfer().add(rs.getInt(2));
				
				while(rs.next()) {
					retVal.getDoerfer().add(rs.getInt(2));
				}
			}
			else {
				retVal = new Spieler();	
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return new Spieler();
		}
		
		return retVal;
	}
}
