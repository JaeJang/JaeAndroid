package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaeja on 2018-01-07.
 */

public class BitcoinData {
    private String lastPrice;
    public static BitcoinData fromJson(JSONObject jsonObject){
        try{
            BitcoinData bitcoinData = new BitcoinData();
            double temp = jsonObject.getDouble("last");
            bitcoinData.lastPrice = Double.toString(temp);

            return bitcoinData;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getLastPrice(){
        return lastPrice;
    }
}
