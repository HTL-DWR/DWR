package Engine;
import java.util.Observable;
import java.util.Observer;


public class Clock extends Thread{
	private long begin;
	private long lifetime; //Session Lifetime = 3h
	protected MyObservable obs = new MyObservable();
	protected static long tick = 100; // steps are 100 millis
	
	
	public Clock(long lifetime) {
		super();
		reset(lifetime);
	}

	@Override
	public void run()
	{
		while(stillAlive())
		{
			//System.out.println("c");
			try {
				this.sleep(tick);
			} catch (InterruptedException e) {
				System.out.println("tick died");
			}
		}
		System.out.println("clock ended");
		obs.change();
		obs.notifyObservers(this);
	
	}
	
	public void newObserver(Observer newObserver)
	{
		System.out.println("newObserver in clock");
		obs.addObserver(newObserver);
		
	}
	
	protected boolean stillAlive()
	{
		return System.currentTimeMillis() - begin < lifetime;
	}
	
	public void reset()
	{
		this.begin = System.currentTimeMillis();
	}
	
	
	public void reset(long lifetime)
	{
		this.lifetime = lifetime;
		reset();
	}
	
	public void reset(int weeks, int days,int hours, int minutes, int seconds, int millis)
	{
		reset(  +weeks * 7 * 24 * 60 * 60 * 1000
				+days * 24 * 60 * 60 * 1000
				+hours * 60 * 60 * 1000
				+minutes * 60*1000
				+seconds*1000
				+millis);
	}
	
	
	
	
}
