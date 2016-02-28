package pkgModel;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DWR")
public class Dorf {
	
	private int id;
	private String name;
	
	private Vector<Gebaeude> gebaude = new Vector<Gebaeude>();
	private Rohstoffe rohstoffe;
	
	private Truppen truppen;
	private Vector<Unterstuetzung> unterstuetzungen = new Vector<Unterstuetzung>();
	
	public Dorf() {
		super();
		this.id = -1;
		this.name = "no name: error";
		this.rohstoffe = new Rohstoffe();
		this.truppen = new Truppen();		
	}
	
	public Dorf(int id, String name, Vector<Gebaeude> gebaude, Rohstoffe rohstoffe, Truppen truppen, Vector<Unterstuetzung> unterstuetzungen) {
		super();
		this.id = id;
		this.name = name;
		this.gebaude = gebaude;
		this.rohstoffe = rohstoffe;
		this.truppen = truppen;
		this.unterstuetzungen = unterstuetzungen;
	}

	@Override
	public String toString() {
		return "Dorf [id=" + id + ", name=" + name + ", gebaude=" + gebaude
				+ ", rohstoffe=" + rohstoffe + ", truppen=" + truppen
				+ ", unterstuetzungen=" + unterstuetzungen + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Gebaeude> getGebaude() {
		return gebaude;
	}

	public void setGebaude(Vector<Gebaeude> gebaude) {
		this.gebaude = gebaude;
	}

	public Rohstoffe getRohstoffe() {
		return rohstoffe;
	}

	public void setRohstoffe(Rohstoffe rohstoffe) {
		this.rohstoffe = rohstoffe;
	}

	public Truppen getTruppen() {
		return truppen;
	}

	public void setTruppen(Truppen truppen) {
		this.truppen = truppen;
	}

	public Vector<Unterstuetzung> getUnterstuetzungen() {
		return unterstuetzungen;
	}

	public void setUnterstuetzungen(Vector<Unterstuetzung> unterstuetzungen) {
		this.unterstuetzungen = unterstuetzungen;
	}
}
