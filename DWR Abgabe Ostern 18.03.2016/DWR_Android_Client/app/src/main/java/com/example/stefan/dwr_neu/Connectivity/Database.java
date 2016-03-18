package com.example.stefan.dwr_neu.Connectivity;

import com.example.stefan.dwr_neu.Model.Board;
import com.example.stefan.dwr_neu.Model.BoardPreview;
import com.example.stefan.dwr_neu.Model.BoardPreviewList;
import com.example.stefan.dwr_neu.Model.Credentials;
import com.example.stefan.dwr_neu.Model.ResponseObject;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stefan on 26.02.2016.
 */
public class Database
{

    private static Database db;
    private Credentials credentials;
    private int SessionID=-1;

    private Database()
    {

    }
    public static Database newInstance()
    {
        if(db==null)
            db = new Database();
        return db;
    }

    public void setCredentials(Credentials c)
    {
        credentials=c;
    }


    public void setSession() throws Exception {
        ResponseObject b;
        Gson g= new Gson();
        WSController wsc = new WSController();
        String hash="";
        try{
            hash = new BigInteger(1,MessageDigest.getInstance("MD5").digest(credentials.passwd.getBytes())).toString(16);
            while(hash.length()<32)
            {
                hash="0"+hash;
            }

        }catch(NoSuchAlgorithmException nsae){System.out.println("Java architecure is stupid, as it's mindset says, it is better to make an Exception, that is thrown when your string input is wrong instead of using an fucking enum, or just make different methods for different algorithms, wich is kind of the purpose of functions/mehtods. So I have to make this catch block, that is impossible to ever happen, because I inputted a hardcoded string. If you ever read this, you either read the code, in wich case I'm sorry for everything cruel I did in here or the MessageDigest Class does not support MD5 anymore.");}
        System.out.println("Passwordhash:" + hash);
        String ss = "LoginSession?uname="+credentials.uname+"&passhash="+hash;
        ss=ss.replaceAll(" ","%20");



        wsc.execute(ss,"GET");

        this.SessionID = (int) Deserializer.deserialize(wsc.get(), int.class);
    }



    public Board getBoard(int boardid) throws Exception {
        Board retVal=new Board();

        WSController wsc = new WSController();
        String ss = "BoardDetail?id="+boardid;
        ss=ss.replaceAll(" ","%20");
        wsc.execute(ss,"GET");

        retVal = (Board) Deserializer.deserialize(wsc.get(), Board.class);
        return retVal;



    }

    public ArrayList<BoardPreview> getAllBoardNames()
    {


        ArrayList<BoardPreview> retVal= new ArrayList<BoardPreview>();

        WSController wsc = new WSController();
        String ss = "BoardList";
        ss=ss.replaceAll(" ","%20");
        wsc.execute(ss,"GET");

        try {
            Vector<BoardPreview> boardPreviewVector = new Vector<BoardPreview>();
            boardPreviewVector = ((BoardPreviewList) Deserializer.deserialize(wsc.get(), BoardPreviewList.class)).getBoardPreviews();

            for (BoardPreview bp : boardPreviewVector) {
                retVal.add(bp);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return retVal;

    }



    public void addBoard(String name, String text) {

        String params = "BoardDetail?name=" + name + "&text=" + text + "&opName=" + credentials.uname + "&sessionid=" + SessionID;

        params=params.replaceAll(" ","%20");

        WSController wsc = new WSController();
        wsc.execute(params, "POST");

    }

    public void postComment(String text,int boardid)
    {

        String ss = "Comment?boardId="+boardid+"&text="+text+ "&opName="+ credentials.uname + "&sessionid="+this.SessionID;
        ss=ss.replaceAll(" ","%20");



        WSController wsc = new WSController();
        wsc.execute(ss, "PUT");
    }
}
