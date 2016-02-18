package pkgSessionHandling;

public class SessionClock extends Clock {

	private Session s;
	public SessionClock(Session owner,long lifetime) {
		super(lifetime);
		this.s=owner;
	}
	
	@Override
	public void run()
	{
		System.out.println("...clock running ");
		while(stillAlive())
		{
			try {
				this.sleep(tick);
			} catch (InterruptedException e) {
				System.out.println("tick died");
			}
		}
		System.out.println("...clock finalized ");
		obs.change();
		obs.notifyObservers(s);
	}

}
