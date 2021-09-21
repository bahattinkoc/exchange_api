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

public class Liquid extends General { // https://api.liquid.com/
    public static List<String> getSymbols() throws IOException {
        /* GET /products
        {
          "id": "659",
          "product_type": "CurrencyPair",
          "code": "CASH",
          "name": null,
          "market_ask": 12.5,
          "market_bid": 12.04,
          "indicator": null,
          "currency": "USD",
          "currency_pair_code": "EWTUSD",
          "symbol": null,
          "btc_minimum_withdraw": null,
          "fiat_minimum_withdraw": null,
          "pusher_channel": "product_cash_ewtusd_659",
          "taker_fee": "0.0",
          "maker_fee": "0.0",
          "low_market_bid": "11.91",
          "high_market_ask": "12.5",
          "volume_24h": "2361.64741972",
          "last_price_24h": "11.92",
          "last_traded_price": "12.32",
          "last_traded_quantity": "2.00340964",
          "average_price": "12.33314",
          "quoted_currency": "USD",
          "base_currency": "EWT",
          "tick_size": "0.01",
          "disabled": false,
          "margin_enabled": false,
          "cfd_enabled": false,
          "perpetual_enabled": false,
          "last_event_timestamp": "1598864820.004941733",
          "timestamp": "1598864820.004941733",
          "multiplier_up": "1.4",
          "multiplier_down": "0.6",
          "average_time_interval": 300
        },
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.liquid.com/products"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("disabled").getAsString().equals("false"))
                symbolsList.add(i.getAsJsonObject().get("currency_pair_code").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /products/{product_id}/ohlc?resolution={resolution}
        {
            "data":
            [
                [
                    1593842400,
                    9109.56,
                    9118.4,
                    9099.99,
                    9109.2,
                    5.39553224
                ],
                [ ... ]
            ]
        }
        */

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.liquid.com/products"))
                .getAsJsonArray();

        String id = "";
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("currency_pair_code").getAsString().equals(symbol)) {
                id = i.getAsJsonObject().get("id").getAsString();
                break;
            }

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.liquid.com/products/" + id + "/ohlc?resolution=86400"))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        BigDecimal open = klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(1).getAsBigDecimal();
        BigDecimal close = klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(4).getAsBigDecimal();

        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", klinesJson.get(klinesJson.size()-1).getAsJsonArray().get(5).getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /products/{product_id}/ohlc?resolution={resolution}
        {
            "data":
            [
                [
                    1593842400,
                    9109.56,
                    9118.4,
                    9099.99,
                    9109.2,
                    5.39553224
                ],
                [ ... ]
            ]
        }
        */

        //Burda yazılanların hepsini kabul ediyor
        int intervalResolution = (interval == Interval.INT_1MIN) ? 60 : (interval == Interval.INT_3MIN) ? 180 : (interval == Interval.INT_5MIN) ? 300
                : (interval == Interval.INT_15MIN) ? 900 : (interval == Interval.INT_30MIN) ? 1800 : (interval == Interval.INT_1HOUR) ? 3600
                : (interval == Interval.INT_2HOURS) ? 7200 : (interval == Interval.INT_4HOURS) ? 14400 : (interval == Interval.INT_6HOURS) ? 21600
                : (interval == Interval.INT_12HOURS) ? 43200 : (interval == Interval.INT_1DAY) ? 86400
                : (interval == Interval.INT_3DAYS) ? 3 * 86400 : 7 * 86400;

        JsonArray symbolsListJsonObj = JsonParser
                .parseString(response("https://api.liquid.com/products"))
                .getAsJsonArray();

        String id = "";
        for (JsonElement i : symbolsListJsonObj)
            if (i.getAsJsonObject().get("currency_pair_code").getAsString().equals(symbol)) {
                id = i.getAsJsonObject().get("id").getAsString();
                break;
            }

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.liquid.com/products/" + id + "/ohlc?resolution=" + intervalResolution))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonArray().get(1).getAsBigDecimal(),
                    e.getAsJsonArray().get(2).getAsBigDecimal(),
                    e.getAsJsonArray().get(3).getAsBigDecimal(),
                    e.getAsJsonArray().get(4).getAsBigDecimal(),
                    e.getAsJsonArray().get(5).getAsBigDecimal()));

        return list;
    }
}
