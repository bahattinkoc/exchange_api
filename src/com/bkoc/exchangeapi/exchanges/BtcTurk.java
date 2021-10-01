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

public class BtcTurk extends General { // https://api.btcturk.com/api/
    public static List<String> getSymbols() throws IOException {
        /* GET v2/server/exchangeinfo
        {
            "data":{
                "timeZone":"UTC",
                "serverTime":1632062929298,
                "symbols":[
                    {
                        "id":1,
                        "name":"BTCTRY",
                        "nameNormalized":"BTC_TRY",
                        "status":"TRADING",
                        "numerator":"BTC",
                        "denominator":"TRY",
                        "numeratorScale":8,
                        "denominatorScale":2,
                        "hasFraction":false,
                        "filters":[
                            {
                                "filterType":"PRICE_FILTER",
                                "minPrice":"0.0000000000001",
                                "maxPrice":"10000000",
                                "tickSize":"10",
                                "minExchangeValue":"99.91",
                                "minAmount":null,
                                "maxAmount":null
                            }
                        ],
                        "orderMethods":[
                            "MARKET",
                            "LIMIT",
                            "STOP_MARKET",
                            "STOP_LIMIT"
                        ],
                        "displayFormat":"#,###",
                        "commissionFromNumerator":false,
                        "order":1000,
                        "priceRounding":false,
                        "isNew":false,
                        "marketPriceWarningThresholdPercentage":0.2500000000000000
                    },
                    ....+
                ]
            }
        }
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api.btcturk.com/api/v2/server/exchangeinfo"))
                .getAsJsonObject().get("data")
                .getAsJsonObject().get("symbols")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            if (i.getAsJsonObject().get("status").getAsString().equals("TRADING"))
                symbolsList.add(i.getAsJsonObject().get("nameNormalized").getAsString());

        return symbolsList;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET v2/ticker?pairSymbol=
        {
            "data":[
            {
                "pair":"BTCUSDT",
                "pairNormalized":"BTC_USDT",
                "timestamp":1622451614310,
                "last":36159,
                "high":36400,
                "low":34084,
                "bid":36153,
                "ask":36200,
                "open":35798,
                "volume":104.97271737,
                "average":35612.53245745,
                "daily":402,
                "dailyPercent":1.01,
                "denominatorSymbol":"USDT",
                "numeratorSymbol":"BTC",
                "order":2001
            },
            ...
            ]
        }
        */

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.btcturk.com/api/v2/ticker?pairSymbol=" + symbol))
                .getAsJsonObject().get("data")
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", klinesJson.get("last").getAsBigDecimal());
        ticker.put("priceChange", klinesJson.get("daily").getAsBigDecimal());
        ticker.put("priceChangePercent", klinesJson.get("dailyPercent").getAsBigDecimal());
        ticker.put("volume", klinesJson.get("volume").getAsBigDecimal());

        return ticker;
    }
    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET https://graph-api.btcturk.com/v1/ohlcs?pair=
        [
          {
            "pair": "BTCTRY",
            "time": 1372636800,
            "open": 183.0,
            "high": 183.0,
            "low": 183.0,
            "close": 183.0,
            "volume": 6.20068047940731,
            "total": 1134.72450065613,
            "average": 183.00,
            "dailyChangeAmount": 0.0,
            "dailyChangePercentage": 0.0
          },
        ]
        */
        // Sadece günlük veriyor
        JsonArray klinesJson = JsonParser
                .parseString(response("https://graph-api.btcturk.com/v1/ohlcs?pair=" + symbol))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));

        return list;
    }
}
