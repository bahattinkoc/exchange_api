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

public class MEXC extends General { // https://www.mexc.com/open/api/v2/market/
    public static List<String> getSymbols() throws IOException {
        /* GET /symbols
        {
          "code": 200,
          "data": [
            {
              "symbol": "QTM_USDT",
              "state": "ENABLED",
              "price_scale": 4,
              "quantity_scale": 4,
              "min_amount": "5",
              "max_amount": "10000000",
              "maker_fee_rate": "0.001",
              "taker_fee_rate": "0.001"
            }
          ]
        }
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://www.mexc.com/open/api/v2/market/symbols"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("state").getAsString().equals("ENABLED"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /ticker?symbol=KIN_USDT
        {
            "code": 200,
            "data": [
                {
                     "symbol": "ETH_USDT",
                     "volume": "0",
                     "high": "182.4117576",
                     "low": "182.4117576",
                     "bid": "182.0017985",
                     "ask": "183.1983186",
                     "open": "182.4117576",
                     "last": "182.4117576",
                     "time": 1574668200000,
                     "change_rate": "0.00027307"
                }
            ]
        }
        */

        JsonObject symbolObj = JsonParser
                .parseString(response("https://www.mexc.com/open/api/v2/market/ticker?symbol=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", symbolObj.get("last").getAsBigDecimal());
        ticker.put("priceChange", symbolObj.get("last").getAsBigDecimal().subtract(symbolObj.get("open").getAsBigDecimal()));
        ticker.put("priceChangePercent", symbolObj.get("change_rate").getAsBigDecimal().multiply(BigDecimal.valueOf(100)));
        ticker.put("volume", symbolObj.get("volume").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /kline?symbol=KIN_USDT&interval=1m&limit=300
        {
            "code": 200,
            "data": [
                [
                    1557728040,    //timestamp in seconds
                    "7054.7",      //open
                    "7056.26",     //close
                    "7056.29",     //high
                    "7054.16",     //low
                    "9.817734",    //vol
                    "6926.521"     //amount
                ],
                ...
            ]
        }
        */

        //1m, 5m, 15m, 30m, 60m. of hour: 4h, of day: 1d, of month: 1M
        String intervalPeriod = (interval == Interval.INT_1MIN) ? "1m"
                : (interval == Interval.INT_5MIN) ? "5m" : (interval == Interval.INT_15MIN) ? "15m"
                : (interval == Interval.INT_30MIN) ? "30m" : (interval == Interval.INT_1HOUR) ? "60m"
                : (interval == Interval.INT_4HOURS) ? "4h" : (interval == Interval.INT_1DAY) ? "1d" : "1M";

        JsonArray klinesJson = JsonParser
                .parseString(response("https://www.mexc.com/open/api/v2/market/kline?symbol=" + symbol + "&interval=" + intervalPeriod + "&limit=300"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonArray().get(1).getAsBigDecimal(),
                    e.getAsJsonArray().get(3).getAsBigDecimal(),
                    e.getAsJsonArray().get(4).getAsBigDecimal(),
                    e.getAsJsonArray().get(2).getAsBigDecimal(),
                    e.getAsJsonArray().get(5).getAsBigDecimal()));

        return list;
    }
}
