package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Gebaeude {
	private String name;
	private int lvl;
	
	public Gebaeude() {
		super();
		this.name = "no name: error";
		this.lvl = -1;
	}
	
	public Gebaeude(String name, int lvl) {
		super();
		this.name = name;
		this.lvl = lvl;
	}

	@Override
	public String toString() {
		return "Gebaeude [name=" + name + ", lvl=" + lvl + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
}
