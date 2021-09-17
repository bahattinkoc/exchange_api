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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Kraken extends General { // https://api.kraken.com/0/public/
    public static List<String> getSymbols() throws IOException {
        /* GET /AssetPairs
        {
        "error": [ ],
        "result": {
            "XETHXXBT": {
            "altname": "ETHXBT",
            "wsname": "ETH/XBT",
            "aclass_base": "currency",
            "base": "XETH",
            "aclass_quote": "currency",
            "quote": "XXBT",
            "lot": "unit",
            "pair_decimals": 5,
            "lot_decimals": 8,
            "lot_multiplier": 1,
            "leverage_buy": [
            2,
            3,
            4,
            5
            ],
            "leverage_sell": [
            2,
            3,
            4,
            5
            ],
            "fees": [
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            []
            ],
            "fees_maker": [
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            []
            ],
            "fee_volume_currency": "ZUSD",
            "margin_call": 80,
            "margin_stop": 40,
            "ordermin": "0.005"
            },
            "XXBTZUSD": {
            "altname": "XBTUSD",
            "wsname": "XBT/USD",
            "aclass_base": "currency",
            "base": "XXBT",
            "aclass_quote": "currency",
            "quote": "ZUSD",
            "lot": "unit",
            "pair_decimals": 1,
            "lot_decimals": 8,
            "lot_multiplier": 1,
            "leverage_buy": [
            2,
            3,
            4,
            5
            ],
            "leverage_sell": [
            2,
            3,
            4,
            5
            ],
            "fees": [
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            []
            ],
            "fees_maker": [
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            [],
            []
            ],
            "fee_volume_currency": "ZUSD",
            "margin_call": 80,
            "margin_stop": 40,
            "ordermin": "0.0002"
            }
            }
        }
        */

        List<String> list = new LinkedList<>();
        JsonObject symbolsObj = JsonParser
                .parseString(response("https://api.kraken.com/0/public/AssetPairs"))
                .getAsJsonObject().get("result")
                .getAsJsonObject();
        //https://stackoverflow.com/questions/22687771/how-to-convert-jsonobjects-to-jsonarray
        //https://stackoverflow.com/questions/31094305/java-gson-getting-the-list-of-all-keys-under-a-jsonobject
        JsonArray array = JsonParser.parseString(symbolsObj.keySet().toString()).getAsJsonArray();
        for (JsonElement i : array)
            list.add(i.getAsString());

        return list;
    }
    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /OHLC
        {
            "error": [ ],
            "result": {
            "XXBTZUSD": [
            [
                1616662740,
                "52591.9",
                "52599.9",
                "52591.8",
                "52599.9",
                "52599.1",
                "0.11091626",
                5
            ],
            [
                1616662800,
                "52600.0",
                "52674.9",
                "52599.9",
                "52665.2",
                "52643.3",
                "2.49035996",
                30
            ],
            [
                1616662860,
                "52677.7",
                "52686.4",
                "52602.1",
                "52609.5",
                "52634.5",
                "1.25810675",
                20
            ],
            [
                1616662920,
                "52603.9",
                "52627.5",
                "52601.2",
                "52616.4",
                "52614.0",
                "3.42391799",
                23
            ],
            [
                1616662980,
                "52601.2",
                "52601.2",
                "52599.9",
                "52599.9",
                "52599.9",
                "0.43748934",
                7
            ]
            ],
            "last": 1616662920
            }
        }
        */
        String intervalStr = (interval == Interval.INT_1MIN) ? "1" : (interval == Interval.INT_5MIN) ? "5" : (interval == Interval.INT_15MIN) ? "1"
                : (interval == Interval.INT_30MIN) ? "30" : (interval == Interval.INT_1HOUR) ? "60" : (interval == Interval.INT_4HOURS) ? "240"
                : (interval == Interval.INT_1DAY) ? "1440" : "10080";

        JsonArray klinesAsJsonArray = JsonParser
                .parseString(response("https://api.kraken.com/0/public/OHLC?pair=" + symbol + "&interval=" + intervalStr))
                .getAsJsonObject().get("result")
                .getAsJsonObject().get(symbol)
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesAsJsonArray) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(1).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(6).getAsBigDecimal()));
        }

        return list;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /Ticker
        {
        "error": [ ],
        "result": {
            "XXBTZUSD": {
                "a": [
                "52609.60000",
                "1",
                "1.000"
                ],
                "b": [
                "52609.50000",
                "1",
                "1.000"
                ],
                "c": [
                "52641.10000",
                "0.00080000"
                ],
                "v": [
                "1920.83610601",
                "7954.00219674"
                ],
                "p": [
                "52389.94668",
                "54022.90683"
                ],
                "t": [
                23329,
                80463
                ],
                "l": [
                "51513.90000",
                "51513.90000"
                ],
                "h": [
                "53219.90000",
                "57200.00000"
                ],
                "o": "52280.40000"
                }
            }
        }
        */
        String json = response("https://api.kraken.com/0/public/Ticker?pair=" + symbol);

        BigDecimal open = JsonParser
                .parseString(json)
                .getAsJsonObject().get("result")
                .getAsJsonObject().get(symbol)
                .getAsJsonObject().get("o").getAsBigDecimal();

        BigDecimal close = JsonParser
                .parseString(json)
                .getAsJsonObject().get("result")
                .getAsJsonObject().get(symbol)
                .getAsJsonObject().get("c")
                .getAsJsonArray().get(0).getAsBigDecimal();

        BigDecimal volume = JsonParser
                .parseString(json)
                .getAsJsonObject().get("result")
                .getAsJsonObject().get(symbol)
                .getAsJsonObject().get("v")
                .getAsJsonArray().get(0).getAsBigDecimal();


        BigDecimal priceChange = close.subtract(open);
        BigDecimal percent = priceChange.divide(open, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", close);
        ticker.put("priceChange", priceChange);
        ticker.put("priceChangePercent", percent);
        ticker.put("volume", volume);

        return ticker;
    }
}
