package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Unterstuetzung {
	private String unterstuetzer_name;
	private Truppen truppen;
	
	public Unterstuetzung() {
		super();
		this.unterstuetzer_name = "no name: error";
		this.truppen = new Truppen();
	}
	
	public Unterstuetzung(String unterstuetzer_name, Truppen truppen) {
		super();
		this.unterstuetzer_name = unterstuetzer_name;
		this.truppen = truppen;
	}
	
	@Override
	public String toString() {
		return "Unterstuetzung [unterstuetzer_name=" + unterstuetzer_name
				+ ", truppen=" + truppen + "]";
	}
	
	public String getUnterstuetzer_name() {
		return unterstuetzer_name;
	}
	
	public void setUnterstuetzer_name(String unterstuetzer_name) {
		this.unterstuetzer_name = unterstuetzer_name;
	}
	
	public Truppen getTruppen() {
		return truppen;
	}
	
	public void setTruppen(Truppen truppen) {
		this.truppen = truppen;
	}	
}
