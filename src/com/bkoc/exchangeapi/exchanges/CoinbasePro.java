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

public class CoinbasePro extends General { // https://api.pro.coinbase.com
    public static List<String> getSymbols() throws IOException {
        /* GET /products
        [
            {
                "id": "BTC-USD",
                "display_name": "BTC/USD",
                "base_currency": "BTC",
                "quote_currency": "USD",
                "base_increment": "0.00000001",
                "quote_increment": "0.01000000",
                "base_min_size": "0.00100000",
                "base_max_size": "280.00000000",
                "min_market_funds": "5",
                "max_market_funds": "1000000",
                "status": "online",
                "status_message": "",
                "cancel_only": false,
                "limit_only": false,
                "post_only": false,
                "trading_disabled": false,
                "fx_stablecoin": false
            },
            ...
        ]
        */

        JsonArray symbolsList = JsonParser
                .parseString(response("https://api.pro.coinbase.com/products?"))
                .getAsJsonArray();

        List<String> list = new LinkedList<>();
        for (JsonElement e : symbolsList)
            if (e.getAsJsonObject().get("trading_disabled").getAsString().equals("false"))
                list.add(e.getAsJsonObject().get("id").getAsString());

        return list;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /products/<product-id>/candles
        [
            [ time, low, high, open, close, volume ],
            [ 1415398768, 0.32, 4.2, 0.35, 4.2, 12.3 ],
            ...
        ]
        */

        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180: (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ?  21600
                : (interval == Interval.INT_8HOURS) ? 28800 : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ?  86400
                : (interval == Interval.INT_3DAYS) ? 3*86400 : (interval == Interval.INT_1WEEK) ? 7*86400 : 30*86400;

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://api.pro.coinbase.com/products/" + symbol + "/candles" + "?granularity=" + intervalResolution))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesAsJsonArray) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(3).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(1).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(5).getAsBigDecimal()));
        }
        Collections.reverse(list);
        return list;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /products/<product-id>/stats
        {
            "open": "6745.61000000",
            "high": "7292.11000000",
            "low": "6650.00000000",
            "volume": "26185.51325269",
            "last": "6813.19000000",
            "volume_30day": "1019451.11188405"
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://api.pro.coinbase.com/products/" + symbol + "/stats"))
                .getAsJsonObject();

        BigDecimal open = tickerObj.get("open").getAsBigDecimal();
        BigDecimal close = tickerObj.get("last").getAsBigDecimal();
        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerObj.get("last").getAsBigDecimal());
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", tickerObj.get("volume").getAsBigDecimal());

        return ticker;
    }
}
