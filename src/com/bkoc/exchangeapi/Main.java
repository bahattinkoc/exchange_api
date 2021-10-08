package com.bkoc.exchangeapi;

import com.bkoc.exchangeapi.exchanges.*;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //FAG FEAR & GREED
//        List<Float> fagList = FAG.getFAG(); // İlk eleman bugüne ait
//        for (float i : fagList)
//            System.out.println(i);
//        System.out.println("Size: " + fagList.size());

//        List<HashMap<String, String>> top7 = Top7.getTop7();
//        for (HashMap<String, String> i : top7)
//            System.out.println("Name: " + i.get("name") + "\nSymbol: " + i.get("symbol") + "\nRank: " + i.get("rank") + "\nImage: " + i.get("small") + "\nPrice: " + i.get("price_btc") + "\n\n");

//        HashMap<String, String> global = Global.getGlobal();
//        for (String i : global.keySet())
//            System.out.println(i + " -> " +global.get(i));
//        System.out.println(Global.getPercent24h());

        //https://api.coingecko.com/api/v3/global
        //https://syncwith.com/api/coinpaprika/get/v1-global

        //FTX 3MIN--30MIN--2HOUR--6HOUR--8HOUR--12HOUR
//        List<Candlestick> candlestickListFTX = FTX.klines("ETH/BTC", Interval.INT_1DAY);
//        List<BigDecimal> closesFTX = FTX.getValuesOfCandlestics(candlestickListFTX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesFTX)
//            System.out.println("Close: " + i);
//        System.out.println("Size: " + closesFTX.size());
//        List<String> symbolsFTX = FTX.getSymbols();
//        for (String i : symbolsFTX)
//            System.out.println(i);
//        HashMap<String, BigDecimal> tickerFTX = FTX.ticker24hr("BEAR/USD");
//        System.out.println("LastPrice: " + tickerFTX.get("lastPrice") + "\nPrice Change: " + tickerFTX.get("priceChange") + "\nPercent: " + tickerFTX.get("priceChangePercent") + "\nVolume: " + tickerFTX.get("volume"));

        //BINANCE
//        List<Candlestick> candlestickListBinance = Binance.klines("ZENBTC", Interval.INT_1DAY);
//        List<BigDecimal> closesBinance = Binance.getValuesOfCandlestics(candlestickListBinance, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBinance)
//            System.out.println("Close: " + i);
//        List<String> symbolsBinance = Binance.getSymbols();
//        for (String i : symbolsBinance)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBinance.size());
//        HashMap<String, BigDecimal> tickerBinance = Binance.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerBinance.get("lastPrice") + "\nPrice Change: " + tickerBinance.get("priceChange") + "\nPercent: " + tickerBinance.get("priceChangePercent") + "\nVolume: " + tickerBinance.get("volume"));

        //GATEIO
//        List<String> gateioSpot = Gateio.getSymbols();
//        for (String i : gateioSpot)
//            System.out.println(i);
//        System.out.println("Size: " + gateioSpot.size());
//        List<Candlestick> candlestickList = Gateio.klines("ADD_ETH", Interval.INT_15MIN);
//        List<BigDecimal> closesGateio = Gateio.getValuesOfCandlestics(candlestickList, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesGateio)
//            System.out.println(i);
//        System.out.println("Size: " + closesGateio.size());
//        HashMap<String, BigDecimal> tickerGateio = Gateio.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerGateio.get("lastPrice") + "\nPrice Change: " + tickerGateio.get("priceChange") + "\nPercent: " + tickerGateio.get("priceChangePercent") + "\nVolume: " + tickerGateio.get("volume"));

        //HUOBI
//        List<String> symbolsHuobi = Huobi.getSymbols();
//        for (String i : symbolsHuobi)
//            System.out.println(i);
//        List<Candlestick> candlesticksHuobi = Huobi.klines("btcusdt", Interval.INT_1WEEK);
//        List<BigDecimal> closesHuobi = Huobi.getValuesOfCandlestics(candlesticksHuobi, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesHuobi)
//            System.out.println(i);
//        System.out.println("Size: " + closesHuobi.size());
//        HashMap<String, BigDecimal> tickerGateio = Huobi.ticker24hr("btcusdt");
//        System.out.println("LastPrice: " + tickerGateio.get("lastPrice") + "\nPrice Change: " + tickerGateio.get("priceChange") + "\nPercent: " + tickerGateio.get("priceChangePercent") + "\nVolume: " + tickerGateio.get("volume"));

        //KRAKEN
