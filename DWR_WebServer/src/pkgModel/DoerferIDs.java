package pkgModel;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "DWR")
public class DoerferIDs {
	
	private Vector<Integer> Integer = new Vector<Integer>();

	public Vector<Integer> getDorf() {
		return Integer;
	}

	public void setDorf(Vector<Integer> dorf) {
		this.Integer = dorf;
	}

	@Override
	public String toString() {
		return "DoerferIDs [dorf=" + Integer + "]";
	}

	public DoerferIDs(Vector<Integer> dorf) {
		super();
		this.Integer = dorf;
	}

	public DoerferIDs() {
		super();
	}
}
