package Engine;

import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;


public class Engine extends Thread implements Observer{

	private boolean stop=false;
	private Database db = new Database("d5bhifs11", "d5bhifs11");
	Clock resourceUpdateTimer = new Clock(0);
	@Override
	public void run()
	{
		
		updateResources(db);
		resolveEndetEvents();
		resetURT();
		while (!stop)
		{
			
			try {
				this.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg == resourceUpdateTimer)
		{
			System.out.println("update");
			updateResources(db);
			resetURT();
		}

	}
	
	private void resetURT()
	{
		
		resourceUpdateTimer = new Clock(0);
		resourceUpdateTimer.newObserver(this);
		resourceUpdateTimer.reset(0, 0, 0, 0, 3, 0);
		System.out.println("clock started");
		resourceUpdateTimer.start();
	}
	
	private void updateResources(Database db) {
		
		
		
		System.out.println("updateResources");
		SendQueryInsert("BEGIN update_resources(); END;");
		
		/*
		 * funktion mit rueckgabewert
		 * SendQueryInsert("declare result number; BEGIN result := Create_Dorf('"+dorfName+"','"+ownerDorf+"','"+posDorfX+"','"+posDorfY+"'); END;");
		 * 
		 * 
		 * */
	}
	
	private void resolveEndetEvents() {
		//System.out.println("res");
		//this.db;
		
		//select resolved events
	
		//call resolve-prcedure of all gotten events
	
		
		
		
		//SendQueryInsert("BEGIN update_resources(); END;");
		
		
	}


	private void SendQueryInsert(String string) {
		try {
			db.insert(string);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private ResultSet SendQuerySelect(String string) {
		ResultSet rs = null;
		try {
			rs = db.getData(string);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rs;	
	}
	
}
