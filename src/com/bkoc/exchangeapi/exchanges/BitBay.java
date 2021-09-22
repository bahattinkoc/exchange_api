package com.bkoc.exchangeapi.exchanges;

import com.bkoc.exchangeapi.Candlestick;
import com.bkoc.exchangeapi.General;
import com.bkoc.exchangeapi.Interval;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BitBay extends General { // https://api.bitbay.net/rest/trading/
    public static List<String> getSymbols() throws IOException {
        /* GET /ticker/{trading_pair}
        {
           "status":"Ok",
           "items":{
              "DAI-PLN":{
                 "market":{
                    "code":"DAI-PLN",
                    "first":{
                       "currency":"DAI",
                       "minOffer":"1.08",
                       "scale":8
                    },
                    "second":{
                       "currency":"PLN",
                       "minOffer":"5",
                       "scale":2
                    }
                 },
                 "time":"1632345510096",
                 "highestBid":"3.88",
                 "lowestAsk":"3.99",
                 "rate":"3.94",
                 "previousRate":"3.99"
              }
           }
        }
        */

        JsonObject symbolsListJsonObj = JsonParser
                .parseString(response("https://api.bitbay.net/rest/trading/ticker"))
                .getAsJsonObject().get("items")
                .getAsJsonObject();

        List<String> symbolsList = new LinkedList<>();
        for (String i : symbolsListJsonObj.keySet())
            symbolsList.add(i);

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
                /* GET /candle/history/trading_pair/resolution
        {
           "status":"Ok",
           "items":[
              [
                 "1632348000000",
                 {
                    "o":"43169.13",
                    "c":"43169.13",
                    "h":"43169.13",
                    "l":"43169.13",
                    "v":"0",
                    "co":"0"
                 }
              ]
           ]
        }
        */

        long from = System.currentTimeMillis() - (86400 * 1 * 1000L);
        long to = System.currentTimeMillis();

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.bitbay.net/rest/trading/candle/history/" + symbol + "/86400?from=" + from + "&to=" + to))
                .getAsJsonObject().get("items")
                .getAsJsonArray();

        BigDecimal open = klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(1).getAsJsonObject().get("o").getAsBigDecimal();
        BigDecimal close = klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(1).getAsJsonObject().get("c").getAsBigDecimal();
        BigDecimal volume = klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(1).getAsJsonObject().get("v").getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", volume);

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /candle/history/trading_pair/resolution
        {
           "status":"Ok",
           "items":[
              [
                 "1632348000000",
                 {
                    "o":"43169.13",
                    "c":"43169.13",
                    "h":"43169.13",
                    "l":"43169.13",
                    "v":"0",
                    "co":"0"
                 }
              ]
           ]
        }
        */

        //Bunların hepsini yapabiliyor
        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180 : (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ? 21600
                : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ? 86400
                : (interval == Interval.INT_3DAYS) ? 3 * 86400 : 7 * 86400;

        long from = System.currentTimeMillis() - (intervalResolution * 300 * 1000L);
        long to = System.currentTimeMillis();

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.bitbay.net/rest/trading/candle/history/" + symbol + "/" + intervalResolution + "?from=" + from + "&to=" + to))
                .getAsJsonObject().get("items")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonArray().get(1).getAsJsonObject().get("o").getAsBigDecimal(),
                    e.getAsJsonArray().get(1).getAsJsonObject().get("h").getAsBigDecimal(),
                    e.getAsJsonArray().get(1).getAsJsonObject().get("l").getAsBigDecimal(),
                    e.getAsJsonArray().get(1).getAsJsonObject().get("c").getAsBigDecimal(),
                    e.getAsJsonArray().get(1).getAsJsonObject().get("v").getAsBigDecimal()));

        return list;
    }
}
