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

public class CoinDCX extends General { // https://api.coindcx.com/ --- https://public.coindcx.com/
    public static List<String> getSymbols() throws IOException {
        /* GET /exchange/v1/markets_details
        [
          {
            "coindcx_name": "SNMBTC",
            "base_currency_short_name": "BTC",
            "target_currency_short_name": "SNM",
            "target_currency_name": "Sonm",
            "base_currency_name": "Bitcoin",
            "min_quantity": 1,
            "max_quantity": 90000000,
            "min_price": 5.66e-7,
            "max_price": 0.0000566,
            "min_notional": 0.001,
            "base_currency_precision": 8,
            "target_currency_precision": 0,
            "step": 1,
            "order_types": [ "take_profit", "stop_limit", "market_order", "limit_order" ],
            "symbol": "SNMBTC",
            "ecode": "B",
            "max_leverage": 3,
            "max_leverage_short": null,
            "pair": "B-SNM_BTC",
            "status": "active"
          }
        ]
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.coindcx.com/exchange/v1/markets_details"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("status").getAsString().equals("active"))
                symbolsList.add(i.getAsJsonObject().get("pair").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /exchange/ticker
        [
          {
            "market": "REQBTC",
            "change_24_hour": "-1.621",
            "high": "0.00002799",
            "low": "0.00002626",
            "volume": "14.10",
            "last_price": "0.00002663",
            "bid": "0.00002663",
            "ask": "0.00002669",
            "timestamp": 1524211224
          }
        ]
        */

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.coindcx.com/exchange/ticker"))
                .getAsJsonArray();

        String _symbol = symbol.substring(symbol.indexOf('-')).replace("-", "").replace("_", "");
        HashMap<String, BigDecimal> ticker = new HashMap<>();

        for (JsonElement i : klinesJson)
            if (i.getAsJsonObject().get("market").getAsString().equals(_symbol)) {

                BigDecimal close = i.getAsJsonObject().get("last_price").getAsBigDecimal().stripTrailingZeros();
                BigDecimal volume = i.getAsJsonObject().get("volume").getAsBigDecimal().divide(close, 8, RoundingMode.HALF_UP).stripTrailingZeros();
                BigDecimal percent = i.getAsJsonObject().get("change_24_hour").getAsBigDecimal().stripTrailingZeros();

                BigDecimal open = close.multiply(BigDecimal.valueOf(100)).divide(percent.add(BigDecimal.valueOf(100)), 8, RoundingMode.HALF_UP);
                BigDecimal priceChange = close.subtract(open);

                ticker.put("lastPrice", close);
                ticker.put("priceChange", priceChange);
                ticker.put("priceChangePercent", percent);
                ticker.put("volume", volume);
                break;
            }
        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /market_data/candles?pair=B-BTC_USDT&interval=1h
        [
          {
                "open": 0.011803, // open price
                "high": 0.011803, // highest price
                "low": 0.011803, // lowest price
                "volume": 0.35,  // total volume in terms of target currency (in BTC for B-BTC_USDT)
                "close": 0.011803, // close price
                "time": 1561463700000 //open time in ms
          }
          .
          .
          .
        ],
        */

            //1m 5m 15m 30m 1h 2h 4h 6h 8h 1d 3d 1w 1M
            JsonArray klinesJson = JsonParser
                    .parseString(response("https://public.coindcx.com/market_data/candles?pair=" + symbol + "&interval=" + interval.getValue()))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesJson)
                list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("high").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("low").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("close").getAsBigDecimal().stripTrailingZeros(),
                        e.getAsJsonObject().get("volume").getAsBigDecimal().stripTrailingZeros()));
            Collections.reverse(list);

            if (list.size() < 210)
                return list;
            else
                return list.subList(list.size() - 200, list.size());
        }
        catch (Exception e) { return null; }
    }
}
