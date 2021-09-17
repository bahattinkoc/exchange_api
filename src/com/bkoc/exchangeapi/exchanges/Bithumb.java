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

public class Bithumb extends General { // https://api.bithumb.com
    public static List<String> getSymbols() throws IOException {
        /* GET /public/ticker/
        {
            "status" : "0000",
            "data" : {
                "opening_price" : "504000",
                "closing_price" : "505000",
                "min_price" : "504000",
                "max_price" : "516000",
                "units_traded" : "14.71960286",
                "acc_trade_value" : "16878100",
                "prev_closing_price" : "503000",
                "units_traded_24H" : "1471960286",
                "acc_trade_value_24H" : "16878100",
                "fluctate_24H" : "1000",
                "fluctate_rate_24H" : 0.19,
                "date" : "1417141032622"
            }
        }
        */

        // Var olan tekli kriptoların listesini al BTC,ETH...
        JsonObject symbolsListJsonArray = JsonParser
                .parseString(response("https://api.bithumb.com/public/ticker/all"))
                .getAsJsonObject().get("data")
                .getAsJsonObject();
        List<String> symbolsList = new LinkedList<>();
        JsonArray array = JsonParser.parseString(symbolsListJsonArray.keySet().toString()).getAsJsonArray();
        for (JsonElement i : array)
            symbolsList.add(i.getAsString().concat("_KRW"));
        symbolsList.remove(symbolsList.size()-1);

//        List<String> list = new LinkedList<>();
//        for (String symbol : symbolsList){
//            JsonObject tradingPairKRW = JsonParser
//                    .parseString(response("https://api.bithumb.com/public/ticker/" + symbol + "_KRW"))
//                    .getAsJsonObject();
//            if (tradingPairKRW.get("status").getAsString().equals("0000")) {
//                list.add(symbol + "_KRW");
//                System.out.println(symbol+"_KRW");
//            }
//            tradingPairKRW = JsonParser
//                    .parseString(response("https://api.bithumb.com/public/ticker/" + symbol + "_BTC"))
//                    .getAsJsonObject();
//            if (tradingPairKRW.get("status").getAsString().equals("0000")) {
//                list.add(symbol + "_BTC");
//                System.out.println(symbol+"_BTC");
//            }
//        }

        return symbolsList;
    }
    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /public/ticker/
        {
            "status" : "0000",
            "data" : {
                "opening_price" : "504000",
                "closing_price" : "505000",
                "min_price" : "504000",
                "max_price" : "516000",
                "units_traded" : "14.71960286",
                "acc_trade_value" : "16878100",
                "prev_closing_price" : "503000",
                "units_traded_24H" : "1471960286",
                "acc_trade_value_24H" : "16878100",
                "fluctate_24H" : "1000",
                "fluctate_rate_24H" : 0.19,
                "date" : "1417141032622"
            }
        }
        */

        JsonObject tickerJson = JsonParser
                .parseString(response("https://api.bithumb.com/public/ticker/"+symbol))
                .getAsJsonObject().get("data")
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerJson.get("closing_price").getAsBigDecimal());
        ticker.put("priceChange", tickerJson.get("fluctate_24H").getAsBigDecimal());
        ticker.put("priceChangePercent", tickerJson.get("fluctate_rate_24H").getAsBigDecimal());
        ticker.put("volume", tickerJson.get("units_traded_24H").getAsBigDecimal());

        return ticker;
    }
    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /public/candlestick/{order_currency}_{payment_currency}/{chart_intervals}
        {
            “status”: “0000”,
            “data” : {
                [
                    1576823400000, // standart zaman
                    “8284000”,// Market fiyatı
                    “8286000”,// kapanış fiyatı
                    “8289000”,// yüksek fiyat
                    “8276000”,// Düşük fiyat
                    “15.41503692”// Işlem hacmi
                ],
                [
                    1576824000000, // time
                    “8284000”,// open
                    “8281000”,// close
                    “8289000”,// high
                    “8275000”,// low
                    “6.19584467”// volume
                ],
                …
            }
        }
        */
        String intervalStr = (interval == Interval.INT_1DAY) ? "24h" : interval.getValue();

        JsonArray klinesJson = JsonParser
                .parseString(response("https://api.bithumb.com/public/candlestick/" + symbol + "/" + intervalStr))
                .getAsJsonObject().get("data")
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson) {
            JsonArray obj = e.getAsJsonArray();
            list.add(new Candlestick(obj.get(1).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(5).getAsBigDecimal()));
        }

        return list;
    }
}
