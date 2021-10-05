package com.bkoc.exchangeapi.exchanges;

import com.bkoc.exchangeapi.Candlestick;
import com.bkoc.exchangeapi.General;
import com.bkoc.exchangeapi.Interval;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class Probit extends General { // https://api.probit.com/api/exchange/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /market
        {
          data: [
            {
              "id":"BCH-BTC",
              "base_currency_id":"BCH",
              "quote_currency_id":"BTC",
              "min_price":"0.00000001",
              "max_price":"9999999999999999",
              "price_increment":"0.00000001",
              "min_quantity":"0.00000001",
              "max_quantity":"9999999999999999",
              "quantity_precision":8,
              "min_cost":"0",
              "max_cost":"9999999999999999",
              "cost_precision": 8
            }
          ]
        }
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.probit.com/api/exchange/v1/market"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("closed").getAsString().equals("false"))
                symbolsList.add(i.getAsJsonObject().get("id").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker?market_ids=BTC-USDT,ETH-BTC
        {
          "data": [
            {
              "last": "3252.4",
              "low": "3235",
              "high": "3273.8",
              "change": "-23.4",
              "base_volume": "0.16690818",
              "quote_volume": "543.142095541",
              "market_id": "BTC-USDT",
              "time": "2018-12-17T06:49:08.000Z"
            },
            {
              "last": "0.02629698",
              "low": "0.02620186",
              "high": "0.02661865",
              "change": "-0.00018023",
              "base_volume": "11.25596565",
              "quote_volume": "0.29703323919717",
              "market_id": "ETH-BTC",
              "time": "2018-12-17T06:05:23.000Z"
            }
          ]
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.probit.com/api/exchange/v1/ticker?market_ids=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        BigDecimal change = klinesJson.get("change").getAsBigDecimal();
        BigDecimal open = klinesJson.get("last").getAsBigDecimal().add(change);
        BigDecimal close = klinesJson.get("last").getAsBigDecimal();
        BigDecimal percent = change.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", change);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", klinesJson.get("base_volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /candle?market_ids=BCH-BTC&interval=1m&limit=2&sort=desc&start_time=2018-08-24T08:00:18.054Z&end_time=2019-08-24T08:00:18.054Z
        {
           "data":[
              {
                 "market_id":"BCH-BTC",
                 "open":"0.030221",
                 "close":"0.030221",
                 "low":"0.030221",
                 "high":"0.030221",
                 "base_volume":"0.1684366",
                 "quote_volume":"0.0050903224886",
                 "start_time":"2019-08-24T07:55:00.000Z",
                 "end_time":"2019-08-24T07:56:00.000Z"
              }
           ]
        }
        */

            //Bunların hepsini yapabiliyor
            String intervalResolution = (interval == Interval.INT_1MIN) ? "1m" : (interval == Interval.INT_3MIN) ? "3m"
                    : (interval == Interval.INT_5MIN) ? "5m" : (interval == Interval.INT_15MIN) ? "15m"
                    : (interval == Interval.INT_30MIN) ? "30m" : (interval == Interval.INT_1HOUR) ? "1h"
                    : (interval == Interval.INT_4HOURS) ? "4h" : (interval == Interval.INT_6HOURS) ? "6h" : "12h";

            int internalSeconds = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180 : (interval == Interval.INT_5MIN) ? 300
                    : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                    : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ? 21600 : 43200;

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS&");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String end_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            calendar.setTimeInMillis((System.currentTimeMillis()) - (200L * internalSeconds * 1000L));
            String start_time = dateFormatter.format(calendar.getTime()).replace("_", "T").replace("&", "Z");

            JsonArray klinesJson = JsonParser
                    .parseString(response("https://api.probit.com/api/exchange/v1/candle?market_ids=" + symbol +
                            "&interval=" + intervalResolution + "&limit=300&sort=desc" +
                            "&start_time=" + start_time +
                            "&end_time=" + end_time))
                    .getAsJsonObject().get("data")
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(),
                        e.getAsJsonObject().get("high").getAsBigDecimal(),
                        e.getAsJsonObject().get("low").getAsBigDecimal(),
                        e.getAsJsonObject().get("close").getAsBigDecimal(),
                        e.getAsJsonObject().get("base_volume").getAsBigDecimal()));

            return list;
        }
        catch (Exception e) { return null; }
    }
}
