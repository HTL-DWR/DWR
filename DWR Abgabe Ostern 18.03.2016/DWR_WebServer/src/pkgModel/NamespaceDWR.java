package pkgModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DWR")
public class NamespaceDWR {
	MapDorfCollection mdc = new MapDorfCollection();

	@XmlElement(name = "Doerfer")
	public MapDorfCollection getMdc() {
		return mdc;
	}

	public void setMdc(MapDorfCollection mdc) {
		this.mdc = mdc;
	}

	public NamespaceDWR(MapDorfCollection mdc) {
		super();
		this.mdc = mdc;
	}

	public NamespaceDWR() {
		super();
	}

	@Override
	public String toString() {
		return "NamespaceDWR [mdc=" + mdc + "]";
	} 
	
	
	
}
