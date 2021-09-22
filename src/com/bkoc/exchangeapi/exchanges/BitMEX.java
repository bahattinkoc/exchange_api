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

public class BitMEX extends General { // https://www.bitmex.com/api/v1
    public static List<String> getSymbols() throws IOException {
        /* GET /instrument/active
        [
          {
            "symbol": "string",
            "rootSymbol": "string",
            "state": "string",
            "typ": "string",
            "listing": "2021-09-22T18:35:01.198Z",
            "front": "2021-09-22T18:35:01.199Z",
            "expiry": "2021-09-22T18:35:01.199Z",
            "settle": "2021-09-22T18:35:01.199Z",
            "listedSettle": "2021-09-22T18:35:01.199Z",
            "relistInterval": "2021-09-22T18:35:01.199Z",
            "inverseLeg": "string",
            "sellLeg": "string",
            "buyLeg": "string",
            "optionStrikePcnt": 0,
            "optionStrikeRound": 0,
            "optionStrikePrice": 0,
            "optionMultiplier": 0,
            "positionCurrency": "string",
            "underlying": "string",
            "quoteCurrency": "string",
            "underlyingSymbol": "string",
            "reference": "string",
            "referenceSymbol": "string",
            "calcInterval": "2021-09-22T18:35:01.199Z",
            "publishInterval": "2021-09-22T18:35:01.199Z",
            "publishTime": "2021-09-22T18:35:01.199Z",
            "maxOrderQty": 0,
            "maxPrice": 0,
            "lotSize": 0,
            "tickSize": 0,
            "multiplier": 0,
            "settlCurrency": "string",
            "underlyingToPositionMultiplier": 0,
            "underlyingToSettleMultiplier": 0,
            "quoteToSettleMultiplier": 0,
            "isQuanto": true,
            "isInverse": true,
            "initMargin": 0,
            "maintMargin": 0,
            "riskLimit": 0,
            "riskStep": 0,
            "limit": 0,
            "capped": true,
            "taxed": true,
            "deleverage": true,
            "makerFee": 0,
            "takerFee": 0,
            "settlementFee": 0,
            "insuranceFee": 0,
            "fundingBaseSymbol": "string",
            "fundingQuoteSymbol": "string",
            "fundingPremiumSymbol": "string",
            "fundingTimestamp": "2021-09-22T18:35:01.199Z",
            "fundingInterval": "2021-09-22T18:35:01.199Z",
            "fundingRate": 0,
            "indicativeFundingRate": 0,
            "rebalanceTimestamp": "2021-09-22T18:35:01.199Z",
            "rebalanceInterval": "2021-09-22T18:35:01.199Z",
            "openingTimestamp": "2021-09-22T18:35:01.199Z",
            "closingTimestamp": "2021-09-22T18:35:01.199Z",
            "sessionInterval": "2021-09-22T18:35:01.199Z",
            "prevClosePrice": 0,
            "limitDownPrice": 0,
            "limitUpPrice": 0,
            "bankruptLimitDownPrice": 0,
            "bankruptLimitUpPrice": 0,
            "prevTotalVolume": 0,
            "totalVolume": 0,
            "volume": 0,
            "volume24h": 0,
            "prevTotalTurnover": 0,
            "totalTurnover": 0,
            "turnover": 0,
            "turnover24h": 0,
            "homeNotional24h": 0,
            "foreignNotional24h": 0,
            "prevPrice24h": 0,
            "vwap": 0,
            "highPrice": 0,
            "lowPrice": 0,
            "lastPrice": 0,
            "lastPriceProtected": 0,
            "lastTickDirection": "string",
            "lastChangePcnt": 0,
            "bidPrice": 0,
            "midPrice": 0,
            "askPrice": 0,
            "impactBidPrice": 0,
            "impactMidPrice": 0,
            "impactAskPrice": 0,
            "hasLiquidity": true,
            "openInterest": 0,
            "openValue": 0,
            "fairMethod": "string",
            "fairBasisRate": 0,
            "fairBasis": 0,
            "fairPrice": 0,
            "markMethod": "string",
            "markPrice": 0,
            "indicativeTaxRate": 0,
            "indicativeSettlePrice": 0,
            "optionUnderlyingPrice": 0,
            "settledPriceAdjustmentRate": 0,
            "settledPrice": 0,
            "timestamp": "2021-09-22T18:35:01.199Z"
          }
        ]
        */

        JsonArray symbolsListJsonArray = JsonParser
                .parseString(response("https://www.bitmex.com/api/v1/instrument/active"))
                .getAsJsonArray();

        List<String> symbolsList = new LinkedList<>();
        for (JsonElement i : symbolsListJsonArray)
            if (i.getAsJsonObject().get("state").getAsString().equals("Open"))
                symbolsList.add(i.getAsJsonObject().get("symbol").getAsString());

        return symbolsList;
    }

