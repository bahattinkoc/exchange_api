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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Currencycom extends General { // https://api-adapter.backend.currency.com/api/v2/
    public static List<String> getSymbols() throws IOException {
        /* GET /exchangeInfo
        {
           "timezone":"UTC",
           "serverTime":1632264247558,
           "rateLimits":[
              {
                 "rateLimitType":"REQUEST_WEIGHT",
                 "interval":"MINUTE",
                 "intervalNum":1,
                 "limit":1200
              },
              {
                 "rateLimitType":"ORDERS",
                 "interval":"SECOND",
                 "intervalNum":1,
                 "limit":10
              },
              {
                 "rateLimitType":"ORDERS",
                 "interval":"DAY",
                 "intervalNum":1,
                 "limit":864000
              }
           ],
           "exchangeFilters":[

           ],
           "symbols":[
              {
                 "symbol":"BTC/USDT",
                 "name":"Bitcoin / Tether",
                 "status":"TRADING",
                 "baseAsset":"BTC",
                 "baseAssetPrecision":3,
                 "quoteAsset":"USDT",
                 "quoteAssetId":"USDT",
                 "quotePrecision":3,
                 "orderTypes":[
                    "LIMIT",
                    "MARKET"
                 ],
                 "filters":[
                    {
                       "filterType":"LOT_SIZE",
                       "minQty":"0.001",
                       "maxQty":"100",
                       "stepSize":"0.001"
                    },
                    {
                       "filterType":"MIN_NOTIONAL",
                       "minNotional":"42"
                    }
                 ],
                 "marketModes":[
                    "REGULAR"
                 ],
                 "marketType":"SPOT",
                 "country":"",
                 "sector":"",
                 "industry":"",
                 "tradingHours":"UTC; Mon - 21:00, 21:05 -; Tue - 21:00, 21:05 -; Wed - 21:00, 21:05 -; Thu - 21:00, 21:05 -; Fri - 21:00, 22:01 -; Sat - 21:00, 21:05 -; Sun - 20:00, 21:05 -",
                 "tickSize":0.01,
                 "tickValue":412.1525,
                 "exchangeFee":0.2
              }
           ]
        }
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api-adapter.backend.currency.com/api/v2/exchangeInfo"))
                .getAsJsonObject().get("symbols")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            if (i.getAsJsonObject().get("status").getAsString().equals("TRADING"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker/24hr?symbol=FB.
        {
           "symbol":"FB.",
           "priceChange":"-2.69",
           "priceChangePercent":"-0.75",
           "weightedAvgPrice":"356.80",
           "prevClosePrice":"359.13",
           "lastPrice":"356.44",
           "lastQty":"611.0",
           "bidPrice":"356.44",
           "askPrice":"357.15",
           "openPrice":"359.13",
           "highPrice":"356.84",
           "lowPrice":"356.44",
           "volume":"9",
           "quoteVolume":"3207.96",
           "openTime":1632182400000,
           "closeTime":1632266064813
        }
        */

        JsonObject tickerJson = JsonParser
                .parseString(response("https://api-adapter.backend.currency.com/api/v2/ticker/24hr?symbol=" + symbol))
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerJson.get("lastPrice").getAsBigDecimal());
        ticker.put("priceChange", tickerJson.get("priceChange").getAsBigDecimal());
        ticker.put("priceChangePercent", tickerJson.get("priceChangePercent").getAsBigDecimal());
        ticker.put("volume", tickerJson.get("volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /klines?symbol=BTC/USDT&interval=1m&limit=1
        [
           [
              1632266700000,
              "40422.26",
              "40497.62",
              "40415.97",
              "40484.93",
              60
           ]
        ]
        */

            //Sadece bunlar
            String intervalResolution = (interval == Interval.INT_1MIN) ? "1m" : (interval == Interval.INT_5MIN) ? "5m"
                    : (interval == Interval.INT_15MIN) ? "15m" : (interval == Interval.INT_30MIN) ? "30m" : (interval == Interval.INT_1HOUR) ? "1h"
                    : (interval == Interval.INT_4HOURS) ? "4h" : (interval == Interval.INT_1DAY) ? "1d" : "1w";

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api-adapter.backend.currency.com/api/v2/klines?symbol=" + symbol + "&interval=" + intervalResolution + "&limit=200"))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonArray().get(1).getAsBigDecimal(), e.getAsJsonArray().get(2).getAsBigDecimal(), e.getAsJsonArray().get(3).getAsBigDecimal(), e.getAsJsonArray().get(4).getAsBigDecimal(), e.getAsJsonArray().get(5).getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
