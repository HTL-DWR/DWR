package pkgModel;

import java.util.Vector;

public class BuildEventList {
	private Vector<BuildEvent> buildEvents = new Vector<BuildEvent>();

	public BuildEventList(Vector<BuildEvent> buildEvents) {
		super();
		this.buildEvents = buildEvents;
	}

	public BuildEventList() {
		super();
	}

	public Vector<BuildEvent> getBuildEvents() {
		return buildEvents;
	}

	public void setBuildEvents(Vector<BuildEvent> buildEvents) {
		this.buildEvents = buildEvents;
	}
}
