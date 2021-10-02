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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TheRock extends General { // https://api.therocktrading.com/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /funds
        {
        "funds":
          [
            {
             "id": "BTCEUR",
             "description": "Trade bitcoin with euro",
             "type": "currency",
             "base_currency": "EUR",
             "trade_currency": "BTC",
             "buy_fee": 0.5,
             "sell_fee": 0.5,
             "minimum_price_offer": 0.01,
             "minimum_quantity_offer": 0.01,
             "base_currency_decimals": 2,
             "trade_currency_decimals": 2,
             "leverages": [
               1.5,
               ​2,
               2.5,
               3
             ]
            },
            ...
          ]
        }
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.therocktrading.com/v1/funds"))
                .getAsJsonObject().get("funds")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            symbolsList.add(i.getAsJsonObject().get("id").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /funds/BTCEUR/ticker
        {
            "fund_id": "BTCEUR",
            "date": "2015-06-13T19:17:45.847+02:00",
            "bid":220.47,
            "ask":223.0,
            "last":220.44,
            "volume":25726.86,
            "volume_traded":115.64,
            "open":219.32,
            "high":229.99,
            "low":219.32,
            "close":222.22
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.therocktrading.com/v1/funds/" + symbol + "/ticker"))
                .getAsJsonObject();

        BigDecimal open = klinesJson.get("open").getAsBigDecimal();
        BigDecimal close = klinesJson.get("last").getAsBigDecimal();
        BigDecimal volume = klinesJson.get("volume").getAsBigDecimal();
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
        try {
        /* GET /funds/BTCEUR/ohlc_statistics?period=1440&before=2021-09-23T13:00:00Z&after=before=2021-09-21T13:00:00Z
        [
           {
              "fund_id":"BTCEUR",
              "high":369.0,
              "low":369.0,
              "open":369.0,
              "close":369.0,
              "average":369.0,
              "weighted_average":369.0,
              "base_volume":36.9,
              "traded_volume":0.1,
              "interval_starts_at":"2016-04-19T17:00:00.000Z",
              "interval_ends_at":"2016-04-19T17:15:00.000Z"
           },
           ...
        ]
        */

            //Bunların hepsini yapabiliyor
            int internalSeconds = (interval == Interval.INT_1MIN) ? 1 : (interval == Interval.INT_3MIN) ? 3 : (interval == Interval.INT_5MIN) ? 5
                    : (interval == Interval.INT_15MIN) ? 15 : (interval == Interval.INT_30MIN) ? 30 : (interval == Interval.INT_1HOUR) ? 60 : 240;

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS&");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String end_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            calendar.setTimeInMillis((System.currentTimeMillis()) - (300L * internalSeconds * 1000L * 60));
            String start_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.therocktrading.com/v1/funds/" + symbol + "/ohlc_statistics?period=" + internalSeconds + "&before=" + end_time + "&after=" + start_time))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(),
                        e.getAsJsonObject().get("high").getAsBigDecimal(),
                        e.getAsJsonObject().get("low").getAsBigDecimal(),
                        e.getAsJsonObject().get("close").getAsBigDecimal(),
                        e.getAsJsonObject().get("base_volume").getAsBigDecimal()));
            Collections.reverse(list);
            return list;
        }
        catch (Exception e) { return null; }
    }
}
