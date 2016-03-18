package pkgModel;

import java.util.ArrayList;
import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement (name = "DWR")
public class Spieler {
	private String username;
	private ArrayList<Integer> doerfer = new ArrayList<Integer>();
	
	public Spieler() {
		super();
		username = "no name: error";
	}
	
	public Spieler(String username) {
		super();
		this.username = username;
	}
	
	public Spieler(String username, ArrayList<Integer> doerfer) {
		super();
		this.username = username;
		this.doerfer = doerfer;
	}

	public ArrayList<Integer> getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(ArrayList<Integer> doerfer) {
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
