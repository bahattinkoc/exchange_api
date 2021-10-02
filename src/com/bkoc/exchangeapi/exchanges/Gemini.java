package com.bkoc.exchangeapi.exchanges;

import com.bkoc.exchangeapi.Candlestick;
import com.bkoc.exchangeapi.General;
import com.bkoc.exchangeapi.Interval;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Gemini extends General { // https://api.gemini.com
    public static List<String> getSymbols() throws IOException {
        /* GET /v1/symbols
        [
            "btcusd",
            "ethbtc",
            "ethusd",
            ..
        ]
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api.sandbox.gemini.com/v1/symbols")) // sandbox kullanma sebebi: iptal edilmişler dahil değil
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            symbolsList.add(i.getAsString().toUpperCase(Locale.ROOT));

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /v2/candles/:symbol/:time_frame
        [
            [
                1559755800000, time
                7781.6,        open
                7820.23,       high
                7776.56,       low
                7819.39,       close
                34.7624802159  volume
            ],
            [
                1559755800000,
                7781.6,
                7829.46,
                7776.56,
                7817.28,
                43.4228281059
            ],
            ...
        ]
        */

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://api.gemini.com/v2/candles/" + symbol.toLowerCase(Locale.ROOT) + "/1day"))
                .getAsJsonArray();

        JsonArray lastOne = klinesAsJsonArray.get(0).getAsJsonArray();

        BigDecimal open = lastOne.get(1).getAsBigDecimal();
        BigDecimal close = lastOne.get(4).getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", lastOne.get(5).getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /v2/candles/:symbol/:time_frame
        [
            [
             1559755800000, time
             7781.6,        open
             7820.23,       high
             7776.56,       low
             7819.39,       close
             34.7624802159  volume
            ],
            [1559755800000,
            7781.6,
            7829.46,
            7776.56,
            7817.28,
            43.4228281059],
            ...
        ]
        */

            //1m, 5m, 15m, 30m, 1hr, 6hr, 1day
            String intervalResolution = (interval == Interval.INT_1HOUR) ? "1hr" : (interval == Interval.INT_6HOURS) ? "6hr"
                    : (interval == Interval.INT_1DAY) ? "1day" : interval.getValue();

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.gemini.com/v2/candles/" + symbol.toLowerCase(Locale.ROOT) + "/" + intervalResolution))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonArray().get(1).getAsBigDecimal(), e.getAsJsonArray().get(2).getAsBigDecimal(), e.getAsJsonArray().get(3).getAsBigDecimal(), e.getAsJsonArray().get(4).getAsBigDecimal(), e.getAsJsonArray().get(5).getAsBigDecimal()));
            Collections.reverse(list);

            if (list.size() < 310)
                return list;
            else
                return list.subList(list.size() - 300, list.size());
        }
        catch (Exception e) { return null; }
    }
}
