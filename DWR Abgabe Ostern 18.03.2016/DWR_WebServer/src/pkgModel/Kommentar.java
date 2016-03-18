package pkgModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Kommentar {
	int id;
	String op;
	String text;
	Date postTime;

	public Kommentar() {
		super();
	}


	public Kommentar(int id, String op, String text, String postTime) {
		super();
		this.id = id;
		this.op = op;
		this.text = text;
		this.setPostTime(postTime);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}


	public String getPostTime() {
		return postTime.toString();
	}


	public void setPostTime(String postTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date d = new Date(0);
		try {
			d = new Date(sdf.parse(postTime).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.postTime = d;
	}

	/*public void setPostTime(long postTime) {
		this.postTime = new Date(postTime);
	}*/


	@Override
	public String toString() {
		return "Kommentar [id=" + id + ", op=" + op + ", text=" + text
				+ ", postTime=" + postTime + "]";
	}
}