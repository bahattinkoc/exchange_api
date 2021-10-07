package com.bkoc.exchangeapi.exchanges;

import com.bkoc.exchangeapi.Candlestick;
import com.bkoc.exchangeapi.General;
import com.bkoc.exchangeapi.Interval;
import com.google.gson.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Binance extends General { //https://www.binance.com/api/
    public enum Permissions{
        SPOT,
        MARGIN
    }

    public enum Parite{
        USDT,
        BTC,
        ETH,
        BNB
    }

    public static List<String> getSymbols() throws IOException {
        /*{
              "timezone": "UTC",
              "serverTime": 1565246363776,
              "rateLimits": [
                {
                  //These are defined in the `ENUM definitions` section under `Rate Limiters (rateLimitType)`.
                  //All limits are optional
                }
              ],
              "exchangeFilters": [
                //These are the defined filters in the `Filters` section.
                //All filters are optional.
              ],
              "symbols": [
                {
                  "symbol": "ETHBTC",
                  "status": "TRADING",
                  "baseAsset": "ETH",
                  "baseAssetPrecision": 8,
                  "quoteAsset": "BTC",
                  "quotePrecision": 8,
                  "quoteAssetPrecision": 8,
                  "orderTypes": [
                    "LIMIT",
                    "LIMIT_MAKER",
                    "MARKET",
                    "STOP_LOSS",
                    "STOP_LOSS_LIMIT",
                    "TAKE_PROFIT",
                    "TAKE_PROFIT_LIMIT"
                  ],
                  "icebergAllowed": true,
                  "ocoAllowed": true,
                  "isSpotTradingAllowed": true,
                  "isMarginTradingAllowed": true,
                  "filters": [
                    //These are defined in the Filters section.
                    //All filters are optional
                  ],
                  "permissions": [
                     "SPOT",
                     "MARGIN"
                  ]
                }
              ]
        }*/

        JsonArray symbolsList = JsonParser
                .parseString(response("https://www.binance.com/api/v3/exchangeInfo"))
                .getAsJsonObject()
                .get("symbols")
                .getAsJsonArray();

        List<String> list = new LinkedList<>();
        for (JsonElement e : symbolsList) {
            JsonObject obj = e.getAsJsonObject();
            if (obj.get("status").getAsString().equals("TRADING"))
                list.add(obj.get("symbol").getAsString());
//            JsonArray permissionsList = obj.getAsJsonArray("permissions");
//            for (JsonElement i : permissionsList)
//                if (i.getAsString().equals(permissions.toString()) && obj.get("quoteAsset").getAsString().equals(parite.toString()) && obj.get("status").getAsString().equals("TRADING")) {
//                    list.add(obj.get("symbol").getAsString());
//                    break;
//                }
        }
        return list;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /*{
              "symbol": "BNBBTC",
              "priceChange": "-94.99999800",
              "priceChangePercent": "-95.960",
              "weightedAvgPrice": "0.29628482",
              "prevClosePrice": "0.10002000",
              "lastPrice": "4.00000200",
              "lastQty": "200.00000000",
              "bidPrice": "4.00000000",
              "askPrice": "4.00000200",
              "openPrice": "99.00000000",
              "highPrice": "100.00000000",
              "lowPrice": "0.10000000",
              "volume": "8913.30000000",
              "quoteVolume": "15.30000000",
              "openTime": 1499783499040,
              "closeTime": 1499869899040,
              "firstId": 28385,   // First tradeId
              "lastId": 28460,    // Last tradeId
              "count": 76         // Trade count
        }*/

        JsonObject tickerAsJsonObject = JsonParser
                .parseString(response("https://www.binance.com/api/v3/ticker/24hr?symbol=" + symbol))
                .getAsJsonObject();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        ticker.put("lastPrice", tickerAsJsonObject.get("lastPrice").getAsBigDecimal().stripTrailingZeros());
        ticker.put("priceChange", tickerAsJsonObject.get("priceChange").getAsBigDecimal().stripTrailingZeros());
        ticker.put("priceChangePercent", tickerAsJsonObject.get("priceChangePercent").getAsBigDecimal().stripTrailingZeros());
        ticker.put("volume", tickerAsJsonObject.get("volume").getAsBigDecimal().stripTrailingZeros()); // BTCUSDT -> BTC
//        ticker.put("quoteVolume", tickerAsJsonObject.get("quoteVolume").getAsBigDecimal().stripTrailingZeros()); // BTCUSDT -> USDT

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        try {
        /*[
              [
                1499040000000,      // Open time
                "0.01634790",       // Open
                "0.80000000",       // High
                "0.01575800",       // Low
                "0.01577100",       // Close
                "148976.11427815",  // Volume
                1499644799999,      // Close time
                "2434.19055334",    // Quote asset volume
                308,                // Number of trades
                "1756.87402397",    // Taker buy base asset volume
                "28.46694368",      // Taker buy quote asset volume
                "17928899.62484339" // Ignore.
              ]
        ]*/

            JsonArray klinesAsJsonArray = JsonParser
                    .parseString(response("https://www.binance.com/api/v3/klines?symbol=" + symbol + "&interval=" + interval.getValue() + "&limit=200"))
                    .getAsJsonArray();

            List<Candlestick> list = new LinkedList<>();
            for (JsonElement e : klinesAsJsonArray) {
                JsonArray obj = e.getAsJsonArray();
                list.add(new Candlestick(obj.get(1).getAsBigDecimal(), obj.get(2).getAsBigDecimal(), obj.get(3).getAsBigDecimal(), obj.get(4).getAsBigDecimal(), obj.get(5).getAsBigDecimal()));
            }

            return list;
        }
        catch (Exception e) { return null; }
    }
}
