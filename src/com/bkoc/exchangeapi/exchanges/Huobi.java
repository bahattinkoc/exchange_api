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

public class Huobi extends General { // https://api.huobi.pro/

    public static List<String> getSymbols() throws IOException {
        /* GET /v1/common/symbols
        {
            "status": "ok",
            "data": [
                {
                    "base-currency": "ekt",
                    "quote-currency": "usdt",
                    "price-precision": 6,
                    "amount-precision": 2,
                    "symbol-partition": "innovation",
                    "symbol": "ektusdt",
                    "state": "online",
                    "value-precision": 8,
                    "min-order-amt": 0.1,
                    "max-order-amt": 10000000,
                    "min-order-value": 5,
                    "limit-order-min-order-amt": 0.1,
                    "limit-order-max-order-amt": 10000000,
                    "limit-order-max-buy-amt": 10000000,
                    "limit-order-max-sell-amt": 10000000,
                    "sell-market-min-order-amt": 0.1,
                    "sell-market-max-order-amt": 1000000,
                    "buy-market-max-order-value": 200000,
                    "api-trading": "enabled"
                },
                {
                    "base-currency": "snx",
                    "quote-currency": "husd",
                    "price-precision": 4,
                    "amount-precision": 2,
                    "symbol-partition": "innovation",
                    "symbol": "snxhusd",
                    "state": "online",
                    "value-precision": 8,
                    "min-order-amt": 0.01,
                    "max-order-amt": 250000,
                    "min-order-value": 5,
                    "limit-order-min-order-amt": 0.01,
                    "limit-order-max-order-amt": 250000,
                    "limit-order-max-buy-amt": 250000,
                    "limit-order-max-sell-amt": 250000,
                    "sell-market-min-order-amt": 0.01,
                    "sell-market-max-order-amt": 25000,
                    "buy-market-max-order-value": 100000,
                    "api-trading": "enabled"
                }
            ]
        }
        */

        List<String> list = new LinkedList<>();
        JsonArray symbolsList = JsonParser
                .parseString(response("https://api.huobi.pro/v1/common/symbols"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        for (JsonElement i : symbolsList)
            if (i.getAsJsonObject().get("state").getAsString().equals("online"))
                list.add(i.getAsJsonObject().get("symbol").getAsString().toUpperCase(Locale.ROOT));

        return list;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /market/detail/
        {
            "ch": "market.btcusdt.detail",
            "status": "ok",
            "ts": 1629795484817,
            "tick": {
                "id": 272164011416,
                "low": 48767.0,
                "high": 50500.0,
                "open": 50266.89,
                "close": 49728.71,
                "vol": 6.010379336834868E8,
                "amount": 12110.642402972368,
                "version": 272164011416,
                "count": 420452
            }
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://api.huobi.pro/market/detail?symbol=" + symbol.toLowerCase(Locale.ROOT)))
                .getAsJsonObject()
                .get("tick")
                .getAsJsonObject();

        BigDecimal open = tickerObj.get("open").getAsBigDecimal();
        BigDecimal close = tickerObj.get("close").getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", tickerObj.get("vol").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /market/history/kline
        {
            "ch": "market.btcusdt.kline.5min",
            "status": "ok",
            "ts": 1629769247172,
            "data": [
                {
                    "id": 1629769200,
                    "open": 49056.37,
                    "close": 49025.51,
                    "low": 49022.86,
                    "high": 49056.38,
                    "amount": 3.946281917950917,
                    "vol": 193489.67275732,
                    "count": 196
                },
                {
                    "id": 1629768900,
                    "open": 48994.61,
                    "close": 49056.37,
                    "low": 48966.72,
                    "high": 49072.46,
                    "amount": 30.72223099519689,
                    "vol": 1505870.732227976,
                    "count": 1504
                }
            ]
        }
        */

            //1min, 5min, 15min, 30min, 60min, 4hour, 1day, 1week, 1mon, 1year
            String intervalStr = (interval == Interval.INT_1MIN) ? "1min" : (interval == Interval.INT_5MIN) ? "5min" : (interval == Interval.INT_15MIN) ? "15min"
                    : (interval == Interval.INT_30MIN) ? "30min" : (interval == Interval.INT_1HOUR) ? "60min" : (interval == Interval.INT_4HOURS) ? "4hour"
                    : (interval == Interval.INT_1DAY) ? "1day" : "1week";

            JsonArray klinesAsJsonArray = JsonParser
                    .parseString(response("https://api.huobi.pro/market/history/kline?period=" + intervalStr + "&size=200&symbol=" + symbol.toLowerCase(Locale.ROOT)))
                    .getAsJsonObject().get("data")
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesAsJsonArray) {
                JsonObject obj = e.getAsJsonObject();
                list.add(new Candlestick(obj.get("open").getAsBigDecimal(), obj.get("high").getAsBigDecimal(), obj.get("low").getAsBigDecimal(), obj.get("close").getAsBigDecimal(), obj.get("vol").getAsBigDecimal()));
            }
            Collections.reverse(list);
            return list;
        }
        catch (Exception e) { return null; }
    }
}
