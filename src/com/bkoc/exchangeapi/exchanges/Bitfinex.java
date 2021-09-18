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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Bitfinex extends General { // https://api-pub.bitfinex.com/v2/
    public static List<String> getSymbols() throws IOException {
        /* GET conf/pub:list:pair:exchange
        [
            [
                "1INCH:USD",
                "1INCH:UST"
            ]
        ]
        */

        // Var olan tekli kriptoların listesini al BTC,ETH...
        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api-pub.bitfinex.com/v2/conf/pub:list:pair:exchange"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            for (JsonElement j : i.getAsJsonArray())
                symbolsList.add(j.getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker/Symbol
        [
            BID,
            BID_SIZE,
            ASK,
            ASK_SIZE,
            DAILY_CHANGE,
            DAILY_CHANGE_RELATIVE,
            LAST_PRICE,
            VOLUME,
            HIGH,
            LOW
        ]
        */

        JsonArray tickerJson = JsonParser
                .parseString(response("https://api-pub.bitfinex.com/v2/ticker/t" + symbol))
                .getAsJsonArray();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerJson.get(6).getAsBigDecimal());
        ticker.put("priceChange", tickerJson.get(4).getAsBigDecimal());
        ticker.put("priceChangePercent", tickerJson.get(5).getAsBigDecimal().multiply(BigDecimal.valueOf(100)));
        ticker.put("volume", tickerJson.get(7).getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET candles/trade:TimeFrame:Symbol/hist?limit=100
        [
            [
                MTS,
                OPEN,
                CLOSE,
                HIGH,
                LOW,
                VOLUME
            ],
            ...
        ]
        */

        String intervalStr = (interval == Interval.INT_1DAY) ? "1D" : (interval == Interval.INT_1WEEK) ? "7D" : interval.getValue();

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api-pub.bitfinex.com/v2/candles/trade:" + intervalStr + ":t" + symbol + "/hist?limit=300"))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(1).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(5).getAsBigDecimal()));
        }
        Collections.reverse(list);
        return list;
    }
}
