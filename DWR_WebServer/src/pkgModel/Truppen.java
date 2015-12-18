package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Truppen {
	private int schwert;
	private int reiter;
	private int bogen;
	private int lanze;
	
	public Truppen() {
		super();
		this.schwert = 0;
		this.reiter = 0;
		this.bogen = 0;
		this.lanze = 0;
	}
	
	public Truppen(int schwert, int reiter, int bogen, int lanze) {
		super();
		this.schwert = schwert;
		this.reiter = reiter;
		this.bogen = bogen;
		this.lanze = lanze;
	}
	
	@Override
	public String toString() {
		return "Truppe [schwert=" + schwert + ", reiter=" + reiter + ", bogen="
				+ bogen + ", lanze=" + lanze + "]";
	}
	public int getSchwert() {
		return schwert;
	}
	public void setSchwert(int schwert) {
		this.schwert = schwert;
	}
	public int getReiter() {
		return reiter;
	}
	public void setReiter(int reiter) {
		this.reiter = reiter;
	}
	public int getBogen() {
		return bogen;
	}
	public void setBogen(int bogen) {
		this.bogen = bogen;
	}
	public int getLanze() {
		return lanze;
	}
	public void setLanze(int lanze) {
		this.lanze = lanze;
	}
}
