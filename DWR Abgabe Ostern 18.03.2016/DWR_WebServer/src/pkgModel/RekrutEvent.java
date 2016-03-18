package pkgModel;

import java.sql.Timestamp;

public class RekrutEvent {
	private Truppen truppen;
	private int duration;
	
	
	
	public RekrutEvent() {
		super();
	}

	public RekrutEvent(int duration, Truppen truppen) {
		super();
		this.truppen = truppen;
		this.duration = duration;
	}

	public Truppen getTruppen() {
		return truppen;
	}

	public void setTruppen(Truppen truppen) {
		this.truppen = truppen;
	}

	@Override
	public String toString() {
		return "RekrutEvent [truppen=" + truppen + "]";
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
