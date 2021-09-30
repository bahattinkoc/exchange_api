package com.bkoc.exchangeapi.exchanges;

import com.bkoc.exchangeapi.Candlestick;
import com.bkoc.exchangeapi.General;
import com.bkoc.exchangeapi.Interval;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FTX extends General { //https://ftx.com/api/
    public enum Permissions{
        spot,
        future
    }

    public static List<String> getSymbols() throws IOException {
        /*  GET /markets
        {
          "success": true,
          "result": [
            {
              "name": "BTC-0628",
              "baseCurrency": null,
              "quoteCurrency": null,
              "quoteVolume24h": 28914.76,
              "change1h": 0.012,
              "change24h": 0.0299,
              "changeBod": 0.0156,
              "highLeverageFeeExempt": false,
              "minProvideSize": 0.001,
              "type": "future",
              "underlying": "BTC",
              "enabled": true,
              "ask": 3949.25,
              "bid": 3949,
              "last": 10579.52,
              "postOnly": false,
              "price": 10579.52,
              "priceIncrement": 0.25,
              "sizeIncrement": 0.0001,
              "restricted": false,
              "volumeUsd24h": 28914.76
            }
          ]
        }*/

        JsonArray symbolsList = JsonParser
                .parseString(response("https://ftx.com/api/markets"))
                .getAsJsonObject().get("result")
                .getAsJsonArray();
        List<String> list = new LinkedList<>();
        for (JsonElement x : symbolsList){
            JsonObject object = x.getAsJsonObject();
            list.add(object.get("name").getAsString());
        }

        return list;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /markets
        {
          "success": true,
          "result": [
            {
              "name": "BTC-0628",
              "baseCurrency": null,
              "quoteCurrency": null,
              "quoteVolume24h": 28914.76,
              "change1h": 0.012,
              "change24h": 0.0299,
              "changeBod": 0.0156,
              "highLeverageFeeExempt": false,
              "minProvideSize": 0.001,
              "type": "future",
              "underlying": "BTC",
              "enabled": true,
              "ask": 3949.25,
              "bid": 3949,
              "last": 10579.52,
              "postOnly": false,
              "price": 10579.52,
              "priceIncrement": 0.25,
              "sizeIncrement": 0.0001,
              "restricted": false,
              "volumeUsd24h": 28914.76
            }
          ]
        }*/

//        NumberFormat.getInstance(Locale.CANADA).format(tickerList.get("quoteVolume24h").getAsBigDecimal())
//        or
//        DecimalFormat df = new DecimalFormat("#,###.00");
//        System.out.println(df.format(new BigDecimal(123456123456.78)));
//        123,456,123,456.78

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://ftx.com/api/markets/" + symbol + "/candles?resolution=86400"))
                .getAsJsonObject()
                .get("result")
                .getAsJsonArray();

        BigDecimal open = klinesAsJsonArray.get(klinesAsJsonArray.size()-1).getAsJsonObject().get("open").getAsBigDecimal();
        BigDecimal close = klinesAsJsonArray.get(klinesAsJsonArray.size()-1).getAsJsonObject().get("close").getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", klinesAsJsonArray.get(klinesAsJsonArray.size()-1).getAsJsonObject().get("volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /markets/{market_name}/candles?resolution={resolution}&start_time={start_time}&end_time={end_time}
        {
          "success": true,
          "result": [
            {
              "close": 11055.25,
              "high": 11089.0,
              "low": 11043.5,
              "open": 11059.25,
              "startTime": "2019-06-24T17:15:00+00:00",
              "volume": null
            }
          ]
        }*/

        //Hepsini yapıyor
        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180: (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ?  21600
                : (interval == Interval.INT_8HOURS) ? 28800 : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ?  86400
                : (interval == Interval.INT_3DAYS) ? 3*86400 : (interval == Interval.INT_1WEEK) ? 7*86400 : 30*86400;

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://ftx.com/api/markets/" + symbol + "/candles?resolution=" + intervalResolution))
                .getAsJsonObject().get("result")
                .getAsJsonArray();
        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesAsJsonArray) {
            JsonObject obj = e.getAsJsonObject();
            list.add(new Candlestick(obj.get("open").getAsBigDecimal(), obj.get("high").getAsBigDecimal(), obj.get("low").getAsBigDecimal(), obj.get("close").getAsBigDecimal(), obj.get("volume").getAsBigDecimal()));
        }

        return list;
    }
}
