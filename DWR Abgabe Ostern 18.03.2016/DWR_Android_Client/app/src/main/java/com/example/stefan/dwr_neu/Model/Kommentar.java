package com.example.stefan.dwr_neu.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Kommentar {
    int id;
    String op;
    String text;
    String postTime;

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


    public void setPostTime(String _postTime) {

        this.postTime = _postTime;
    }



    @Override
    public String toString() {
        return op+"["+this.postTime+"]: "+this.getText();
    }
}