//        List<String> symbolsKraken = Kraken.getSymbols();
//        for (String i : symbolsKraken)
//            System.out.println(i);
//        List<Candlestick> candlesticksKraken = Kraken.klines("XBTUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesKraken = Kraken.getValuesOfCandlestics(candlesticksKraken, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesKraken)
//            System.out.println(i);
//        System.out.println("Size: " + closesKraken.size());
//        HashMap<String, BigDecimal> tickerKraken = Kraken.ticker24hr("XXBTZUSD");
//        System.out.println("LastPrice: " + tickerKraken.get("lastPrice") + "\nPrice Change: " + tickerKraken.get("priceChange") + "\nPercent: " + tickerKraken.get("priceChangePercent") + "\nVolume: " + tickerKraken.get("volume"));

        //KUCOIN
//        List<String> symbolsKucoin = Kucoin.getSymbols();
//        for (String i : symbolsKucoin)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsKucoin.size());
//        List<Candlestick> closesKucoin = Kucoin.klines("BTC-USDT", Interval.INT_3DAYS);
//        List<BigDecimal> closesKucoinList = Kucoin.getValuesOfCandlestics(closesKucoin, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesKucoinList)
//            System.out.println(i);
//        System.out.println("Size: " + closesKucoinList.size());
//        HashMap<String, BigDecimal> tickerKucoin = Kucoin.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerKucoin.get("lastPrice") + "\nPrice Change: " + tickerKucoin.get("priceChange") + "\nPercent: " + tickerKucoin.get("priceChangePercent") + "\nVolume: " + tickerKucoin.get("volume"));

        //COINBASEPRO
//        List<String> symbolsCoinbasePro = CoinbasePro.getSymbols();
//        for (String i : symbolsCoinbasePro)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsCoinbasePro.size());
//        List<Candlestick> candlesticksCoinbasePro = CoinbasePro.klines("BTC-USDT", Interval.INT_1HOUR);
//        List<BigDecimal> candlesticksCoinbaseProList = CoinbasePro.getValuesOfCandlestics(candlesticksCoinbasePro, General.OHLCV.CLOSE);
//        for (BigDecimal i :candlesticksCoinbaseProList)
//            System.out.println(i);
//        System.out.println("Size: " + candlesticksCoinbaseProList.size());
//        HashMap<String, BigDecimal> tickerCoinbasePro = CoinbasePro.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerCoinbasePro.get("lastPrice") + "\nPrice Change: " + tickerCoinbasePro.get("priceChange") + "\nPercent: " + tickerCoinbasePro.get("priceChangePercent") + "\nVolume: " + tickerCoinbasePro.get("volume"));

        //BITHUMB
//        List<String> symbolsBithumb = Bithumb.getSymbols();
//        for (String i : symbolsBithumb)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBithumb.size());
//        List<Candlestick> candlesticksBithumb = Bithumb.klines("BTC_KRW", Interval.INT_1DAY);
//        List<BigDecimal> candlesticksBithumbList = Bithumb.getValuesOfCandlestics(candlesticksBithumb, General.OHLCV.CLOSE);
//        for (BigDecimal i :candlesticksBithumbList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + candlesticksBithumbList.size());
//        HashMap<String, BigDecimal> tickerBithumb = Bithumb.ticker24hr("BTC_KRW");
//        System.out.println("LastPrice: " + tickerBithumb.get("lastPrice") + "\nPrice Change: " + tickerBithumb.get("priceChange") + "\nPercent: " + tickerBithumb.get("priceChangePercent") + "\nVolume: " + tickerBithumb.get("volume"));

        //OKEX 8HOUR---
//        List<String> symbolsOkex = Okex.getSymbols();
//        for (String i : symbolsOkex)
//            System.out.println(i);
//        List<Candlestick> candlesticksOkex = Okex.klines("BTC-USDT", Interval.INT_1DAY);
//        List<BigDecimal> candlesticksOkexList = Bithumb.getValuesOfCandlestics(candlesticksOkex, General.OHLCV.CLOSE);
//        for (BigDecimal i :candlesticksOkexList)
//            System.out.println(i);
//        System.out.println("Size: " + candlesticksOkexList.size());
//        HashMap<String, BigDecimal> tickerOkex = Okex.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerOkex.get("lastPrice") + "\nPrice Change: " + tickerOkex.get("priceChange") + "\nPercent: " + tickerOkex.get("priceChangePercent") + "\nVolume: " + tickerOkex.get("volume"));

        //BITFINEX
