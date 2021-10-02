package com.bkoc.exchangeapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.bkoc.exchangeapi.General.response;

public class FAG { // https://api.alternative.me/fng/
    public static List<Float> getFAG() throws IOException {

        Request request = new Request.Builder().url("https://api.alternative.me/fng/?limit=30").build();
        OkHttpClient okHttpClient = new OkHttpClient();
        List<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_1_1);
        protocols.add(Protocol.HTTP_2);
        okHttpClient.newBuilder().protocols(protocols);
        Response response = (okHttpClient).newCall(request).execute();

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response.body().string())
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Float> fagList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            fagList.add(i.getAsJsonObject().get("value").getAsFloat());

        return fagList;
    }
}
