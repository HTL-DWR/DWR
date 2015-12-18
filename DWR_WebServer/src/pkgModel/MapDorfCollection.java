package pkgModel;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MapDorfCollection")
public class MapDorfCollection {
	
	private ArrayList<DorfMap> doerfer = new ArrayList<DorfMap>();

	@XmlElement(name = "Dorf")
	public ArrayList<DorfMap> getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(ArrayList<DorfMap> doerfer) {
		this.doerfer = doerfer;
	}

	public MapDorfCollection(ArrayList<DorfMap> doerfer) {
		super();
		this.doerfer = doerfer;
	}
	
	public MapDorfCollection() {
		super();
	}

	@Override
	public String toString() {
		return "MapDorfCollection [doerfer=" + doerfer + "]";
	}
}
