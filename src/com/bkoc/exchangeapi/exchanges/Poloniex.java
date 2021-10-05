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

public class Poloniex extends General { // https://poloniex.com/public
    public static List<String> getSymbols() throws IOException {
        /* GET ?command=returnTicker
        {
          BTC_BCN: {
            id: 7,
            last: '0.00000024',
            lowestAsk: '0.00000025',
            highestBid: '0.00000024',
            percentChange: '0.04347826',
            baseVolume: '58.19056621',
            quoteVolume: '245399098.35236773',
            isFrozen: '0',
            postOnly: '0',
            high24hr: '0.00000025',
            low24hr: '0.00000022'
          },
          ...
        }
        */

        JsonObject symbolsListJsonArray = JsonParser
                .parseString(response("https://poloniex.com/public?command=returnTicker"))
                .getAsJsonObject();

        List<String> symbolsList = new LinkedList<>();
        for (String i : symbolsListJsonArray.keySet())
            if (symbolsListJsonArray.get(i).getAsJsonObject().get("isFrozen").getAsString().equals("0"))
                symbolsList.add(i);

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET ?command=returnTicker
        {
          BTC_BCN: {
            id: 7,
            last: '0.00000024',
            lowestAsk: '0.00000025',
            highestBid: '0.00000024',
            percentChange: '0.04347826',
            baseVolume: '58.19056621',
            quoteVolume: '245399098.35236773',
            isFrozen: '0',
            postOnly: '0',
            high24hr: '0.00000025',
            low24hr: '0.00000022'
          },
          ...
        }
        */

        JsonObject tickerObj = JsonParser
                .parseString(response("https://poloniex.com/public?command=returnTicker"))
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();

        BigDecimal last = tickerObj.get(symbol).getAsJsonObject().get("last").getAsBigDecimal();
        BigDecimal percentChange = tickerObj.get(symbol).getAsJsonObject().get("percentChange").getAsBigDecimal().multiply(BigDecimal.valueOf(100));
        BigDecimal open = last.multiply(BigDecimal.valueOf(100)).divide(percentChange.add(BigDecimal.valueOf(100)), 8, RoundingMode.HALF_UP);

        ticker.put("lastPrice", last);
        ticker.put("priceChange", last.subtract(open));
        ticker.put("priceChangePercent", percentChange);
        ticker.put("volume", tickerObj.get(symbol).getAsJsonObject().get("baseVolume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET ?command=returnChartData&currencyPair=BTC_XMR&period=14400
        [
            {
                date: 1539864000,
                high: 0.03149999,
                low: 0.031,
                open: 0.03144307,
                close: 0.03124064,
                volume: 64.36480422,
                quoteVolume: 2055.56810329,
                weightedAverage: 0.03131241
            },
            {
                date: 1539878400,
                high: 0.03129379,
                low: 0.03095999,
                open: 0.03124064,
                close: 0.03108499,
                volume: 50.21821153,
                quoteVolume: 1615.31999527,
                weightedAverage: 0.0310887
            },
            ...
        ]
        */

            //300, 900, 1800, 7200, 14400, and 86400 saniye
            int intervalResolution = (interval == Interval.INT_5MIN) ? 300 : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800
                    : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : 86400;

            long start = (System.currentTimeMillis() / 1000L) - (200 * intervalResolution);

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://poloniex.com/public?command=returnChartData&currencyPair=" + symbol + "&start=" + start + "&end=" + (System.currentTimeMillis() / 1000L) + "&period=" + intervalResolution))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
