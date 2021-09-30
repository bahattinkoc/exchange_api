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

public class Okex extends General { // https://www.okex.com
    public static List<String> getSymbols() throws IOException {
        /* GET /api/spot/v3/instruments
        [
            {
                "base_currency":"BTC",
                "instrument_id":"BTC-USDT",
                "min_size":"0.001",
                "quote_currency":"USDT",
                "size_increment":"0.00000001",
                "category":"1",
                "tick_size":"0.1"
            },
            {
                "base_currency":"ETH",
                "instrument_id":"ETH-USDT",
                "min_size":"0.001",
                "quote_currency":"USDT",
                "size_increment":"0.000001",
                "category":"1",
                "tick_size":"0.01"
            }
        ]
        */

        // Var olan tekli kriptoların listesini al BTC,ETH...
        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://www.okex.com/api/spot/v3/instruments"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            symbolsList.add(i.getAsJsonObject().get("instrument_id").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /api/spot/v3/instruments/<instrument_id>/ticker
        {
            "best_ask": "7222.2",
            "best_bid": "7222.1",
            "instrument_id": "BTC-USDT",
            "product_id": "BTC-USDT",
            "last": "7222.2",
            "last_qty": "0.00136237",
            "ask": "7222.2",
            "best_ask_size": "0.09207739",
            "bid": "7222.1",
            "best_bid_size": "3.61314948",
            "open_24h": "7356.8",
            "high_24h": "7367.7",
            "low_24h": "7160",
            "base_volume_24h": "18577.2",
            "open_utc0": "34067.1",
            "open_utc8": "33830.9",
            "timestamp": "2019-12-11T07:48:04.014Z",
            "quote_volume_24h": "134899542.8"
        }
        */

        JsonObject tickerJson = JsonParser
                .parseString(response("https://www.okex.com/api/spot/v3/instruments/" + symbol + "/ticker"))
                .getAsJsonObject();

        BigDecimal open = tickerJson.get("open_utc0").getAsBigDecimal();
        BigDecimal close = tickerJson.get("last").getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerJson.get("last").getAsBigDecimal());
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", tickerJson.get("base_volume_24h").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /api/spot/v3/instruments/<instrument_id>/candles?granularity=86400
        [
            [
                "2019-03-19T16:00:00.000Z",
                "3997.3",
                "4031.9",
                "3982.5",
                "3998.7",
                "26175.21141385"
            ],
            [
                "2019-03-18T16:00:00.000Z",
                "3980.6",
                "4014.6",
                "3968.9",
                "3997.3",
                "33053.48725643"
            ]
        ]
        */

        //Hepsini yapıyor
        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180: (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ?  21600
                : (interval == Interval.INT_8HOURS) ? 28800 : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ?  86400
                : (interval == Interval.INT_3DAYS) ? 3*86400 : (interval == Interval.INT_1WEEK) ? 7*86400 : 30*86400;

        JsonArray klinesJson = JsonParser
                .parseString(response("https://www.okex.com/api/spot/v3/instruments/" + symbol + "/candles?granularity=" + intervalResolution))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(1).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(5).getAsBigDecimal()));
        }
        Collections.reverse(list);
        return list;
    }
}
