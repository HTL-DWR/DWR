package pkgModel;

import java.util.Vector;

public class DorfIdMapper {
	private Vector<Integer> doerfer = new Vector<Integer>();

	public DorfIdMapper(Vector<Integer> doerfer) {
		super();
		this.doerfer = doerfer;
	}

	public DorfIdMapper() {
		super();
	}

	public Vector<Integer> getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(Vector<Integer> doerfer) {
		this.doerfer = doerfer;
	}

	@Override
	public String toString() {
		return "DorfIdMapper [doerfer=" + doerfer + "]";
	}
}
