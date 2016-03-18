package pkgModel;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")	//Es sind Objects in der Array List
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