//        List<String> symbolsBitfinex = Bitfinex.getSymbols();
//        for (String i : symbolsBitfinex)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBitfinex.size());
//        List<Candlestick> candlesticksBitfinex = Bitfinex.klines("BTCUSD", Interval.INT_1MONTH);
//        List<BigDecimal> csBitfinexList = Bitfinex.getValuesOfCandlestics(candlesticksBitfinex, General.OHLCV.CLOSE);
//        for (BigDecimal i : csBitfinexList)
//            System.out.println(i);
//        System.out.println("Size: " + csBitfinexList.size());
//        HashMap<String, BigDecimal> tickerBitfinex = Bitfinex.ticker24hr(symbolsBitfinex.get(20));
//        System.out.println(symbolsBitfinex.get(20));
//        System.out.println("LastPrice: " + tickerBitfinex.get("lastPrice") + "\nPrice Change: " + tickerBitfinex.get("priceChange") + "\nPercent: " + tickerBitfinex.get("priceChangePercent") + "\nVolume: " + tickerBitfinex.get("volume"));

        //BITSTAMP
//        List<String> symbolsBitstamp = Bitstamp.getSymbols();
//        for (String i : symbolsBitstamp)
//            System.out.println(i);
//        List<Candlestick> candlesticksBitstamp = Bitstamp.klines("btcusdt", Interval.INT_1DAY);
//        List<BigDecimal> csBitstampList = Bitstamp.getValuesOfCandlestics(candlesticksBitstamp, General.OHLCV.CLOSE);
//        for (BigDecimal i : csBitstampList)
//            System.out.println(i);
//        System.out.println("Size: " + csBitstampList.size());
//        HashMap<String, BigDecimal> tickerBitstamp = Bitstamp.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerBitstamp.get("lastPrice") + "\nPrice Change: " + tickerBitstamp.get("priceChange") + "\nPercent: " + tickerBitstamp.get("priceChangePercent") + "\nVolume: " + tickerBitstamp.get("volume"));

        //GEMINI
//        List<String> symbolsGemini = Gemini.getSymbols();
//        for (String i : symbolsGemini)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsGemini.size());
//        List<Candlestick> candlesticksGemini = Gemini.klines("btcusd", Interval.INT_1DAY);
//        List<BigDecimal> csGeminiList = Gemini.getValuesOfCandlestics(candlesticksGemini, General.OHLCV.CLOSE);
//        for (BigDecimal i : csGeminiList)
//            System.out.println(i);
//        System.out.println("Size: " + csGeminiList.size());
//        HashMap<String, BigDecimal> tickerGemini = Gemini.ticker24hr("BTCUSD");
//        System.out.println("LastPrice: " + tickerGemini.get("lastPrice") + "\nPrice Change: " + tickerGemini.get("priceChange") + "\nPercent: " + tickerGemini.get("priceChangePercent") + "\nVolume: " + tickerGemini.get("volume"));

        //BITTREX
//        List<String> symbolsBittrex = Bittrex.getSymbols();
//        for (String i : symbolsBittrex)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBittrex.size());
//        List<Candlestick> closesBittrex = Bittrex.klines("BTC-USDT", Interval.INT_1DAY);
//        List<BigDecimal> closesBittrexList = Bittrex.getValuesOfCandlestics(closesBittrex, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBittrexList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesBittrexList.size());
//        HashMap<String, BigDecimal> tickerBittrex = Bittrex.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerBittrex.get("lastPrice") + "\nPrice Change: " + tickerBittrex.get("priceChange") + "\nPercent: " + tickerBittrex.get("priceChangePercent") + "\nVolume: " + tickerBittrex.get("volume"));

        //BYBIT
