package com.example.jaeja.howmuch;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaeja on 2018-01-07.
 */

public class DataHandler {

    String exchangedValue;

    public static DataHandler fromJson(JSONObject jsonObject, String selectedCurrency){
        try{
            DataHandler datahandler = new DataHandler();
            double temp = jsonObject.getJSONObject("rates").getDouble(selectedCurrency);
            datahandler.exchangedValue = Double.toString(temp);
            return datahandler;
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getValue() {
        return exchangedValue;
    }
}