    public static HashMap<String, BigDecimal> ticker24hr(String symbol) throws IOException {
        /* GET /instrument/activeAndIndices
        [
          {
            "symbol": "string",
            "rootSymbol": "string",
            "state": "string",
            "typ": "string",
            "listing": "2021-09-22T18:35:01.214Z",
            "front": "2021-09-22T18:35:01.214Z",
            "expiry": "2021-09-22T18:35:01.214Z",
            "settle": "2021-09-22T18:35:01.214Z",
            "listedSettle": "2021-09-22T18:35:01.214Z",
            "relistInterval": "2021-09-22T18:35:01.214Z",
            "inverseLeg": "string",
            "sellLeg": "string",
            "buyLeg": "string",
            "optionStrikePcnt": 0,
            "optionStrikeRound": 0,
            "optionStrikePrice": 0,
            "optionMultiplier": 0,
            "positionCurrency": "string",
            "underlying": "string",
            "quoteCurrency": "string",
            "underlyingSymbol": "string",
            "reference": "string",
            "referenceSymbol": "string",
            "calcInterval": "2021-09-22T18:35:01.214Z",
            "publishInterval": "2021-09-22T18:35:01.214Z",
            "publishTime": "2021-09-22T18:35:01.214Z",
            "maxOrderQty": 0,
            "maxPrice": 0,
            "lotSize": 0,
            "tickSize": 0,
            "multiplier": 0,
            "settlCurrency": "string",
            "underlyingToPositionMultiplier": 0,
            "underlyingToSettleMultiplier": 0,
            "quoteToSettleMultiplier": 0,
            "isQuanto": true,
            "isInverse": true,
            "initMargin": 0,
            "maintMargin": 0,
            "riskLimit": 0,
            "riskStep": 0,
            "limit": 0,
            "capped": true,
            "taxed": true,
            "deleverage": true,
            "makerFee": 0,
            "takerFee": 0,
            "settlementFee": 0,
            "insuranceFee": 0,
            "fundingBaseSymbol": "string",
            "fundingQuoteSymbol": "string",
            "fundingPremiumSymbol": "string",
            "fundingTimestamp": "2021-09-22T18:35:01.214Z",
            "fundingInterval": "2021-09-22T18:35:01.214Z",
            "fundingRate": 0,
            "indicativeFundingRate": 0,
            "rebalanceTimestamp": "2021-09-22T18:35:01.214Z",
            "rebalanceInterval": "2021-09-22T18:35:01.214Z",
            "openingTimestamp": "2021-09-22T18:35:01.214Z",
            "closingTimestamp": "2021-09-22T18:35:01.214Z",
            "sessionInterval": "2021-09-22T18:35:01.214Z",
            "prevClosePrice": 0,
            "limitDownPrice": 0,
            "limitUpPrice": 0,
            "bankruptLimitDownPrice": 0,
            "bankruptLimitUpPrice": 0,
            "prevTotalVolume": 0,
            "totalVolume": 0,
            "volume": 0,
            "volume24h": 0,
            "prevTotalTurnover": 0,
            "totalTurnover": 0,
            "turnover": 0,
            "turnover24h": 0,
            "homeNotional24h": 0,
            "foreignNotional24h": 0,
            "prevPrice24h": 0,
            "vwap": 0,
            "highPrice": 0,
            "lowPrice": 0,
            "lastPrice": 0,
            "lastPriceProtected": 0,
            "lastTickDirection": "string",
            "lastChangePcnt": 0,
            "bidPrice": 0,
            "midPrice": 0,
            "askPrice": 0,
            "impactBidPrice": 0,
            "impactMidPrice": 0,
            "impactAskPrice": 0,
            "hasLiquidity": true,
            "openInterest": 0,
            "openValue": 0,
            "fairMethod": "string",
            "fairBasisRate": 0,
            "fairBasis": 0,
            "fairPrice": 0,
            "markMethod": "string",
            "markPrice": 0,
            "indicativeTaxRate": 0,
            "indicativeSettlePrice": 0,
            "optionUnderlyingPrice": 0,
            "settledPriceAdjustmentRate": 0,
            "settledPrice": 0,
            "timestamp": "2021-09-22T18:35:01.215Z"
          }
        ]
        */

        JsonArray tickerJson = JsonParser
                .parseString(response("https://www.bitmex.com/api/v1/instrument/activeAndIndices"))
                .getAsJsonArray();

        HashMap<String, BigDecimal> ticker = new HashMap<>();
        for (JsonElement i : tickerJson)
            if (i.getAsJsonObject().get("symbol").getAsString().equals(symbol) && i.getAsJsonObject().get("state").getAsString().equals("Open")) {
                BigDecimal close = i.getAsJsonObject().get("lastPrice").getAsBigDecimal();
                BigDecimal percent = i.getAsJsonObject().get("lastChangePcnt").getAsBigDecimal().multiply(BigDecimal.valueOf(100));
                BigDecimal open = close.multiply(BigDecimal.valueOf(100)).divide(percent.add(BigDecimal.valueOf(100)), 8, RoundingMode.HALF_UP);

                ticker.put("lastPrice", close);
                ticker.put("priceChange", close.subtract(open));
                ticker.put("priceChangePercent", percent);
                ticker.put("volume", i.getAsJsonObject().get("homeNotional24h").getAsBigDecimal());
                break;
            }

        return ticker;
    }

