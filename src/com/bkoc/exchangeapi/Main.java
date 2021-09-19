package com.bkoc.exchangeapi;

import com.bkoc.exchangeapi.exchanges.*;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //FTX
        /*
        List<Candlestick> candlestickListFTX = FTX.klines("BTC-PERP", Interval.INT_1DAY);
        List<BigDecimal> closesFTX = FTX.getValuesOfCandlestics(candlestickListFTX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesFTX)
            System.out.println("Close: " + i);
        List<String> symbolsFTX = FTX.getSymbols(FTX.Permissions.future);
        for (String i : symbolsFTX)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerFTX = FTX.ticker24hr("BEAR/USD");
        System.out.println("LastPrice: " + tickerFTX.get("lastPrice") + "\nPrice Change: " + tickerFTX.get("priceChange") + "\nPercent: " + tickerFTX.get("priceChangePercent") + "\nVolume: " + tickerFTX.get("volume"));*/

        //BINANCE
        /*List<Candlestick> candlestickListFTX = Binance.klines("BTCUSDT", Interval.INT_1DAY, 300);
        List<BigDecimal> closesFTX = Binance.getValuesOfCandlestics(candlestickListFTX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesFTX)
            System.out.println("Close: " + i);
        List<String> symbolsFTX = Binance.getSymbols(Binance.Parite.USDT, Binance.Permissions.MARGIN);
        for (String i : symbolsFTX)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerFTX = Binance.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerFTX.get("lastPrice") + "\nPrice Change: " + tickerFTX.get("priceChange") + "\nPercent: " + tickerFTX.get("priceChangePercent") + "\nVolume: " + tickerFTX.get("volume"));*/

        //GATEIO
        /*List<String> gateioSpot = Gateio.getSymbols(Gateio.Permissions.SPOT);
        for (String i : gateioSpot)
            System.out.println(i);
        List<Candlestick> candlestickList = Gateio.klines("BTC_USDT", Interval.INT_1HOUR, 105);
        List<BigDecimal> closesGateio = Gateio.getValuesOfCandlestics(candlestickList, General.OHLCV.CLOSE);
        for (BigDecimal i : closesGateio)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerGateio = Gateio.ticker24hr("BTC_USDT");
        System.out.println("LastPrice: " + tickerGateio.get("lastPrice") + "\nPrice Change: " + tickerGateio.get("priceChange") + "\nPercent: " + tickerGateio.get("priceChangePercent") + "\nVolume: " + tickerGateio.get("volume"));*/

        //HUOBI
        /*List<String> symbolsHuobi = Huobi.getSymbols();
        for (String i : symbolsHuobi)
            System.out.println(i);
        List<Candlestick> candlesticksHuobi = Huobi.klines("btcusdt", Interval.INT_1HOUR, 300);
        List<BigDecimal> closesHuobi = Huobi.getValuesOfCandlestics(candlesticksHuobi, General.OHLCV.CLOSE);
        for (BigDecimal i : closesHuobi)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerGateio = Huobi.ticker24hr("btcusdt");
        System.out.println("LastPrice: " + tickerGateio.get("lastPrice") + "\nPrice Change: " + tickerGateio.get("priceChange") + "\nPercent: " + tickerGateio.get("priceChangePercent") + "\nVolume: " + tickerGateio.get("volume"));*/

        //KRAKEN
        /*List<String> symbolsKraken = Kraken.getSymbols();
        for (String i : symbolsKraken)
            System.out.println(i);
        List<Candlestick> candlesticksKraken = Kraken.klines("XBTUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesKraken = Kraken.getValuesOfCandlestics(candlesticksKraken, General.OHLCV.CLOSE);
        for (BigDecimal i : closesKraken)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerKraken = Kraken.ticker24hr("XXBTZUSD");
        System.out.println("LastPrice: " + tickerKraken.get("lastPrice") + "\nPrice Change: " + tickerKraken.get("priceChange") + "\nPercent: " + tickerKraken.get("priceChangePercent") + "\nVolume: " + tickerKraken.get("volume"));*/

        //KUCOIN
        /*List<String> symbolsKucoin = Kucoin.getSymbols();
        for (String i : symbolsKucoin)
            System.out.println(i);
        System.out.println("Size: " + symbolsKucoin.size());
        List<Candlestick> closesKucoin = Kucoin.klines("BTC-USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesKucoinList = Kucoin.getValuesOfCandlestics(closesKucoin, General.OHLCV.CLOSE);
        for (BigDecimal i : closesKucoinList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerKucoin = Kucoin.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerKucoin.get("lastPrice") + "\nPrice Change: " + tickerKucoin.get("priceChange") + "\nPercent: " + tickerKucoin.get("priceChangePercent") + "\nVolume: " + tickerKucoin.get("volume"));*/

        //COINBASEPRO
        /*List<String> symbolsCoinbasePro = CoinbasePro.getSymbols();
        for (String i : symbolsCoinbasePro)
            System.out.println(i);
        System.out.println("Size: " + symbolsCoinbasePro.size());
        List<Candlestick> candlesticksCoinbasePro = CoinbasePro.klines("BTC-USDT", Interval.INT_1HOUR);
        List<BigDecimal> candlesticksCoinbaseProList = CoinbasePro.getValuesOfCandlestics(candlesticksCoinbasePro, General.OHLCV.CLOSE);
        for (BigDecimal i :candlesticksCoinbaseProList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerCoinbasePro = CoinbasePro.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerCoinbasePro.get("lastPrice") + "\nPrice Change: " + tickerCoinbasePro.get("priceChange") + "\nPercent: " + tickerCoinbasePro.get("priceChangePercent") + "\nVolume: " + tickerCoinbasePro.get("volume"));*/

        //BITHUMB
        /*List<String> symbolsBithumb = Bithumb.getSymbols();
        for (String i : symbolsBithumb)
            System.out.println(i);
        System.out.println("Size: " + symbolsBithumb.size());
        List<Candlestick> candlesticksBithumb = Bithumb.klines("BTC_KRW", Interval.INT_30MIN);
        List<BigDecimal> candlesticksBithumbList = Bithumb.getValuesOfCandlestics(candlesticksBithumb, General.OHLCV.CLOSE);
        for (BigDecimal i :candlesticksBithumbList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerBithumb = Bithumb.ticker24hr("BTC_KRW");
        System.out.println("LastPrice: " + tickerBithumb.get("lastPrice") + "\nPrice Change: " + tickerBithumb.get("priceChange") + "\nPercent: " + tickerBithumb.get("priceChangePercent") + "\nVolume: " + tickerBithumb.get("volume"));*/

        //OKEX
        /*List<String> symbolsOkex = Okex.getSymbols();
        for (String i : symbolsOkex)
            System.out.println(i);
        List<Candlestick> candlesticksOkex = Okex.klines("BTC-USDT", Interval.INT_4HOURS);
        List<BigDecimal> candlesticksOkexList = Bithumb.getValuesOfCandlestics(candlesticksOkex, General.OHLCV.CLOSE);
        for (BigDecimal i :candlesticksOkexList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerOkex = Okex.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerOkex.get("lastPrice") + "\nPrice Change: " + tickerOkex.get("priceChange") + "\nPercent: " + tickerOkex.get("priceChangePercent") + "\nVolume: " + tickerOkex.get("volume"));*/

        //BITFINEX
        /*List<String> symbolsBitfinex = Bitfinex.getSymbols();
        for (String i : symbolsBitfinex)
            System.out.println(i);
        System.out.println("Size: " + symbolsBitfinex.size());
        List<Candlestick> candlesticksBitfinex = Bitfinex.klines("BTCUSD", Interval.INT_1HOUR);
        List<BigDecimal> csBitfinexList = Bitfinex.getValuesOfCandlestics(candlesticksBitfinex, General.OHLCV.CLOSE);
        for (BigDecimal i : csBitfinexList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerBitfinex = Bitfinex.ticker24hr(symbolsBitfinex.get(20));
        System.out.println(symbolsBitfinex.get(20));
        System.out.println("LastPrice: " + tickerBitfinex.get("lastPrice") + "\nPrice Change: " + tickerBitfinex.get("priceChange") + "\nPercent: " + tickerBitfinex.get("priceChangePercent") + "\nVolume: " + tickerBitfinex.get("volume"));*/

        //BITSTAMP
        /*List<String> symbolsBitstamp = Bitstamp.getSymbols();
        for (String i : symbolsBitstamp)
            System.out.println(i);
        List<Candlestick> candlesticksBitstamp = Bitstamp.klines("btcusdt", Interval.INT_30MIN);
        List<BigDecimal> csBitstampList = Bitstamp.getValuesOfCandlestics(candlesticksBitstamp, General.OHLCV.CLOSE);
        for (BigDecimal i : csBitstampList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerBitstamp = Bitstamp.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerBitstamp.get("lastPrice") + "\nPrice Change: " + tickerBitstamp.get("priceChange") + "\nPercent: " + tickerBitstamp.get("priceChangePercent") + "\nVolume: " + tickerBitstamp.get("volume"));*/

        //GEMINI
        /*List<String> symbolsGemini = Gemini.getSymbols();
        for (String i : symbolsGemini)
            System.out.println(i);
        System.out.println("Size: " + symbolsGemini.size());
        List<Candlestick> candlesticksGemini = Gemini.klines("btcusd", Interval.INT_30MIN);
        List<BigDecimal> csGeminiList = Gemini.getValuesOfCandlestics(candlesticksGemini, General.OHLCV.CLOSE);
        for (BigDecimal i : csGeminiList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerGemini = Gemini.ticker24hr("BTCUSD");
        System.out.println("LastPrice: " + tickerGemini.get("lastPrice") + "\nPrice Change: " + tickerGemini.get("priceChange") + "\nPercent: " + tickerGemini.get("priceChangePercent") + "\nVolume: " + tickerGemini.get("volume"));*/

        //BITTREX
        /*List<String> symbolsBittrex = Bittrex.getSymbols();
        for (String i : symbolsBittrex)
            System.out.println(i);
        System.out.println("Size: " + symbolsBittrex.size());
        List<Candlestick> closesBittrex = Bittrex.klines("BTC-USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesBittrexList = Bittrex.getValuesOfCandlestics(closesBittrex, General.OHLCV.CLOSE);
        for (BigDecimal i : closesBittrexList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerBittrex = Bittrex.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerBittrex.get("lastPrice") + "\nPrice Change: " + tickerBittrex.get("priceChange") + "\nPercent: " + tickerBittrex.get("priceChangePercent") + "\nVolume: " + tickerBittrex.get("volume"));*/

        //BYBIT
        /*List<String> symbolsByBit = ByBit.getSymbols();
        for (String i : symbolsByBit)
            System.out.println(i);
        System.out.println("Size: " + symbolsByBit.size());
        List<Candlestick> closesByBit = ByBit.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesByBitList = ByBit.getValuesOfCandlestics(closesByBit, General.OHLCV.CLOSE);
        for (BigDecimal i : closesByBitList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerByBit = ByBit.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerByBit.get("lastPrice") + "\nPrice Change: " + tickerByBit.get("priceChange") + "\nPercent: " + tickerByBit.get("priceChangePercent") + "\nVolume: " + tickerByBit.get("volume"));*/
    }
}