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

public class Exmo extends General { // https://api.exmo.com/v1.1/
    public static List<String> getSymbols() throws IOException {
        /* GET /ticker
        {
          "BTC_USD": {
            "buy_price": "589.06",
            "sell_price": "592",
            "last_trade": "591.221",
            "high": "602.082",
            "low": "584.51011695",
            "avg": "591.14698808",
            "vol": "167.59763535",
            "vol_curr": "99095.17162071",
            "updated": 1470250973
          }
        }
        */

        JsonObject symbolsListJsonObj = JsonParser
                .parseString(response("https://api.exmo.com/v1.1/ticker"))
                .getAsJsonObject();

        List<String> symbolsList = new LinkedList<>();
        for (String i : symbolsListJsonObj.keySet())
            symbolsList.add(i);

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /candles_history?symbol=BTC_USD&resolution=30&from=1585556979&to=1585557979
        {
          "candles": [
            {
              "t": 1585557000000,
              "o": 6590.6164,
              "c": 6602.3624,
              "h": 6618.78965693,
              "l": 6579.054,
              "v": 6.932754980000013
            }
          ]
        }
        */

        long from = (System.currentTimeMillis() / 1000L) - (1440 * 2 * 60);
        long to = System.currentTimeMillis() / 1000L;

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.exmo.com/v1.1/candles_history?symbol=" + symbol + "&resolution=D&from=" + from + "&to=" + to))
                .getAsJsonObject().get("candles")
                .getAsJsonArray();

        BigDecimal open = klinesJson.get(klinesJson.size()-1).getAsJsonObject().get("o").getAsBigDecimal();
        BigDecimal close = klinesJson.get(klinesJson.size()-1).getAsJsonObject().get("c").getAsBigDecimal();
        BigDecimal volume = klinesJson.get(klinesJson.size()-1).getAsJsonObject().get("v").getAsBigDecimal();
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
        /* GET /candles_history?symbol=BTC_USD&resolution=30&from=1585556979&to=1585557979
        {
          "candles": [
            {
              "t": 1585557000000,
              "o": 6590.6164,
              "c": 6602.3624,
              "h": 6618.78965693,
              "l": 6579.054,
              "v": 6.932754980000013
            }
          ]
        }
        */

        //1, 5, 15, 30, 45, 60, 120, 180, 240, D, W, M
        String intervalResolution = (interval == Interval.INT_1MIN) ? "1" : (interval == Interval.INT_5MIN) ? "5"
                : (interval == Interval.INT_15MIN) ? "15" : (interval == Interval.INT_30MIN) ? "30" : (interval == Interval.INT_1HOUR) ? "60"
                : (interval == Interval.INT_2HOURS) ? "120" : (interval == Interval.INT_4HOURS) ? "240" : (interval == Interval.INT_1DAY) ? "D"
                : (interval == Interval.INT_1WEEK) ? "W" : "M";

        int intervalInt = (interval == Interval.INT_1DAY) ? 1440 : (interval == Interval.INT_1WEEK) ? 10080 : (interval == Interval.INT_1MONTH) ? 43200 : Integer.parseInt(intervalResolution);
        long from = (System.currentTimeMillis() / 1000L) - (intervalInt * 300 * 60);
        long to = System.currentTimeMillis() / 1000L;

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.exmo.com/v1.1/candles_history?symbol=" + symbol + "&resolution=" + intervalResolution + "&from=" + from + "&to=" + to))
                .getAsJsonObject().get("candles")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonObject().get("o").getAsBigDecimal().stripTrailingZeros(),
                    e.getAsJsonObject().get("h").getAsBigDecimal().stripTrailingZeros(),
                    e.getAsJsonObject().get("l").getAsBigDecimal().stripTrailingZeros(),
                    e.getAsJsonObject().get("c").getAsBigDecimal().stripTrailingZeros(),
                    e.getAsJsonObject().get("v").getAsBigDecimal().stripTrailingZeros()));

        return list;
    }
}
