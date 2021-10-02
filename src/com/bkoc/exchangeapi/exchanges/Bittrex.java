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
import java.util.*;

public class Bittrex extends General { // https://api.bittrex.com/v3/
    public static List<String> getSymbols() throws IOException {
        /* GET /markets
        [
          {
            "symbol": "string",
            "baseCurrencySymbol": "string",
            "quoteCurrencySymbol": "string",
            "minTradeSize": "number (double)",
            "precision": "integer (int32)",
            "status": "string",
            "createdAt": "string (date-time)",
            "notice": "string",
            "prohibitedIn": [
              "string"
            ],
            "associatedTermsOfService": [
              "string"
            ],
            "tags": [
              "string"
            ]
          }
        ]
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api.bittrex.com/v3/markets"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            if (i.getAsJsonObject().get("status").getAsString().equals("ONLINE"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /markets/{marketSymbol}/summary
        [
          {
            "symbol": "string",
            "high": "number (double)",
            "low": "number (double)",
            "volume": "number (double)",
            "quoteVolume": "number (double)",
            "percentChange": "number (double)",
            "updatedAt": "string (date-time)"
          }
        ]
        */

//        iki istek göndermemek için bunu iptal ettim
//        JsonObject klinesAsJsonArray = JsonParser
//                .parseString(response("https://api.bittrex.com/v3/markets/" + symbol + "/summary"))
//                .getAsJsonObject();

        JsonArray lastOneJson = JsonParser
                .parseString(response("https://api.bittrex.com/v3/markets/" + symbol + "/candles/DAY_1/recent"))
                .getAsJsonArray();

        JsonObject lastOne = lastOneJson.get(lastOneJson.size()-1).getAsJsonObject();
        BigDecimal open = lastOne.get("open").getAsBigDecimal();
        BigDecimal close = lastOne.get("close").getAsBigDecimal();
        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", lastOne.get("volume").getAsBigDecimal());

        return ticker;
    }
    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /markets/{marketSymbol}/candles/{candleType}/{candleInterval}/recent
        [
          {
            "startsAt": "string (date-time)",
            "open": "number (double)",
            "high": "number (double)",
            "low": "number (double)",
            "close": "number (double)",
            "volume": "number (double)",
            "quoteVolume": "number (double)"
          }
        ]
        */

            // MINUTE_1, MINUTE_5, HOUR_1, DAY_1
            String intervalResolution = (interval == Interval.INT_1MIN) ? "MINUTE_1" : (interval == Interval.INT_5MIN) ? "MINUTE_5"
                    : (interval == Interval.INT_1HOUR) ? "HOUR_1" : "DAY_1";

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.bittrex.com/v3/markets/" + symbol + "/candles/" + intervalResolution + "/recent"))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));

            if (list.size() < 310)
                return list;
            else
                return list.subList(list.size() - 300, list.size());
        }
        catch (Exception e) { return null; }
    }
}
