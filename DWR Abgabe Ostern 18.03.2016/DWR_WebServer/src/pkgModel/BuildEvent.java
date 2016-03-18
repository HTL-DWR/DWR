package pkgModel;

import java.sql.Timestamp;

public class BuildEvent {
	private String dorfName;
	private Gebaeude gebauede;
	private int duration;
	
	public String getDorfName() {
		return dorfName;
	}
	public void setDorfName(String dorfName) {
		this.dorfName = dorfName;
	}
	public Gebaeude getGebauede() {
		return gebauede;
	}
	public void setGebauede(Gebaeude gebauede) {
		this.gebauede = gebauede;
	}
	@Override
	public String toString() {
		return "BuildEvent [dorfName=" + dorfName + ", gebauede=" + gebauede
				+ "]";
	}
	
	
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public BuildEvent(int duration, String dorfName, Gebaeude gebauede) {
		
		this.duration = duration;
		this.dorfName = dorfName;
		this.gebauede = gebauede;
	}
	public BuildEvent() {
		super();
	}
}