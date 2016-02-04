package pkgModel;

/**
 * Diese Klasse liefert ein Object (data) zurück. 
 * Bei Fehlern ist dieses Object null.
 * Fehlerüberprüfung mit .getErrormsg(), wenn ResponseObject.isOk() == false
 * Wenn man ein leeres, vorbereitetes RO wünscht: prepareRO() setzt ok auf falsch, leert die errormsg und setzt das Datenobjekt auf null.
 * 
 * @author Notsch Tino
 *
 */
public class ResponseObject {
	private boolean ok;
	private String errormsg;
	private Object data;
	
	
	public ResponseObject(){
		super();
	}
	
	/**
	 * Setzt ok auf falsch, errormsg auf "" und data auf null um das Object vorzubereiten für den WebService
	 */
	public void prepareRO(){
		ok=false;
		errormsg="";
		data=null;
	}
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
