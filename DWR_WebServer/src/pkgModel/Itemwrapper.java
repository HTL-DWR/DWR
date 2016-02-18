package pkgModel;

import java.util.ArrayList;

public class Itemwrapper {
	private ArrayList items;

	public ArrayList getItems() {
		return items;
	}

	public void setItems(ArrayList items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Itemwrapper [items=" + items + "]";
	}

	public Itemwrapper(ArrayList items) {
		super();
		this.items = items;
	}

	public Itemwrapper() {
		super();
	}
}
