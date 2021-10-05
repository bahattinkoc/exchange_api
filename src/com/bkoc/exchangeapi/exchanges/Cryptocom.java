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

public class Cryptocom extends General { // https://api.crypto.com/v2/
    public static List<String> getSymbols() throws IOException {
        /* GET public/get-instruments
        {
           "id": 11,
           "method": "public/get-instruments",
           "code": 0,
           "result": {
             "instruments": [
               {
                 "instrument_name": "BTC_USDT",
                 "quote_currency": "BTC",
                 "base_currency": "USDT",
                 "price_decimals": 2,
                 "quantity_decimals": 6,
                 "margin_trading_enabled": true

               },
               {
                 "instrument_name": "CRO_BTC",
                 "quote_currency": "BTC",
                 "base_currency": "CRO",
                 "price_decimals": 8,
                 "quantity_decimals": 2,
                 "margin_trading_enabled": false
               }
             ]
           }
        }
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api.crypto.com/v2/public/get-instruments"))
                .getAsJsonObject().get("result")
                .getAsJsonObject().get("instruments")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            symbolsList.add(i.getAsJsonObject().get("instrument_name").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /get-candlestick?instrument_name=BTC_USDT&timeframe=5m
        {
          "code":0,
          "method":"public/get-candlestick",
          "result":{
            "instrument_name":"BTC_USDT",
            "interval":"5m",
            "data":[
              {"t":1596944700000,"o":11752.38,"h":11754.77,"l":11746.65,"c":11753.64,"v":3.694583},
              {"t":1596945000000,"o":11753.63,"h":11754.77,"l":11739.83,"c":11746.17,"v":2.073019},
              {"t":1596945300000,"o":11746.16,"h":11753.24,"l":11738.1,"c":11740.65,"v":0.867247},
              ...
            ]
          }
        }
        */

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.crypto.com/v2/public/get-candlestick?instrument_name=" + symbol + "&timeframe=1D"))
                .getAsJsonObject().get("result")
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        JsonObject lastOne = klinesJson.get(klinesJson.size()-1).getAsJsonObject();
        BigDecimal open = lastOne.get("o").getAsBigDecimal();
        BigDecimal close = lastOne.get("c").getAsBigDecimal();
        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", lastOne.get("v").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /get-candlestick?instrument_name=BTC_USDT&timeframe=5m
        {
          "code":0,
          "method":"public/get-candlestick",
          "result":{
            "instrument_name":"BTC_USDT",
            "interval":"5m",
            "data":[
              {"t":1596944700000,"o":11752.38,"h":11754.77,"l":11746.65,"c":11753.64,"v":3.694583},
              {"t":1596945000000,"o":11753.63,"h":11754.77,"l":11739.83,"c":11746.17,"v":2.073019},
              {"t":1596945300000,"o":11746.16,"h":11753.24,"l":11738.1,"c":11740.65,"v":0.867247},
              ...
            ]
          }
        }
        */

            //1m, 5m, 15m, 30m, 1h, 4h, 6h, 12h, 1D, 7D, 14D, 1M
            String intervalResolution = (interval == Interval.INT_1DAY) ? "1D" : (interval == Interval.INT_1WEEK) ? "7D" : interval.getValue();

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.crypto.com/v2/public/get-candlestick?instrument_name=" + symbol + "&timeframe=" + intervalResolution))
                    .getAsJsonObject().get("result")
                    .getAsJsonObject().get("data")
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("o").getAsBigDecimal(), e.getAsJsonObject().get("h").getAsBigDecimal(), e.getAsJsonObject().get("l").getAsBigDecimal(), e.getAsJsonObject().get("c").getAsBigDecimal(), e.getAsJsonObject().get("v").getAsBigDecimal()));

            if (list.size() < 210)
                return list;
            else
                return list.subList(list.size() - 200, list.size());
        }
        catch (Exception e) { return null; }
    }
}
