package com.bkoc.exchangeapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.bkoc.exchangeapi.General.response;

public class FAG { // https://api.alternative.me/fng/
    public static List<Float> getFAG() throws IOException {
        JsonArray jsonArray = JsonParser
                .parseString(response("https://api.alternative.me/fng/?limit=30"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Float> fagList = new LinkedList<>();
        for (JsonElement i : jsonArray)
            fagList.add(i.getAsJsonObject().get("value").getAsFloat());

        return fagList;
    }
}
