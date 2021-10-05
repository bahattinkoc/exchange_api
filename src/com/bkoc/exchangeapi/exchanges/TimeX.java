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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeX extends General { // https://plasma-relay-backend.timex.io/public/
    public static List<String> getSymbols() throws IOException {
        /* GET /markets
        [
          {
            "alternativeCurrency": "ETH",
            "alternativeTokenAddress": "0xe140faabaf512a4516284073ca6b26fd0cbeba4c",
            "baseCurrency": "TIME",
            "baseMinSize": "0.01",
            "baseTokenAddress": "0x086d0b67d7e3dbccdc8df488ffa7e102deea8f43",
            "feeCurrency": "ETH",
            "feeTokenAddress": "0xe140faabaf512a4516284073ca6b26fd0cbeba4c",
            "locked": false,
            "makerAltFee": "-0.025",
            "makerFee": "-0.025",
            "name": "TIME/ETH",
            "promotional": false,
            "quantityIncrement": "0.01",
            "quoteCurrency": "ETH",
            "quoteMinSize": "0.01",
            "quoteTokenAddress": "0xe140faabaf512a4516284073ca6b26fd0cbeba4c",
            "showOnLanding": true,
            "sortOrder": 1,
            "symbol": "TIMEETH",
            "takerAltFee": "0.075",
            "takerFee": "0.075",
            "tickSize": "0.000001"
          }
        ]
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://plasma-relay-backend.timex.io/public/markets"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("locked").getAsString().equals("false"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /tickers24?market=EOSUSDT
        [
          {
            "ask": 0.017,
            "bid": 0.016,
            "high": 0.019,
            "last": 0.017,
            "low": 0.015,
            "market": "TIME/ETH",
            "open": 0.016,
            "period": "H1",
            "timestamp": "2018-12-14T20:50:36.134Z",
            "volume": 4.57,
            "volumeQuote": 0.07312
          }
        ]
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://plasma-relay-backend.timex.io/public/tickers24?market=" + symbol))
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        BigDecimal open = klinesJson.get("open").getAsBigDecimal();
        BigDecimal close = klinesJson.get("last").getAsBigDecimal();
        BigDecimal volume = klinesJson.get("volume").getAsBigDecimal();
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
        /* GET /candles?from=2021-01-01T00%3A00%3A00Z&market=TIMEETH&period=I1&till=2021-02-01T00%3A00%3A00Z&useCache=true
        [
          {
            "close": 0.017,
            "high": 0.019,
            "low": 0.015,
            "open": 0.016,
            "timestamp": "2018-12-14T20:50:36.134Z",
            "volume": 4.57,
            "volumeQuote": 0.07312
          }
        ]
        */

            //I1, I5, I15, I30, H1, D1, W1
            String internalResolution = (interval == Interval.INT_1MIN) ? "I1" : (interval == Interval.INT_5MIN) ? "I5"
                    : (interval == Interval.INT_15MIN) ? "I15" : (interval == Interval.INT_30MIN) ? "I30" : (interval == Interval.INT_1HOUR) ? "H1"
                    : (interval == Interval.INT_1DAY) ? "D1" : "W1";

            int internalSeconds = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_5MIN) ? 300
                    : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                    : (interval == Interval.INT_1DAY) ? 86400 : 7 * 86400;

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS&");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String end_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            calendar.setTimeInMillis((System.currentTimeMillis()) - (200L * internalSeconds * 1000L));
            String start_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://plasma-relay-backend.timex.io/public/candles?from=" + start_time + "&market=" + symbol + "&period=" + internalResolution + "&till=" + end_time))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(),
                        e.getAsJsonObject().get("high").getAsBigDecimal(),
                        e.getAsJsonObject().get("low").getAsBigDecimal(),
                        e.getAsJsonObject().get("close").getAsBigDecimal(),
                        e.getAsJsonObject().get("volume").getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
