package pkgModel;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EventList")
public class EventList {
	private Vector<Event> events = new Vector<Event>();

	@Override
	public String toString() {
		return "EventList [events=" + events + "]";
	}

	public Vector<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}

	public EventList(Vector<Event> events) {
		super();
		this.events = events;
	}

	public EventList() {
		super();
	}

}