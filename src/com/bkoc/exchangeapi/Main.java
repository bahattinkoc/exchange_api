package com.bkoc.exchangeapi;

import com.bkoc.exchangeapi.exchanges.*;

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
    }
}