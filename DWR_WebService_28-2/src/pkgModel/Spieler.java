package pkgModel;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Spieler {
	private String username;
	private Vector<Integer> doerfer = new Vector<Integer>();
	
	public Spieler() {
		super();
		username = "no name: error";
	}
	
	public Spieler(String username) {
		super();
		this.username = username;
	}
	
	public Spieler(String username, Vector<Integer> doerfer) {
		super();
		this.username = username;
		this.doerfer = doerfer;
	}

	public Vector<Integer> getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(Vector<Integer> doerfer) {
		this.doerfer = doerfer;
	}

	@Override
	public String toString() {
		return "Spieler [username=" + username + ", doerfer=" + doerfer + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
