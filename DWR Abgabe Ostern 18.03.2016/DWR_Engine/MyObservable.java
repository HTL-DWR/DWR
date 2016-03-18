package Engine;
import java.util.Observable;


public class MyObservable extends Observable {
	public void change()
	{
		this.setChanged();
	}
}
