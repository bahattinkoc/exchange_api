package com.bkoc.exchangeapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.bkoc.exchangeapi.General.response;

public class FAG { // https://api.alternative.me/fng/
    public static List<Integer> getFAG() throws IOException {

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.alternative.me/fng/?limit=10"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Integer> fagList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            fagList.add(i.getAsJsonObject().get("value").getAsInt());

        return fagList;
    }
}
