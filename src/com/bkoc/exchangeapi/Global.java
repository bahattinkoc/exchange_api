package com.bkoc.exchangeapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Global extends General{ // https://www.coingecko.com/api/documentations/v3
    public static BigDecimal percent24h;

    public static BigDecimal getPercent24h() {
        return percent24h;
    }

    public static void setPercent24h(BigDecimal _percent24h) {
        percent24h = _percent24h;
    }

    public static HashMap<String, String> getGlobal() throws IOException {
        try {
            JsonObject jsonObj = JsonParser
                    .parseString(response("https://api.coingecko.com/api/v3/global"))
                    .getAsJsonObject().get("data")
                    .getAsJsonObject();

            HashMap<String, String> global = new HashMap<>();
            for (String i : jsonObj.get("market_cap_percentage").getAsJsonObject().keySet())
                global.put(i, jsonObj.get("market_cap_percentage").getAsJsonObject().get(i).toString());
            setPercent24h(jsonObj.get("market_cap_change_percentage_24h_usd").getAsBigDecimal());
            return global;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
