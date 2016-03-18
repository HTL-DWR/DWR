package com.example.stefan.dwr_neu.Connectivity;

import com.example.stefan.dwr_neu.Model.Board;
import com.example.stefan.dwr_neu.Model.ResponseObject;
import com.google.gson.Gson;

import java.util.Objects;

/**
 * Created by User on 17.03.16.
 */
public class Deserializer {

    public static Object deserialize(String jsonString, Class Dataclass) throws Exception{

        Gson gson = new Gson();

        ResponseObject ro = gson.fromJson(jsonString, ResponseObject.class);


        if (ro.isOk()) {
            if (Dataclass.equals(int.class)) {
                ro.setData(Integer.parseInt(ro.getData().toString()));
            }

            String bstring = jsonString.split("data\":")[1];
            bstring = bstring.substring(0, bstring.length() - 1);
            System.out.println("boardjson:" + bstring);

            ro.setData((Object) gson.fromJson(bstring, Dataclass));
        } else {
            throw new Exception(ro.getErrormsg());
        }

        return ro.getData();
    }
}
