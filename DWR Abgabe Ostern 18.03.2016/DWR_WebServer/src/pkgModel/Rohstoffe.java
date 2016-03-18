package pkgModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class Rohstoffe {
	private int holz;
	private int stein;
	private int lehm;
	
	public Rohstoffe() {
		super();
		this.holz = 0;
		this.stein = 0;
		this.lehm = 0;
	}
	
	public Rohstoffe(int holz, int stein, int lehm) {
		super();
		this.holz = holz;
		this.stein = stein;
		this.lehm = lehm;
	}
	@Override
	public String toString() {
		return "Rohstoffe [holz=" + holz + ", stein=" + stein + ", lehm="
				+ lehm + "]";
	}
	public int getHolz() {
		return holz;
	}
	public void setHolz(int holz) {
		this.holz = holz;
	}
	public int getStein() {
		return stein;
	}
	public void setStein(int stein) {
		this.stein = stein;
	}
	public int getLehm() {
		return lehm;
	}
	public void setLehm(int lehm) {
		this.lehm = lehm;
	}
}
