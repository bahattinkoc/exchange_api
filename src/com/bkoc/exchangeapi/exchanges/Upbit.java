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

public class Upbit extends General { // https://api.upbit.com/v1/
    public static List<String> getSymbols() throws IOException {
        /* GET /market/all
        [
            {
                "market":"KRW-BTC",
                "korean_name":"비트코인",
                "english_name":"Bitcoin"
            },
            ..
        ]
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.upbit.com/v1/market/all"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            symbolsList.add(i.getAsJsonObject().get("market").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker?markets=KRW-BTC
        [
            {
                "market":"KRW-BTC",
                "trade_date":"20210921",
                "trade_time":"123619",
                "trade_date_kst":"20210921",
                "trade_time_kst":"213619",
                "trade_timestamp":1632227779000,
                "opening_price":53801000.00000000,
                "high_price":54400000.0,
                "low_price":51012000.00000000,
                "trade_price":52868000.0,
                "prev_closing_price":53801000.00000000,
                "change":"FALL",
                "change_price":933000.00000000,
                "change_rate":0.0173416851,
                "signed_change_price":-933000.00000000,
                "signed_change_rate":-0.0173416851,
                "trade_volume":0.00182599,
                "acc_trade_price":350468975395.470150000,
                "acc_trade_price_24h":559916576815.77812000,
                "acc_trade_volume":6617.22958101,
                "acc_trade_volume_24h":10458.75235332,
                "highest_52_week_price":81994000.00000000,
                "highest_52_week_date":"2021-04-14",
                "lowest_52_week_price":11912000.00000000,
                "lowest_52_week_date":"2020-09-24",
                "timestamp":1632227779175
            }
        ]
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.upbit.com/v1/ticker?markets=" + symbol))
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", klinesJson.get("trade_price").getAsBigDecimal());
        ticker.put("priceChange", klinesJson.get("signed_change_price").getAsBigDecimal());
        ticker.put("priceChangePercent", klinesJson.get("signed_change_rate").getAsBigDecimal().multiply(BigDecimal.valueOf(100)));
        ticker.put("volume", klinesJson.get("acc_trade_volume_24h").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /candles/minutes/5?market=KRW-BTC&count=3
        [
           {
              "market":"KRW-BTC",
              "candle_date_time_utc":"2021-09-21T13:55:00",
              "candle_date_time_kst":"2021-09-21T22:55:00",
              "opening_price":52691000.00000000,
              "high_price":52724000.00000000,
              "low_price":52690000.00000000,
              "trade_price":52693000.00000000,
              "timestamp":1632232745604,
              "candle_acc_trade_price":634550409.81417000,
              "candle_acc_trade_volume":12.03986118,
              "unit":5
           }
        ]
        */

        //Bunların hepsini yapabiliyor
        String intervalResolution = (interval == Interval.INT_1MIN) ? "minutes/1" : (interval == Interval.INT_3MIN) ? "minutes/3"
                : (interval == Interval.INT_5MIN) ? "minutes/5" : (interval == Interval.INT_15MIN) ? "minutes/15"
                : (interval == Interval.INT_30MIN) ? "minutes/30" : (interval == Interval.INT_1HOUR) ? "minutes/60"
                : (interval == Interval.INT_4HOURS) ? "minutes/240" : (interval == Interval.INT_1DAY) ? "days"
                : (interval == Interval.INT_1WEEK) ? "weeks" : "months";

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.upbit.com/v1/candles/" + intervalResolution + "?market=" + symbol + "&count=200"))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonObject().get("opening_price").getAsBigDecimal(),
                    e.getAsJsonObject().get("high_price").getAsBigDecimal(),
                    e.getAsJsonObject().get("low_price").getAsBigDecimal(),
                    e.getAsJsonObject().get("trade_price").getAsBigDecimal(),
                    e.getAsJsonObject().get("candle_acc_trade_volume").getAsBigDecimal()));
        Collections.reverse(list);
        return list;
    }
}