//        List<String> symbolsByBit = ByBit.getSymbols();
//        for (String i : symbolsByBit)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsByBit.size());
//        List<Candlestick> closesByBit = ByBit.klines("SOLUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesByBitList = ByBit.getValuesOfCandlestics(closesByBit, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesByBitList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesByBitList.size());
//        HashMap<String, BigDecimal> tickerByBit = ByBit.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerByBit.get("lastPrice") + "\nPrice Change: " + tickerByBit.get("priceChange") + "\nPercent: " + tickerByBit.get("priceChangePercent") + "\nVolume: " + tickerByBit.get("volume"));

        //CRYPTOCOM
//        List<String> symbolsCryptocom = Cryptocom.getSymbols();
//        for (String i : symbolsCryptocom)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsCryptocom.size());
//        List<Candlestick> closesCryptocom = Cryptocom.klines("BTC_USDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesCryptocomList = ByBit.getValuesOfCandlestics(closesCryptocom, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesCryptocomList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesCryptocomList.size());
//        HashMap<String, BigDecimal> tickerCryptocom = Cryptocom.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerCryptocom.get("lastPrice") + "\nPrice Change: " + tickerCryptocom.get("priceChange") + "\nPercent: " + tickerCryptocom.get("priceChangePercent") + "\nVolume: " + tickerCryptocom.get("volume"));

        //BTCTURK
//        List<String> symbolsBtcTurk = BtcTurk.getSymbols();
//        for (String i : symbolsBtcTurk)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBtcTurk.size());
//        List<Candlestick> closesBtcTurk = BtcTurk.klines("BTC_USDT", Interval.INT_1DAY);
//        List<BigDecimal> closesBtcTurkList = BtcTurk.getValuesOfCandlestics(closesBtcTurk, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBtcTurkList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesBtcTurkList.size());
//        HashMap<String, BigDecimal> tickerBtcTurk = BtcTurk.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerBtcTurk.get("lastPrice") + "\nPrice Change: " + tickerBtcTurk.get("priceChange") + "\nPercent: " + tickerBtcTurk.get("priceChangePercent") + "\nVolume: " + tickerBtcTurk.get("volume"));

        //POLONIEX
//        List<String> symbolsPoloniex = Poloniex.getSymbols();
//        for (String i : symbolsPoloniex)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsPoloniex.size());
//        List<Candlestick> closesPoloniex = Poloniex.klines("USDT_BTC", Interval.INT_1DAY);
//        List<BigDecimal> closesPoloniexList = Poloniex.getValuesOfCandlestics(closesPoloniex, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesPoloniexList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesPoloniexList.size());
//        HashMap<String, BigDecimal> tickerPoloniex = Poloniex.ticker24hr("USDT_BTC");
//        System.out.println("LastPrice: " + tickerPoloniex.get("lastPrice") + "\nPrice Change: " + tickerPoloniex.get("priceChange") + "\nPercent: " + tickerPoloniex.get("priceChangePercent") + "\nVolume: " + tickerPoloniex.get("volume"));

        //ASCENDEX
//        List<String> symbolsAscendEX = AscendEX.getSymbols();
//        for (String i : symbolsAscendEX)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsAscendEX.size());
//        List<Candlestick> closesAscendEX = AscendEX.klines("BTC/USDT", Interval.INT_1MONTH);
//        List<BigDecimal> closesAscendEXList = AscendEX.getValuesOfCandlestics(closesAscendEX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesAscendEXList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        HashMap<String, BigDecimal> tickerAscendEX = AscendEX.ticker24hr("BTC/USDT");
//        System.out.println("LastPrice: " + tickerAscendEX.get("lastPrice") + "\nPrice Change: " + tickerAscendEX.get("priceChange") + "\nPercent: " + tickerAscendEX.get("priceChangePercent") + "\nVolume: " + tickerAscendEX.get("volume"));

        //HITBTC
//        List<String> symbolsHitBTC = HitBTC.getSymbols();
//        for (String i : symbolsHitBTC)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsHitBTC.size());
//        List<Candlestick> closesHitBTC = HitBTC.klines("BTCUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesHitBTCList = HitBTC.getValuesOfCandlestics(closesHitBTC, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesHitBTCList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesHitBTCList.size());
//        HashMap<String, BigDecimal> tickerHitBTC = HitBTC.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerHitBTC.get("lastPrice") + "\nPrice Change: " + tickerHitBTC.get("priceChange") + "\nPercent: " + tickerHitBTC.get("priceChangePercent") + "\nVolume: " + tickerHitBTC.get("volume"));

        //LIQUID
