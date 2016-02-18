package pkgSessionHandling;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class SessionDB implements Observer {
	private static SessionDB instance=null; 
	public static SessionDB newInstance()
	{
		if(instance==null)
			instance=new SessionDB();
		return instance;
	}
	private SessionDB()
	{
		super();
	}
	private HashMap<Integer,Session> actives = new HashMap<Integer,Session>();
	
	public int newSession(String uname,String passwd)
	{
		int retVal=-1;
		try
		{
			Session s = new Session(uname,passwd);
			
			s.notifyOnTimeout(this);
			actives.put(s.hashCode(), s);
			retVal = s.hashCode();
		}
		catch(Exception e)
		{
			System.out.println("session creation failed: "+e.getMessage());
			retVal=-1;
		}
		return retVal;
	}
	
	@Override
	public String toString() {
		return "active sessions: [" + actives + "]";
	}

	public boolean checkSession(int sessionId)
	{
		return actives.get(sessionId) != null;
	}	
	
	public Session getSession_User(int sessionId)
	{
		return actives.get(sessionId);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		Session s = (Session) arg;
		System.out.println("Session "+ s.toString() + " timed out.");
		actives.remove(s.hashCode());
		System.out.println(this.toString());
		
	}
}
