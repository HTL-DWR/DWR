package pkgModel;

import java.util.Vector;

public class RekrutEventList {
	private Vector<RekrutEvent> rekrutEvents = new Vector<RekrutEvent>();

	public RekrutEventList() {
		super();
	}

	public RekrutEventList(Vector<RekrutEvent> rekrutEvents) {
		super();
		this.rekrutEvents = rekrutEvents;
	}

	public Vector<RekrutEvent> getRekrutEvents() {
		return rekrutEvents;
	}

	public void setRekrutEvents(Vector<RekrutEvent> rekrutEvents) {
		this.rekrutEvents = rekrutEvents;
	}
	
	
}