//        List<String> symbolsLiquid = Liquid.getSymbols();
//        for (String i : symbolsLiquid)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsLiquid.size());
//        List<Candlestick> closesLiquid = Liquid.klines("BTCUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesLiquidList = Liquid.getValuesOfCandlestics(closesLiquid, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesLiquidList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesLiquidList.size());
//        HashMap<String, BigDecimal> tickerLiquid = Liquid.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerLiquid.get("lastPrice") + "\nPrice Change: " + tickerLiquid.get("priceChange") + "\nPercent: " + tickerLiquid.get("priceChangePercent") + "\nVolume: " + tickerLiquid.get("volume"));

        //UPBIT
//        List<String> symbolsUpbit = Upbit.getSymbols();
//        for (String i : symbolsUpbit)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsUpbit.size());
//        List<Candlestick> closesUpbit = Upbit.klines("USDT-BTC", Interval.INT_1WEEK);
//        List<BigDecimal> closesUpbitList = Upbit.getValuesOfCandlestics(closesUpbit, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesUpbitList)
//            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesUpbitList.size());
//        HashMap<String, BigDecimal> tickerUpbit = Upbit.ticker24hr("USDT-BTC");
//        System.out.println("LastPrice: " + tickerUpbit.get("lastPrice") + "\nPrice Change: " + tickerUpbit.get("priceChange") + "\nPercent: " + tickerUpbit.get("priceChangePercent") + "\nVolume: " + tickerUpbit.get("volume"));

        //PROBIT 1DAY---1WEEK
//        List<String> symbolsProbit = Probit.getSymbols();
//        for (String i : symbolsProbit)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsProbit.size());
//        List<Candlestick> closesProbit = Probit.klines("BTC-USDT", Interval.INT_1DAY);
//        List<BigDecimal> closesProbitList = Probit.getValuesOfCandlestics(closesProbit, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesProbitList)
//            System.out.println(i);
//        System.out.println("Size: " + closesProbitList.size());
//        HashMap<String, BigDecimal> tickerProbit = Probit.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerProbit.get("lastPrice") + "\nPrice Change: " + tickerProbit.get("priceChange") + "\nPercent: " + tickerProbit.get("priceChangePercent") + "\nVolume: " + tickerProbit.get("volume"));

        //CURRENCYCOM
//        List<String> symbolsCurrencycom = Currencycom.getSymbols();
//        for (String i : symbolsCurrencycom)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsCurrencycom.size());
//        List<Candlestick> closesCurrencycom = Currencycom.klines("ETH/USD", Interval.INT_1WEEK);
//        List<BigDecimal> closesCurrencycomList = Currencycom.getValuesOfCandlestics(closesCurrencycom, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesCurrencycomList)
//            System.out.println(i);
//        System.out.println("Size: " + closesCurrencycomList.size());
//        HashMap<String, BigDecimal> tickerCurrencycom = Currencycom.ticker24hr("BTC/USDT");
//        System.out.println("LastPrice: " + tickerCurrencycom.get("lastPrice") + "\nPrice Change: " + tickerCurrencycom.get("priceChange") + "\nPercent: " + tickerCurrencycom.get("priceChangePercent") + "\nVolume: " + tickerCurrencycom.get("volume"));

        //BITMEX
//        List<String> symbolsBitMEX = BitMEX.getSymbols();
//        for (String i : symbolsBitMEX)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBitMEX.size());
//        List<Candlestick> closesBitMEX = BitMEX.klines("XBTUSD", Interval.INT_1DAY);
//        List<BigDecimal> closesBitMEXList = BitMEX.getValuesOfCandlestics(closesBitMEX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBitMEXList)
//            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesBitMEXList.size());
//        HashMap<String, BigDecimal> tickerBitMEX = BitMEX.ticker24hr("XBTEURU21");
//        System.out.println("LastPrice: " + tickerBitMEX.get("lastPrice") + "\nPrice Change: " + tickerBitMEX.get("priceChange") + "\nPercent: " + tickerBitMEX.get("priceChangePercent") + "\nVolume: " + tickerBitMEX.get("volume"));

        //BITBAY
