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

public class Bitstamp extends General { // https://www.bitstamp.net/api/v2/
    public static List<String> getSymbols() throws IOException {
        /* GET trading-pairs-info/
        [
            {
                "base_decimals": 8,
                "minimum_order": "20.0 USD",
                "name": "BTC/USD",
                "counter_decimals": 2,
                "trading": "Enabled",
                "url_symbol": "btcusd",
                "description": "Bitcoin / U.S. dollar"
            },
            ..
        ]
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://www.bitstamp.net/api/v2/trading-pairs-info/"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            symbolsList.add(i.getAsJsonObject().get("url_symbol").getAsString().toUpperCase(Locale.ROOT));

        return symbolsList;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET ticker/{currency_pair}/
        {
            "high": "48834.00",
            "last": "48197.19",
            "timestamp": "1632008864",
            "bid": "48173.07",
            "vwap": "48291.20",
            "volume": "1458.70183447",
            "low": "47052.90",
            "ask": "48197.47",
            "open": "47316.21"
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://www.bitstamp.net/api/v2/ticker/" + symbol.toLowerCase(Locale.ROOT)))
                .getAsJsonObject();

        BigDecimal open = tickerObj.get("open").getAsBigDecimal();
        BigDecimal close = tickerObj.get("last").getAsBigDecimal();
        BigDecimal volume = tickerObj.get("volume").getAsBigDecimal();

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
        /* GET ohlc/btcusdt/?step=60&limit=100
        {
            "data":
            {
                "pair": "BTC/USDT",
                "ohlc":
                [
                    {
                        "high": "47958.42",
                        "timestamp": "1632003000",
                        "volume": "0.00000000",
                        "low": "47958.42",
                        "close": "47958.42",
                        "open": "47958.42"
                    },
                    ..
                ]
            }
        }
        */

            //Bunları yapabiliyor
            int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180 : (interval == Interval.INT_5MIN) ? 300
                    : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                    : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ? 21600
                    : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ? 86400 : 3 * 86400;

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://www.bitstamp.net/api/v2/ohlc/" + symbol.toLowerCase(Locale.ROOT) + "/?step=" + intervalResolution + "&limit=300"))
                    .getAsJsonObject().get("data")
                    .getAsJsonObject().get("ohlc")
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
