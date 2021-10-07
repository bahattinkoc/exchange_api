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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Gateio extends General { //https://api.gateio.ws/api/v4/
    public enum Permissions{
        SPOT,
        MARGIN
    }

    public static List<String> getSymbols() throws IOException {
        /* GET /spot/currency_pairs
        [
          {
            "id": "ETH_USDT",
            "base": "ETH",
            "quote": "USDT",
            "fee": "0.2",
            "min_base_amount": "0.001",
            "min_quote_amount": "1.0",
            "amount_precision": 3,
            "precision": 6,
            "trade_status": "tradable",
            "sell_start": 1516378650,
            "buy_start": 1516378650
          }
        ]*/

        /* GET /margin/currency_pairs
        [
          {
            "id": "ETH_USDT",
            "base": "ETH",
            "quote": "USDT",
            "leverage": 3,
            "min_base_amount": "0.01",
            "min_quote_amount": "100",
            "max_quote_amount": "1000000"
          }
        ]*/

        List<String> list = new LinkedList<>();
        JsonArray symbolsList = JsonParser
                .parseString(response("https://api.gateio.ws/api/v4/spot/currency_pairs"))
                .getAsJsonArray();

        for (JsonElement i : symbolsList)
            if (i.getAsJsonObject().get("trade_status").getAsString().equals("tradable"))
                list.add(i.getAsJsonObject().get("id").getAsString());

        return list;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /spot/tickers
        [
          {
            "currency_pair": "BTC3L_USDT",
            "last": "2.46140352",
            "lowest_ask": "2.477",
            "highest_bid": "2.4606821",
            "change_percentage": "-8.91",
            "base_volume": "656614.0845820589",
            "quote_volume": "1602221.66468375534639404191",
            "high_24h": "2.7431",
            "low_24h": "1.9863",
            "etf_net_value": "2.46316141",
            "etf_pre_net_value": "2.43201848",
            "etf_pre_timestamp": 1611244800,
            "etf_leverage": "2.2803019447281203"
          }
        ]
        */

        JsonArray tickerAsJsonArray = JsonParser
                .parseString(response("https://api.gateio.ws/api/v4/spot/tickers?currency_pair=" + symbol))
                .getAsJsonArray();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerAsJsonArray.get(0).getAsJsonObject().get("last").getAsBigDecimal().stripTrailingZeros());
        ticker.put("priceChange", tickerAsJsonArray.get(0).getAsJsonObject().get("high_24h").getAsBigDecimal().subtract(tickerAsJsonArray.get(0).getAsJsonObject().get("low_24h").getAsBigDecimal()));
        ticker.put("priceChangePercent", tickerAsJsonArray.get(0).getAsJsonObject().get("change_percentage").getAsBigDecimal().stripTrailingZeros());
        ticker.put("volume", tickerAsJsonArray.get(0).getAsJsonObject().get("base_volume").getAsBigDecimal().stripTrailingZeros()); // BTCUSDT -> BTC

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /* GET /spot/candlesticks
        [
          [
            "1539852480",
            "971519.677",
            "0.0021724",
            "0.0021922",
            "0.0021724",
            "0.0021737"
          ]
        ]*/

            //10s, 1m, 5m, 15m, 30m, 1h, 4h, 8h, 1d, 7d
            String intervalStr = (interval.getValue().equals("1w")) ? "7d" : interval.getValue();
            JsonArray klinesAsJsonArray = JsonParser
                    .parseString(response("https://api.gateio.ws/api/v4/spot/candlesticks?currency_pair=" + symbol + "&limit=200&interval=" + intervalStr))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesAsJsonArray) {
                JsonArray obj = e.getAsJsonArray();
                list.add(new Candlestick(obj.get(5).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(1).getAsBigDecimal()));
            }

            return list;
        }
        catch (Exception e) { return null; }
    }
}