//        List<String> symbolsBitBay = BitBay.getSymbols();
//        for (String i : symbolsBitBay)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBitBay.size());
//        List<Candlestick> closesBitBay = BitBay.klines("ADA-USDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesBitBayList = BitBay.getValuesOfCandlestics(closesBitBay, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBitBayList)
//            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesBitBayList.size());
//        HashMap<String, BigDecimal> tickerBitBay = BitBay.ticker24hr("BTC-USDT");
//        System.out.println("LastPrice: " + tickerBitBay.get("lastPrice") + "\nPrice Change: " + tickerBitBay.get("priceChange") + "\nPercent: " + tickerBitBay.get("priceChangePercent") + "\nVolume: " + tickerBitBay.get("volume"));

        //CHANGELLYPRO
//        List<String> symbolsChangellyPro = ChangellyPro.getSymbols();
//        for (String i : symbolsChangellyPro)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsChangellyPro.size());
//        List<Candlestick> closesChangellyPro = ChangellyPro.klines("BTCUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesChangellyProList = ChangellyPro.getValuesOfCandlestics(closesChangellyPro, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesChangellyProList)
//            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
//        System.out.println("Size: " + closesChangellyProList.size());
//        HashMap<String, BigDecimal> tickerChangellyPro = ChangellyPro.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerChangellyPro.get("lastPrice") + "\nPrice Change: " + tickerChangellyPro.get("priceChange") + "\nPercent: " + tickerChangellyPro.get("priceChangePercent") + "\nVolume: " + tickerChangellyPro.get("volume"));

        //COINDCX
//        List<String> symbolsCoinDCX = CoinDCX.getSymbols();
//        for (String i : symbolsCoinDCX)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsCoinDCX.size());
//        List<Candlestick> closesCoinDCX = CoinDCX.klines("B-BTC_USDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesCoinDCXList = CoinDCX.getValuesOfCandlestics(closesCoinDCX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesCoinDCXList)
//            System.out.println(i);
//        System.out.println("Size: " + closesCoinDCXList.size());
//        HashMap<String, BigDecimal> tickerCoinDCX = CoinDCX.ticker24hr("I-FIL_INR");
//        System.out.println("LastPrice: " + tickerCoinDCX.get("lastPrice") + "\nPrice Change: " + tickerCoinDCX.get("priceChange") + "\nPercent: " + tickerCoinDCX.get("priceChangePercent") + "\nVolume: " + tickerCoinDCX.get("volume"));

        //EXMO
//        List<String> symbolsExmo = Exmo.getSymbols();
//        for (String i : symbolsExmo)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsExmo.size());
//        List<Candlestick> closesExmo = Exmo.klines("BTC_USD", Interval.INT_1WEEK);
//        List<BigDecimal> closesExmoList = Exmo.getValuesOfCandlestics(closesExmo, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesExmoList)
//            System.out.println(i);
//        System.out.println("Size: " + closesExmoList.size());
//        HashMap<String, BigDecimal> tickerExmo = Exmo.ticker24hr("BTC_USD");
//        System.out.println("LastPrice: " + tickerExmo.get("lastPrice") + "\nPrice Change: " + tickerExmo.get("priceChange") + "\nPercent: " + tickerExmo.get("priceChangePercent") + "\nVolume: " + tickerExmo.get("volume"));

        //NOVADAX
//        List<String> symbolsNovaDAX = NovaDAX.getSymbols();
//        for (String i : symbolsNovaDAX)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsNovaDAX.size());
//        List<Candlestick> closesNovaDAX = NovaDAX.klines("BTC_USDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesNovaDAXList = NovaDAX.getValuesOfCandlestics(closesNovaDAX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesNovaDAXList)
//            System.out.println(i);
//        System.out.println("Size: " + closesNovaDAXList.size());
//        HashMap<String, BigDecimal> tickerNovaDAX = NovaDAX.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerNovaDAX.get("lastPrice") + "\nPrice Change: " + tickerNovaDAX.get("priceChange") + "\nPercent: " + tickerNovaDAX.get("priceChangePercent") + "\nVolume: " + tickerNovaDAX.get("volume"));

        //THEROCK 6hour---12hour---1DAY---1WEEK
