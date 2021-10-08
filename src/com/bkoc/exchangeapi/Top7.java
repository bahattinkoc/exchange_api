package com.bkoc.exchangeapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Top7 extends General{ // https://www.coingecko.com/api/documentations/v3
    public static List<HashMap<String, String>> getTop7() throws IOException {
        JsonArray jsonArray = JsonParser
                .parseString(response("https://api.coingecko.com/api/v3/search/trending"))
                .getAsJsonObject().get("coins")
                .getAsJsonArray();

        List<HashMap<String, String>> top7 = new LinkedList<>();
        for (JsonElement i : jsonArray){
            JsonObject item = i.getAsJsonObject().get("item").getAsJsonObject();
            HashMap<String, String> hash = new HashMap<>();
            hash.put("name", item.get("name").toString());
            hash.put("symbol", item.get("symbol").toString());
            hash.put("rank", item.get("market_cap_rank").toString());
            hash.put("small", item.get("small").toString());
            hash.put("price_btc", item.get("price_btc").toString());
            top7.add(hash);
        }

        return top7;
    }
}
