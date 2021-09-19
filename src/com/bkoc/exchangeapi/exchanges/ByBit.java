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

public class ByBit extends General { // https://api.bybit.com/
    public static List<String> getSymbols() throws IOException {
        /* GET /v2/public/symbols
        {
            "ret_code":0,
            "ret_msg":"OK",
            "ext_code":"",
            "ext_info":"",
            "result":[
                {
                    "name":"BTCUSD",
                    "alias":"BTCUSD",
                    "status":"Trading",
                    "base_currency":"BTC",
                    "quote_currency":"USD",
                    "price_scale":2,
                    "taker_fee":"0.00075",
                    "maker_fee":"-0.00025",
                    "leverage_filter":{
                        "min_leverage":1,
                        "max_leverage":100,
                        "leverage_step":"0.01"
                    },
                    "price_filter":{
                        "min_price":"0.5",
                        "max_price":"999999.5",
                        "tick_size":"0.5"
                    },
                    "lot_size_filter":{
                        "max_trading_qty":1000000,
                        "min_trading_qty":1,
                        "qty_step":1
                    }
                },
                ...
        }
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://api.bybit.com/v2/public/symbols"))
                .getAsJsonObject().get("result")
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            if (i.getAsJsonObject().get("status").getAsString().equals("Trading"))
                symbolsList.add(i.getAsJsonObject().get("name").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /v2/public/tickers
        {
            "ret_code": 0,
            "ret_msg": "OK",
            "ext_code": "",
            "ext_info": "",
            "result": [
                {
                    "symbol": "BTCUSD",
                    "bid_price": "7230",
                    "ask_price": "7230.5",
                    "last_price": "7230.00",
                    "last_tick_direction": "ZeroMinusTick",
                    "prev_price_24h": "7163.00",
                    "price_24h_pcnt": "0.009353",
                    "high_price_24h": "7267.50",
                    "low_price_24h": "7067.00",
                    "prev_price_1h": "7209.50",
                    "price_1h_pcnt": "0.002843",
                    "mark_price": "7230.31",
                    "index_price": "7230.14",
                    "open_interest": 117860186,
                    "open_value": "16157.26",
                    "total_turnover": "3412874.21",
                    "turnover_24h": "10864.63",
                    "total_volume": 28291403954,
                    "volume_24h": 78053288,
                    "funding_rate": "0.0001",
                    "predicted_funding_rate": "0.0001",
                    "next_funding_time": "2019-12-28T00:00:00Z",
                    "countdown_hour": 2,
                    "delivery_fee_rate": "0",
                    "predicted_delivery_price": "0.00",
                    "delivery_time": ""
                },
                ...
        }
        */

        JsonObject tickerJson = JsonParser
                .parseString(response("https://api.bybit.com/v2/public/tickers?symbol=" + symbol))
                .getAsJsonObject().get("result")
                .getAsJsonArray().get(0)
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerJson.get("last_price").getAsBigDecimal());
        ticker.put("priceChange", tickerJson.get("last_price").getAsBigDecimal().subtract(tickerJson.get("prev_price_24h").getAsBigDecimal()));
        ticker.put("priceChangePercent", tickerJson.get("price_24h_pcnt").getAsBigDecimal().multiply(BigDecimal.valueOf(100)));
        ticker.put("volume", tickerJson.get("volume_24h").getAsBigDecimal());

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /v2/public/kline/list?symbol=BTCUSD&interval=1&limit=2&from=1581231260
        {
            "ret_code": 0,
            "ret_msg": "OK",
            "ext_code": "",
            "ext_info": "",
            "result": [{
                "symbol": "BTCUSD",
                "interval": "1",
                "open_time": 1581231300,
                "open": "10112.5",
                "high": "10112.5",
                "low": "10112",
                "close": "10112",
                "volume": "75981",
                "turnover": "7.51394369"
            }, {
                "symbol": "BTCUSD",
                "interval": "1",
                "open_time": 1581231360,
                "open": "10112",
                "high": "10112.5",
                "low": "10112",
                "close": "10112",
                "volume": "24616",
                "turnover": "2.4343353100000003"
            }],
            "time_now": "1581928016.558522"
        }
        */

        String intervalResolution = (interval == Interval.INT_1MIN) ? "1" : (interval == Interval.INT_3MIN) ? "3" : (interval == Interval.INT_5MIN) ? "5"
                : (interval == Interval.INT_15MIN) ? "15" : (interval == Interval.INT_30MIN) ? "30" : (interval == Interval.INT_1HOUR) ? "60"
                : (interval == Interval.INT_2HOURS) ? "120" : (interval == Interval.INT_4HOURS) ? "240" : (interval == Interval.INT_6HOURS) ? "360"
                : (interval == Interval.INT_12HOURS) ? "720" : (interval == Interval.INT_1DAY) ? "D" : (interval == Interval.INT_1WEEK) ? "W" : "M";

        long from = (interval == Interval.INT_1DAY) ? 1440 : (interval == Interval.INT_1WEEK) ? 10080 : (interval == Interval.INT_1MONTH) ? 40320 : Long.parseLong(intervalResolution);
        from = (System.currentTimeMillis() / 1000L) - (200 * from * 60);

        JsonObject klinesJson = JsonParser
                .parseString(response("https://api.bybit.com/v2/public/kline/list?symbol=" + symbol + "&interval=" + intervalResolution + "&limit=200&from=" + from))
                .getAsJsonObject();

        if (!klinesJson.get("ret_msg").getAsString().equals("OK"))
            klinesJson = JsonParser
                    .parseString(response("https://api.bybit.com/public/linear/kline?symbol=" + symbol + "&interval=" + intervalResolution + "&limit=200&from=" + from))
                    .getAsJsonObject();

        JsonArray jsonArray = klinesJson.get("result").getAsJsonArray();
        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : jsonArray)
            list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));

        return list;
    }
}