//        List<String> symbolsTheRock = TheRock.getSymbols();
//        for (String i : symbolsTheRock)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsTheRock.size());
//        List<Candlestick> closesTheRock = TheRock.klines("BTCUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesTheRockList = TheRock.getValuesOfCandlestics(closesTheRock, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesTheRockList)
//            System.out.println(i);
//        System.out.println("Size: " + closesTheRockList.size());
//        HashMap<String, BigDecimal> tickerTheRock = TheRock.ticker24hr("BTCUSDT");
//        System.out.println("LastPrice: " + tickerTheRock.get("lastPrice") + "\nPrice Change: " + tickerTheRock.get("priceChange") + "\nPercent: " + tickerTheRock.get("priceChangePercent") + "\nVolume: " + tickerTheRock.get("volume"));

        //TIMEX
//        List<String> symbolsTimeX = TimeX.getSymbols();
//        for (String i : symbolsTimeX)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsTimeX.size());
//        List<Candlestick> closesTimeX = TimeX.klines("BTCUSDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesTimeXList = TimeX.getValuesOfCandlestics(closesTimeX, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesTimeXList)
//            System.out.println(i);
//        System.out.println("Size: " + closesTimeXList.size());
//        HashMap<String, BigDecimal> tickerTimeX = TimeX.ticker24hr("LTCAUDT");
//        System.out.println("LastPrice: " + tickerTimeX.get("lastPrice") + "\nPrice Change: " + tickerTimeX.get("priceChange") + "\nPercent: " + tickerTimeX.get("priceChangePercent") + "\nVolume: " + tickerTimeX.get("volume"));

        //WHITEBIT
//        List<String> symbolsWhiteBit = WhiteBit.getSymbols();
//        for (String i : symbolsWhiteBit)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsWhiteBit.size());
//        List<Candlestick> closesWhiteBit = WhiteBit.klines("BTC_USDT", Interval.INT_1WEEK);
//        List<BigDecimal> closesWhiteBitList = WhiteBit.getValuesOfCandlestics(closesWhiteBit, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesWhiteBitList)
//            System.out.println(i);
//        System.out.println("Size: " + closesWhiteBitList.size());
//        HashMap<String, BigDecimal> tickerWhiteBit = WhiteBit.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerWhiteBit.get("lastPrice") + "\nPrice Change: " + tickerWhiteBit.get("priceChange") + "\nPercent: " + tickerWhiteBit.get("priceChangePercent") + "\nVolume: " + tickerWhiteBit.get("volume"));

        //BITPANDA
//        List<String> symbolsBitpanda = Bitpanda.getSymbols();
//        for (String i : symbolsBitpanda)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsBitpanda.size());
//        List<Candlestick> closesBitpanda = Bitpanda.klines("BTC_EUR", Interval.INT_15MIN);
//        List<BigDecimal> closesBitpandaList = Bitpanda.getValuesOfCandlestics(closesBitpanda, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesBitpandaList)
//            System.out.println(i);
//        System.out.println("Size: " + closesBitpandaList.size());
//        HashMap<String, BigDecimal> tickerBitpanda = Bitpanda.ticker24hr("BTC_EUR");
//        System.out.println("LastPrice: " + tickerBitpanda.get("lastPrice") + "\nPrice Change: " + tickerBitpanda.get("priceChange") + "\nPercent: " + tickerBitpanda.get("priceChangePercent") + "\nVolume: " + tickerBitpanda.get("volume"));

        //MEXC
//        List<String> symbolsMEXC = MEXC.getSymbols();
//        for (String i : symbolsMEXC)
//            System.out.println(i);
//        System.out.println("Size: " + symbolsMEXC.size());
//        List<Candlestick> closesMEXC = MEXC.klines("BTC_USDT", Interval.INT_4HOURS);
//        List<BigDecimal> closesMEXCList = MEXC.getValuesOfCandlestics(closesMEXC, General.OHLCV.CLOSE);
//        for (BigDecimal i : closesMEXCList)
//            System.out.println(i);
//        System.out.println("Size: " + closesMEXCList.size());
//        HashMap<String, BigDecimal> tickerMEXC = MEXC.ticker24hr("BTC_USDT");
//        System.out.println("LastPrice: " + tickerMEXC.get("lastPrice") + "\nPrice Change: " + tickerMEXC.get("priceChange") + "\nPercent: " + tickerMEXC.get("priceChangePercent") + "\nVolume: " + tickerMEXC.get("volume"));
    }
}