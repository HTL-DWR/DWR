package pkgModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Event {
	private String startTime;
	private int duration;
	
	public String getStartTime() {
		return this.startTime;
	}
	public void setStartTime(String startTime) {
			this.startTime = startTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "Event [startTime=" + startTime + ", duration=" + duration + "]";
	}
	public Event(String startTime, int duration) {
		super();
		this.startTime = startTime;
		this.duration = duration;
	}
	public Event() {
		super();
	}
}