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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WhiteBit extends General { // https://whitebit.com/api/v4/public/ --- https://whitebit.com/api/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /api/v1/public/tickers
        {
          "success": true,
          "message": null,
          "result": {
            "BTC_USDT": {                         // Name of market pair
              "ticker": {
                "bid": "9412.1",                  // Highest bid
                "ask": "9416.33",                 // Lowest ask
                "low": "9203.13",                 // Lowest price for 24h
                "high": "9469.99",                // Highest price for 24h
                "last": "9414.4",                 // Last deal price
                "vol": "27324.819448",            // Volume in stock currency
                "deal": "254587570.43407191",     // Volume in money currency
                "change": "1.53"                  // Change in percent between open and last prices
              },
              "at": 159423219                     // Timestamp in seconds
            },
            "ETH_BTC": {
              ...
            }
          }
        }
        */

        JsonObject symbolsListJsonObj = JsonParser
                .parseString(response("https://whitebit.com/api/v1/public/tickers"))
                .getAsJsonObject().get("result")
                .getAsJsonObject();

        List<String> symbolsList = new LinkedList<>();
        for (String i : symbolsListJsonObj.keySet())
            symbolsList.add(i);

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /api/v1/public/ticker?market=ETH_BTC
        {
          "success": true,
          "message": null,
          "result": {
            "open": "9267.98",               // Open price for day
            "bid": "9417.4",                 // The highest bid currently available
            "ask": "9421.64",                // The lowest ask currently available
            "low": "9203.13",                // Lowest price for day
            "high": "9469.99",               // Highest price for day
            "last": "9419.55",               // Latest deal price
            "volume": "27303.824558",        // Volume in stock
            "deal": "254399191.68843464",    // Volume in money
            "change": "1.63"                 // Change between open and close price in percentage
          }
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://whitebit.com/api/v1/public/ticker?market=" + symbol))
                .getAsJsonObject().get("result")
                .getAsJsonObject();

        BigDecimal close = tickerObj.get("last").getAsBigDecimal();
        BigDecimal volume = tickerObj.get("volume").getAsBigDecimal();
        BigDecimal open = tickerObj.get("open").getAsBigDecimal();
        BigDecimal priceChange = close.subtract(open);

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", tickerObj.get("change").getAsBigDecimal());
        ticker.put("volume", volume);

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /api/v1/public/kline?market=BTC_USDT&interval=1h&limit=100&start=1572415247&end=1632415247
        {
          "success": true,
          "message": null,
          "result": [
            [
                1631440800,            // Time in seconds
                "45865.62",            // Open
                "45958.14",            // Close
                "45981.3",             // High
                "45750.23",            // Low
                "15.327634",           // Volume stock
                "703140.24230131"      // Volume money
            ],
            [...]
          ]
        }
        */

        //1m, 3m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 8h, 12h, 1d, 3d, 1w, 1M.
        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180 : (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ? 21600
                : (interval == Interval.INT_8HOURS) ? 28800 : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ? 86400
                : (interval == Interval.INT_3DAYS) ? 3 * 86400 : (interval == Interval.INT_1WEEK) ? 7 * 86400 : 30 * 86400;

        long from = (System.currentTimeMillis() / 1000L) - (intervalResolution * 300);
        long to = System.currentTimeMillis() / 1000L;

        JsonArray klinesJson = JsonParser
                .parseString(response("https://whitebit.com/api/v1/public/kline?market=" + symbol + "&interval=" + interval.getValue() + "&limit=301&start=" + from + "&end=" + to))
                .getAsJsonObject().get("result")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonArray().get(1).getAsBigDecimal(),
                    e.getAsJsonArray().get(3).getAsBigDecimal(),
                    e.getAsJsonArray().get(4).getAsBigDecimal(),
                    e.getAsJsonArray().get(2).getAsBigDecimal(),
                    e.getAsJsonArray().get(5).getAsBigDecimal()));

        return list;
    }
}
