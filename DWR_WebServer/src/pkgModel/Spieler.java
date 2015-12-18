package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Spieler {
	private String username;
	private DoerferIDs doerfer = new DoerferIDs();
	
	public Spieler() {
		super();
		username = "no name: error";
	}
	
	public Spieler(String username) {
		super();
		this.username = username;
	}
	
	public Spieler(String username, DoerferIDs doerfer) {
		super();
		this.username = username;
		this.doerfer = doerfer;
	}

	public DoerferIDs getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(DoerferIDs doerfer) {
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
