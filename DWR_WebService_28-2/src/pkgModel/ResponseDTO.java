package pkgModel;

import java.util.ArrayList;

public class ResponseDTO {
	private Boolean ok;
	private ArrayList<String> errors = new ArrayList<String>();
	private Itemwrapper items;
	
	public ResponseDTO(Boolean ok, ArrayList<String> errors, Itemwrapper items) {
		super();
		this.ok = ok;
		this.errors = errors;
		this.items = items;
	}

	public ResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "ResponseDTO [ok=" + ok + ", errors=" + errors + ", items="
				+ items + "]";
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public Itemwrapper getItems() {
		return items;
	}

	public void setItems(Itemwrapper items) {
		this.items = items;
	}
}
