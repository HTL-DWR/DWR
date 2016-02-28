package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DorfMap")
public class DorfMap {
	private int id;
	private String name;
	private String owner;
	private String clan;
	private int x;
	private int y;
	private Truppen truppen;
	
	public DorfMap () {
		super();
		this.id = -1;
		this.name = "noname";
		this.owner = "noname";
		this.clan = "";
		this.x = -1;
		this.y = -1;
		this.truppen = new Truppen();
	}
	
	public DorfMap(int id, String name, String owner, String clan, int x, int y,
			Truppen truppen) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.clan = clan;
		this.x = x;
		this.y = y;
		this.truppen = truppen;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getClan() {
		return clan;
	}
	public void setClan(String clan) {
		this.clan = clan;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Truppen getTruppen() {
		return truppen;
	}
	public void setTruppen(Truppen truppen) {
		this.truppen = truppen;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DorfMap [id=" + id  + ", name=" + name + ", owner=" + owner + ", clan=" + clan
				+ ", x=" + x + ", y=" + y + ", truppen=" + truppen + "]";
	}
}
