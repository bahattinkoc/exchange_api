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

public class Kucoin extends General { // https://api.kucoin.com
    public static List<String> getSymbols() throws IOException {
        /* GET /api/v1/symbols
        [
          {
            "symbol": "XLM-USDT",
            "name": "XLM-USDT",
            "baseCurrency": "XLM",
            "quoteCurrency": "USDT",
            "feeCurrency": "USDT",
            "market": "USDS",
            "baseMinSize": "0.1",
            "quoteMinSize": "0.01",
            "baseMaxSize": "10000000000",
            "quoteMaxSize": "99999999",
            "baseIncrement": "0.0001",
            "quoteIncrement": "0.000001",
            "priceIncrement": "0.000001",
            "priceLimitRate": "0.1",
            "isMarginEnabled": true,
            "enableTrading": true
          },
          {
            "symbol": "VET-USDT",
            "name": "VET-USDT",
            "baseCurrency": "VET",
            "quoteCurrency": "USDT",
            "feeCurrency": "USDT",
            "market": "USDS",
            "baseMinSize": "10",
            "quoteMinSize": "0.01",
            "baseMaxSize": "10000000000",
            "quoteMaxSize": "99999999",
            "baseIncrement": "0.0001",
            "quoteIncrement": "0.000001",
            "priceIncrement": "0.0000001",
            "priceLimitRate": "0.1",
            "isMarginEnabled": true,
            "enableTrading": true
          }
        ]
        */

        List<String> list = new LinkedList<>();
        JsonArray symbolsList = JsonParser
                .parseString(response("https://api.kucoin.com/api/v1/symbols"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        for (JsonElement i : symbolsList)
            if (i.getAsJsonObject().get("enableTrading").getAsString().equals("true"))
                list.add(i.getAsJsonObject().get("symbol").getAsString());

        return list;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /api/v1/market/stats
        {
            "time": 1602832092060,  // time
            "symbol": "BTC-USDT",   // symbol
            "buy": "11328.9",   // bestAsk
            "sell": "11329",    // bestBid
            "changeRate": "-0.0055",    // 24h change rate
            "changePrice": "-63.6", // 24h change price
            "high": "11610",    // 24h highest price
            "low": "11200", // 24h lowest price
            "vol": "2282.70993217", // 24h volume，the aggregated trading volume in BTC
            "volValue": "25984946.157790431",   // 24h total, the trading volume in quote currency of last 24 hours
            "last": "11328.9",  // last price
            "averagePrice": "11360.66065903",   // 24h average transaction price yesterday
            "takerFeeRate": "0.001",    // Basic Taker Fee
            "makerFeeRate": "0.001",    // Basic Maker Fee
            "takerCoefficient": "1",    // Taker Fee Coefficient
            "makerCoefficient": "1" // Maker Fee Coefficient
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://api.kucoin.com/api/v1/market/stats?symbol=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerObj.get("last").getAsBigDecimal());
        ticker.put("priceChange", tickerObj.get("changePrice").getAsBigDecimal());
        ticker.put("priceChangePercent", tickerObj.get("changeRate").getAsBigDecimal().multiply(BigDecimal.valueOf(100)));
        ticker.put("volume", tickerObj.get("vol").getAsBigDecimal());

        return ticker;
    }
    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /api/v1/market/candles
        [
            [
                "1545904980",             //Start time of the candle cycle
                "0.058",                  //opening price
                "0.049",                  //closing price
                "0.058",                  //highest price
                "0.049",                  //lowest price
                "0.018",                  //Transaction volume
                "0.000945"                //Transaction amount
            ],
            [
                "1545904920",
                "0.058",
                "0.072",
                "0.072",
                "0.058",
                "0.103",
                "0.006986"
            ]
        ]
        */
        String intervalStr = (interval == Interval.INT_1MIN) ? "1min" : (interval == Interval.INT_5MIN) ? "5min" : (interval == Interval.INT_15MIN) ? "15min"
                : (interval == Interval.INT_30MIN) ? "30min" : (interval == Interval.INT_1HOUR) ? "1hour" : (interval == Interval.INT_4HOURS) ? "4hour"
                : (interval == Interval.INT_1DAY) ? "1day" : "1week";

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://api.kucoin.com/api/v1/market/candles?symbol=" + symbol + "&type=" + intervalStr))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesAsJsonArray) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(1).getAsBigDecimal().setScale(2, RoundingMode.HALF_UP), obj.get(3).getAsBigDecimal().setScale(2, RoundingMode.HALF_UP), obj.get(4).getAsBigDecimal().setScale(2, RoundingMode.HALF_UP), obj.get(2).getAsBigDecimal().setScale(2, RoundingMode.HALF_UP), obj.get(5).getAsBigDecimal()));
        }
        Collections.reverse(list);
        return list;
    }
}