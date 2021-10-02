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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Bitpanda extends General { // https://api.exchange.bitpanda.com/public/v1
    public static List<String> getSymbols() throws IOException {
        /* GET /market-ticker
        [
          {
            "instrument_code": "BTC_EUR",
            "sequence": 1337,
            "state": "ACTIVE",
            "is_frozen": 0,
            "quote_volume": "123456.78",
            "base_volume": "987.654321",
            "last_price": "9214.23",
            "best_bid": "9199.2345",
            "best_ask": "9331.9999",
            "price_change": "1200.30",
            "price_change_percentage": "42.21",
            "high": "10142.21",
            "low": "9213.89"
          }
        ]
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.exchange.bitpanda.com/public/v1/market-ticker/"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("state").getAsString().equals("ACTIVE") && i.getAsJsonObject().get("is_frozen").getAsString().equals("0"))
                symbolsList.add(i.getAsJsonObject().get("instrument_code").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /market-ticker
        [
          {
            "instrument_code": "BTC_EUR",
            "sequence": 1337,
            "state": "ACTIVE",
            "is_frozen": 0,
            "quote_volume": "123456.78",
            "base_volume": "987.654321",
            "last_price": "9214.23",
            "best_bid": "9199.2345",
            "best_ask": "9331.9999",
            "price_change": "1200.30",
            "price_change_percentage": "42.21",
            "high": "10142.21",
            "low": "9213.89"
          }
        ]
        */

        JsonObject symbolObj = JsonParser
                .parseString(response("https://api.exchange.bitpanda.com/public/v1/market-ticker/" + symbol))
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", symbolObj.get("last_price").getAsBigDecimal());
        ticker.put("priceChange", symbolObj.get("price_change").getAsBigDecimal());
        ticker.put("priceChangePercent", symbolObj.get("price_change_percentage").getAsBigDecimal());
        ticker.put("volume", symbolObj.get("base_volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /candlesticks/{instrument_code}?unit=HOURS&period=1&from=2019-10-03T04%3A59%3A59.999Z&to=2019-10-03T07%3A59%3A59.999Z
        [
          {
            "last_sequence": 49838,
            "instrument_code": "BTC_EUR",
            "granularity": {
              "unit": "HOURS",
              "period": 1
            },
            "high": "7658.87",
            "low": "7633.86",
            "open": "7634.26",
            "close": "7633.86",
            "total_amount": "0.27459",
            "volume": "2097.5213865",
            "time": "2019-10-03T04:59:59.999Z"
          },
          ...
        ]
        */

            //1, 5, 15, 30 MINUTES & 1, 4 HOURS & 1 DAYS & 1 WEEKS & 1 MONTHS.
            String intervalPeriod = (interval == Interval.INT_1MIN) ? "MINUTES&period=1"
                    : (interval == Interval.INT_5MIN) ? "MINUTES&period=5" : (interval == Interval.INT_15MIN) ? "MINUTES&period=15"
                    : (interval == Interval.INT_30MIN) ? "MINUTES&period=30" : (interval == Interval.INT_1HOUR) ? "HOURS&period=1"
                    : (interval == Interval.INT_4HOURS) ? "HOURS&period=4" : "DAYS&period=1";

            int internalSeconds = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_5MIN) ? 300
                    : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                    : (interval == Interval.INT_4HOURS) ? 14400 : 86400;

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS&");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String end_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            calendar.setTimeInMillis((System.currentTimeMillis()) - (300L * internalSeconds * 1000L));
            String start_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.exchange.bitpanda.com/public/v1/" +
                            "candlesticks/" + symbol + "?unit=" + intervalPeriod + "&from=" + start_time + "&to=" + end_time))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(),
                        e.getAsJsonObject().get("high").getAsBigDecimal(),
                        e.getAsJsonObject().get("low").getAsBigDecimal(),
                        e.getAsJsonObject().get("close").getAsBigDecimal(),
                        e.getAsJsonObject().get("total_amount").getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