    public static List<Candlestick> klines(String symbol, Interval interval) throws Exception {
        /* GET /trade/bucketed?binSize=1h&partial=true&symbol=XBTUSD&count=2&reverse=true
        [
          {
            "timestamp": "2021-09-22T22:00:00.000Z",
            "symbol": "XBTUSD",
            "open": 43423,
            "high": 43469.5,
            "low": 43305,
            "close": 43460,
            "trades": 722,
            "volume": 3112500,
            "vwap": 43397.8804,
            "lastSize": 200,
            "turnover": 7172025344,
            "homeNotional": 71.72025344000001,
            "foreignNotional": 3112500
          }
        ]
        */

        //Sadece bunlar
        String intervalResolution = (interval == Interval.INT_1MIN) ? "1m" : (interval == Interval.INT_5MIN) ? "5m"
                : (interval == Interval.INT_1HOUR) ? "1h" : "1d";

        JsonArray klinesJson = JsonParser
                .parseString(response("https://www.bitmex.com/api/v1/trade/bucketed?binSize=" + intervalResolution + "&partial=true&symbol=" + symbol + "&count=300&reverse=true"))
                .getAsJsonArray();

        List<Candlestick> list = new LinkedList<>();
        for (JsonElement e : klinesJson)
            list.add(new Candlestick(e.getAsJsonObject().get("open").getAsBigDecimal(), e.getAsJsonObject().get("high").getAsBigDecimal(), e.getAsJsonObject().get("low").getAsBigDecimal(), e.getAsJsonObject().get("close").getAsBigDecimal(), e.getAsJsonObject().get("volume").getAsBigDecimal()));
        Collections.reverse(list);
        return list;
    }
}
