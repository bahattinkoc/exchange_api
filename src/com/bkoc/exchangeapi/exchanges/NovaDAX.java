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

public class NovaDAX extends General { // https://api.novadax.com/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /common/symbols
        {
            "code": "A10000",
            "data": [
                {
                    "symbol": "BTC_BRL",
                    "baseCurrency": "BTC",
                    "quoteCurrency": "BRL",
                    "amountPrecision": 4,
                    "pricePrecision": 2,
                    "valuePrecision": 4,
                    "minOrderAmount": "0.001",
                    "minOrderValue": "5",
                },
                {
                    "symbol": "ETH_BRL",
                    "baseCurrency": "ETH",
                    "quoteCurrency": "BRL",
                    "amountPrecision": 4,
                    "pricePrecision": 2,
                    "valuePrecision": 4,
                    "minOrderAmount": "0.01",
                    "minOrderValue": "5"
                }
            ],
            "message": "Success"
        }
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.novadax.com/v1/common/symbols"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /market/ticker?symbol=FTT_USDT
        {
            "code": "A10000",
            "data": {
                "ask": "34708.15",
                "baseVolume24h": "34.08241488",
                "bid": "34621.74",
                "high24h": "35079.77",
                "lastPrice": "34669.81",
                "low24h": "34330.64",
                "open24h": "34492.08",
                "quoteVolume24h": "1182480.09502814",
                "symbol": "BTC_BRL",
                "timestamp": 1571112216346
            },
            "message": "Success"
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.novadax.com/v1/market/ticker?symbol=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonObject();

        BigDecimal open = klinesJson.get("open24h").getAsBigDecimal();
        BigDecimal close = klinesJson.get("lastPrice").getAsBigDecimal();
        BigDecimal volume = klinesJson.get("baseVolume24h").getAsBigDecimal();
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
        /* GET /market/kline/history?symbol=BTC_BRL&from=1567619280&to=1567620000&unit=ONE_MIN
        {
            "code": "A10000",
            "data": [
                {
                    "amount": 8.25709100,
                    "closePrice": 62553.20,
                    "count": 29,
                    "highPrice": 62592.87,
                    "lowPrice": 62553.20,
                    "openPrice": 62554.23,
                    "score": 1602501480,
                    "symbol": "BTC_BRL",
                    "vol": 516784.2504067500
                }
            ],
            "message": "Success"
        }
        */

            //ONE_MIN, FIVE_MIN, FIFTEEN_MIN, HALF_HOU, ONE_HOU, ONE_DAY, ONE_WEE, ONE_MON
            String intervalResolution = (interval == Interval.INT_1MIN) ? "ONE_MIN" : (interval == Interval.INT_5MIN) ? "FIVE_MIN"
                    : (interval == Interval.INT_15MIN) ? "FIFTEEN_MIN" : (interval == Interval.INT_30MIN) ? "HALF_HOU"
                    : (interval == Interval.INT_1HOUR) ? "ONE_HOU" : (interval == Interval.INT_1DAY) ? "ONE_DAY" : "ONE_WEE";

            int intervalInt = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_5MIN) ? 300
                    : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                    : (interval == Interval.INT_1DAY) ? 86400 : 7 * 86400;

            long from = (System.currentTimeMillis() / 1000L) - (intervalInt * 200);
            long to = System.currentTimeMillis() / 1000L;

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.novadax.com/v1/market/kline/history?symbol=" + symbol + "&from=" + from + "&to=" + to + "&unit=" + intervalResolution))
                    .getAsJsonObject().get("data")
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("openPrice").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("highPrice").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("lowPrice").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("closePrice").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("vol").getAsBigDecimal().stripTrailingZeros()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
