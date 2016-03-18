package pkgSessionHandling;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Observer;

import pkgDatabase.DatabaseConnection;


public class Session {
	private String uname;
	private SessionClock  clock =  null;
	public static long session_lifetime; //in Millis
	private static long session_id_pot = 0;
	private long session_id;
	private static DatabaseConnection connection= null;
	
	private Date startTime = new Date();
	
	public String getUname() {
		return uname;
	}
	public long getSession_id() {
		return session_id;
	}
	
	public Session(String uname, String passwd_hc) throws Exception {
		super();
		connection = new DatabaseConnection("d5bhifs11", "d5bhifs11");
		ResultSet rs = connection.checkCredentials(uname, passwd_hc);
		rs.next();
		String returnedString=rs.getString(1);
		System.out.println("-----------------------------------"+returnedString);
		//select DECODE('true',... select von zeiringer
		
		if(!returnedString.equals("true"))
		{
			throw new Exception("no match for this uname-passwd combination");
		}
		
		this.uname = uname;
		clock = new SessionClock(this,session_lifetime);
		session_id = session_id_pot++;
	}

	@Override
	public String toString() {
		return "Session '" + session_id + "' of " + uname;
	}

	public void notifyOnTimeout(Observer ob)
	{
		System.out.println("will notify on thimeout");
		clock.newObserver(ob);
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (session_id ^ (session_id >>> 32));
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (session_id != other.session_id)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}
}
