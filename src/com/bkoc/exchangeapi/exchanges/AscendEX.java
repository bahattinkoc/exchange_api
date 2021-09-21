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

public class AscendEX extends General { // https://ascendex.com/api/pro/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /products
        {
            "code": 0,
            "data": [
                {
                    "symbol":                "ASD/USDT",
                    "baseAsset":             "ASD",
                    "quoteAsset":            "USDT",
                    "status":                "Normal",
                    "minNotional":           "5",
                    "maxNotional":           "100000",
                    "marginTradable":         true,
                    "commissionType":        "Quote",
                    "commissionReserveRate": "0.001",
                    "tickSize":              "0.000001",
                    "lotSize":               "0.001"
                }
            ]
        }
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://ascendex.com/api/pro/v1/products"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray) {
            if (i.getAsJsonObject().get("status").getAsString().equals("Normal"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());
        }

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker
        {
            "code": 0,
            "data": {
                "symbol": "ASD/USDT",
                "open":   "0.06777",
                "close":  "0.06809",
                "high":   "0.06899",
                "low":    "0.06708",
                "volume": "19823722",
                "ask": [
                    "0.0681",
                    "43641"
                ],
                "bid": [
                    "0.0676",
                    "443"
                ]
            }
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://ascendex.com/api/pro/v1/ticker?symbol=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonObject();

        BigDecimal open = klinesJson.get("open").getAsBigDecimal();
        BigDecimal close = klinesJson.get("close").getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", klinesJson.get("volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /barhist?symbol=ASD/USDT&interval=1
        {
            "code": 0,
            "data": [
                {
                    "data": {
                        "c": "0.05019",
                        "h": "0.05019",
                        "i": "1",
                        "l": "0.05019",
                        "o": "0.05019",
                        "ts": 1575409260000,
                        "v": "1612"},
                   "m": "bar",
                   "s": "ASD/USDT"},
                {
                    "data": {
                        "c": "0.05019",
                        "h": "0.05027",
                        "i": "1",
                        "l": "0.05017",
                        "o": "0.05017",
                        "ts": 1575409200000,
                        "v": "57242"
                        },
                   "m": "bar",
                   "s": "ASD/USDT"},
            ]
        }
        */

        String intervalResolution = (interval == Interval.INT_1MIN) ? "1" : (interval == Interval.INT_5MIN) ? "5"
                : (interval == Interval.INT_15MIN) ? "15" : (interval == Interval.INT_30MIN) ? "30" : (interval == Interval.INT_1HOUR) ? "60"
                : (interval == Interval.INT_2HOURS) ? "120" : (interval == Interval.INT_4HOURS) ? "240" : (interval == Interval.INT_6HOURS) ? "360"
                : (interval == Interval.INT_12HOURS) ? "720" : (interval == Interval.INT_1DAY) ? "1d" : (interval == Interval.INT_1WEEK) ? "1w" : "1m";

        JsonArray klinesJson = JsonParser
                .parseString(response("https://ascendex.com/api/pro/v1/barhist?symbol=" + symbol + "&interval=" + intervalResolution + "&n=300"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonObject().get("data").getAsJsonObject().get("o").getAsBigDecimal(),
                    e.getAsJsonObject().get("data").getAsJsonObject().get("h").getAsBigDecimal(),
                    e.getAsJsonObject().get("data").getAsJsonObject().get("l").getAsBigDecimal(),
                    e.getAsJsonObject().get("data").getAsJsonObject().get("c").getAsBigDecimal(),
                    e.getAsJsonObject().get("data").getAsJsonObject().get("v").getAsBigDecimal()));

        return list;
    }
}
