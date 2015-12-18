package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.*;


@Path("/DorfDetailFull")
public class DorfDetailFull {
	static DatabaseConnection connection= null;
	
	public DorfDetailFull(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public Dorf getDorfDetailFull(@QueryParam("id") int dorfId) {
		Dorf retVal = null;
		ResultSet rs = null;
		
		try {
			//Name & id
			rs = connection.getData("select id, name from dorf where id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no dorfname found");
			}
			
			retVal = new Dorf();
			retVal.setId(rs.getInt(1));
			retVal.setName(rs.getString(2));
			
			//Gebäude
			rs = connection.getData("select gt.name, b.lvl from dorf d " +
					"inner join bau b on b.did=d.id inner join geb_typ gt on b.tid = gt.id where d.id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no gebaeude found");
			}
			
			do {
				retVal.getGebaude().add(new Gebaeude(rs.getString(1), rs.getInt(2)));
			} while(rs.next());
			
			//Rohstoffe
			rs = connection.getData("select r.holz, r.stein, r.lehm from resgruppe r " +
					"inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no rohstoffe found");
			}
			
			retVal.setRohstoffe(new Rohstoffe(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			
			//eigene Truppen
			rs = connection.getData("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where t.owner = d.owner and d.id = " + dorfId); 
			
			if(!rs.next()) {
				throw new Exception("no truppen found");
			}
			
			retVal.setTruppen(new Truppen(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));
				
			//Unterstützungen
			rs = connection.getData("select t.owner, t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did " +
					"where not t.owner = d.owner and d.id= " + dorfId);
			
			while(rs.next()) {
				retVal.getUnterstuetzungen().add(new Unterstuetzung(rs.getString(1), new Truppen(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5))));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return new Dorf();
		}
		
		return retVal;
	}
}
