package pkgService;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pkgDatabase.DatabaseConnection;
import pkgModel.*;
import pkgSessionHandling.SessionDB;


@Path("/DorfDetailFull")
public class DorfDetailFull {
	static DatabaseConnection connection= null;
	
	public DorfDetailFull(){
		super();
		connection =new DatabaseConnection("d5bhifs11", "d5bhifs11");
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) 
	public ResponseObject getDorfDetailFull(@QueryParam("id") int dorfId, @QueryParam("sessionid") int sessionid) {
		ResponseObject retVal = null;
		ResultSet rs = null;
		SessionDB sdb = SessionDB.newInstance();
		
		retVal.prepareRO();
		
		try {
			
			if(!sdb.checkSession(sessionid)){
				throw new Exception("no such active session");
			}
			
			//Name & id
			rs = connection.getData("select id, name, owner from dorf where id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no dorfname found");
			}
			
			Dorf d = new Dorf();
			d.setId(rs.getInt(1));
			d.setName(rs.getString(2));
			//retVal.setData(d);
			
			if(!rs.getString(3).trim().equals(sdb.getSession_User(sessionid).getUname().trim())){
				throw new Exception("You have no permission to view this village");
			}
			//Geb�ude
			rs = connection.getData("select gt.name, b.lvl from dorf d " +
					"inner join bau b on b.did=d.id inner join geb_typ gt on b.tid = gt.id where d.id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no gebaeude found");
			}
			
			do {
				d.getGebaude().add(new Gebaeude(rs.getString(1), rs.getInt(2)));
			} while(rs.next());
			
			//Rohstoffe
			rs = connection.getData("select r.holz, r.stein, r.lehm from resgruppe r " +
					"inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.id = " + dorfId);
			
			if(!rs.next()) {
				throw new Exception("no rohstoffe found");
			}
			
			d.setRohstoffe(new Rohstoffe(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			
			//eigene Truppen
			rs = connection.getData("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did where t.owner = d.owner and d.id = " + dorfId); 
			
			if(!rs.next()) {
				throw new Exception("no truppen found");
			}
			
			d.setTruppen(new Truppen(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));
				
			//Unterst�tzungen
			rs = connection.getData("select t.owner, t.schwert, t.reiter, t.bogen, t.lanze from truppe t " +
					"inner join movable m on m.id = t.id  " +
					"inner join dorf d on d.id = m.did " +
					"where not t.owner = d.owner and d.id= " + dorfId);
			
			while(rs.next()) {
				d.getUnterstuetzungen().add(new Unterstuetzung(rs.getString(1), new Truppen(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5))));
			}
			
			retVal.setData(d);
			retVal.setOk(true);
		}
		catch(Exception ex) {
			retVal.setErrormsg(ex.getMessage());
			retVal.setData(null);
		}
	
		return retVal;
	}
}
