package pkgModel;

//import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement (name = "DWR")
public class LoginStatus {
	
	private static final long serialVersionUID = 1L;
	private String status;
	private String username;
	private String sessionId;
	
	/*@XmlJavaTypeAdapter(SqlDateAdapter.class)
	private Date lastLogin;*/
	
	
	public LoginStatus() {
		super();
	}
	
	public LoginStatus(String status, String username, String sessionId) {
		super();
		this.status = status;
		this.username = username;
		this.sessionId = sessionId;
		//this.lastLogin = lastLogin;
	}
	@Override
	public String toString() {
		return "LoginStatus [status=" + status + ", username=" + username
				+ ", sessionId=" + sessionId + "]";/*, lastLogin=" + lastLogin + "]";*/
				
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/*public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}*/
}
