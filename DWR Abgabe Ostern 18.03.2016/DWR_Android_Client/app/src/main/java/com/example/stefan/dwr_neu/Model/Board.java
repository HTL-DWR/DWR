
package com.example.stefan.dwr_neu.Model;


import com.example.stefan.dwr_neu.Model.Kommentar;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Board {
    int id;
    String name;
    String op;
    String text;
    String postTime;

    ArrayList<Kommentar> kommentar = new ArrayList<Kommentar>();

    public Board() {
        super();
        postTime = "1970-01-01";
        text="";
    }



    public Board(int id, String name, String op, String text,
                 String postTime, ArrayList<Kommentar> kommentar) {
        super();
        this.id = id;
        this.name = name;
        this.op = op;
        this.text = text;
        this.setPostTime(postTime);
        this.kommentar = kommentar;
    }

    public String getPostTime() {
        return postTime.toString();
    }



    public void setPostTime(String _postTime) {
        postTime=_postTime;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }



    public ArrayList<Kommentar> getKommentar() {
        return kommentar;
    }

    public void setKommentar(ArrayList<Kommentar> kommentar) {
        this.kommentar = kommentar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Board [id=" + id + ", name=" + name + ", op=" + op + ", text="
                + text + ", postTime=" + postTime + ", kommentar=" + kommentar
                + "]";
    }
}
	