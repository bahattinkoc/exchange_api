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

public class ChangellyPro extends General { //https://api.pro.changelly.com/api/3/public/
    public static List<String> getSymbols() throws IOException {
        /* GET /symbol
        {
            "ETHBTC": {
                "type": "spot",
                "base_currency": "ETH",
                "quote_currency": "BTC",
                "quantity_increment": "0.001",
                "tick_size": "0.000001",
                "take_rate": "0.001",
                "make_rate": "-0.0001",
                "fee_currency": "BTC",
                "margin_trading": true,
                "max_initial_leverage": "10.00"
            }
        }
        */

        JsonObject symbolsListJsonObj = JsonParser
                .parseString(response("https://api.pro.changelly.com/api/3/public/symbol"))
                .getAsJsonObject();

        List<String> symbolsList = new LinkedList<>();
        for (String i : symbolsListJsonObj.keySet())
            symbolsList.add(i);

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker?symbols=BTCUSDT
        {
            "ETHBTC": {
                "ask": "0.050043",
                "bid": "0.050042",
                "last": "0.050042",
                "low": "0.047052",
                "high": "0.051679",
                "open": "0.047800",
                "volume": "36456.720",
                "volume_quote": "1782.625000",
                "timestamp": "2021-06-12T14:57:19.999Z"
            }
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.pro.changelly.com/api/3/public/ticker?symbols=" + symbol))
                .getAsJsonObject().get(symbol)
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
        /* GET /candles?symbols=BTCUSDT&period=H1&limit=300
        {
            "BTCUSDT":[
              {
                "timestamp": "2021-07-01T20:00:00.000Z",
                "open": "33079.93",
                "close": "33236.53",
                "min": "33079.93",
                "max": "33295.73",
                "volume": "146.86223",
                "volume_quote": "4877838.3025063"
              },
              ...
           ]
        }
        */

            //Bunların hepsini yapabiliyor
            String intervalResolution = (interval == Interval.INT_1MIN) ? "M1" : (interval == Interval.INT_3MIN) ? "M3" : (interval == Interval.INT_5MIN) ? "M5"
                    : (interval == Interval.INT_15MIN) ? "M15" : (interval == Interval.INT_30MIN) ? "M30" : (interval == Interval.INT_1HOUR) ? "H1"
                    : (interval == Interval.INT_4HOURS) ? "H4" : (interval == Interval.INT_1DAY) ? "D1" : "D7";

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.pro.changelly.com/api/3/public/candles?symbols=" + symbol + "&period=" + intervalResolution + "&limit=300"))
                    .getAsJsonObject().get(symbol)
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(),
                        e.getAsJsonObject().get("max").getAsBigDecimal(),
                        e.getAsJsonObject().get("min").getAsBigDecimal(),
                        e.getAsJsonObject().get("close").getAsBigDecimal(),
                        e.getAsJsonObject().get("volume").getAsBigDecimal()));
            Collections.reverse(list);
            return list;
        }
        catch (Exception e) { return null; }
    }
}
