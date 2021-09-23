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

        //CRYPTOCOM
        /*List<String> symbolsCryptocom = Cryptocom.getSymbols();
        for (String i : symbolsCryptocom)
            System.out.println(i);
        System.out.println("Size: " + symbolsCryptocom.size());
        List<Candlestick> closesCryptocom = Cryptocom.klines("BTC_USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesCryptocomList = ByBit.getValuesOfCandlestics(closesCryptocom, General.OHLCV.CLOSE);
        for (BigDecimal i : closesCryptocomList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerCryptocom = Cryptocom.ticker24hr("BTC_USDT");
        System.out.println("LastPrice: " + tickerCryptocom.get("lastPrice") + "\nPrice Change: " + tickerCryptocom.get("priceChange") + "\nPercent: " + tickerCryptocom.get("priceChangePercent") + "\nVolume: " + tickerCryptocom.get("volume"));*/

        //BTCTURK
        /*List<String> symbolsBtcTurk = BtcTurk.getSymbols();
        for (String i : symbolsBtcTurk)
            System.out.println(i);
        System.out.println("Size: " + symbolsBtcTurk.size());
        List<Candlestick> closesBtcTurk = BtcTurk.klines("BTC_USDT");
        List<BigDecimal> closesBtcTurkList = BtcTurk.getValuesOfCandlestics(closesBtcTurk, General.OHLCV.CLOSE);
        for (BigDecimal i : closesBtcTurkList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerBtcTurk = BtcTurk.ticker24hr("BTC_USDT");
        System.out.println("LastPrice: " + tickerBtcTurk.get("lastPrice") + "\nPrice Change: " + tickerBtcTurk.get("priceChange") + "\nPercent: " + tickerBtcTurk.get("priceChangePercent") + "\nVolume: " + tickerBtcTurk.get("volume"));*/

        //POLONIEX
        /*List<String> symbolsPoloniex = Poloniex.getSymbols();
        for (String i : symbolsPoloniex)
            System.out.println(i);
        System.out.println("Size: " + symbolsPoloniex.size());
        List<Candlestick> closesPoloniex = Poloniex.klines("USDT_BTC", Interval.INT_2HOURS);
        List<BigDecimal> closesPoloniexList = Poloniex.getValuesOfCandlestics(closesPoloniex, General.OHLCV.CLOSE);
        for (BigDecimal i : closesPoloniexList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerPoloniex = Poloniex.ticker24hr("USDT_BTC");
        System.out.println("LastPrice: " + tickerPoloniex.get("lastPrice") + "\nPrice Change: " + tickerPoloniex.get("priceChange") + "\nPercent: " + tickerPoloniex.get("priceChangePercent") + "\nVolume: " + tickerPoloniex.get("volume"));*/

        //ASCENDEX
        /*List<String> symbolsAscendEX = AscendEX.getSymbols();
        for (String i : symbolsAscendEX)
            System.out.println(i);
        System.out.println("Size: " + symbolsAscendEX.size());
        List<Candlestick> closesAscendEX = AscendEX.klines("BTC/USDT", Interval.INT_2HOURS);
        List<BigDecimal> closesAscendEXList = AscendEX.getValuesOfCandlestics(closesAscendEX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesAscendEXList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerAscendEX = AscendEX.ticker24hr("BTC/USDT");
        System.out.println("LastPrice: " + tickerAscendEX.get("lastPrice") + "\nPrice Change: " + tickerAscendEX.get("priceChange") + "\nPercent: " + tickerAscendEX.get("priceChangePercent") + "\nVolume: " + tickerAscendEX.get("volume"));*/

        //HITBTC
        /*List<String> symbolsHitBTC = HitBTC.getSymbols();
        for (String i : symbolsHitBTC)
            System.out.println(i);
        System.out.println("Size: " + symbolsHitBTC.size());
        List<Candlestick> closesHitBTC = HitBTC.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesHitBTCList = HitBTC.getValuesOfCandlestics(closesHitBTC, General.OHLCV.CLOSE);
        for (BigDecimal i : closesHitBTCList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerHitBTC = HitBTC.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerHitBTC.get("lastPrice") + "\nPrice Change: " + tickerHitBTC.get("priceChange") + "\nPercent: " + tickerHitBTC.get("priceChangePercent") + "\nVolume: " + tickerHitBTC.get("volume"));*/

        //LIQUID
        /*List<String> symbolsLiquid = Liquid.getSymbols();
        for (String i : symbolsLiquid)
            System.out.println(i);
        System.out.println("Size: " + symbolsLiquid.size());
        List<Candlestick> closesLiquid = Liquid.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesLiquidList = Liquid.getValuesOfCandlestics(closesLiquid, General.OHLCV.CLOSE);
        for (BigDecimal i : closesLiquidList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerLiquid = Liquid.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerLiquid.get("lastPrice") + "\nPrice Change: " + tickerLiquid.get("priceChange") + "\nPercent: " + tickerLiquid.get("priceChangePercent") + "\nVolume: " + tickerLiquid.get("volume"));*/

        //UPBIT
        /*List<String> symbolsUpbit = Upbit.getSymbols();
        for (String i : symbolsUpbit)
            System.out.println(i);
        System.out.println("Size: " + symbolsUpbit.size());
        List<Candlestick> closesUpbit = Upbit.klines("USDT-BTC", Interval.INT_1HOUR);
        List<BigDecimal> closesUpbitList = Upbit.getValuesOfCandlestics(closesUpbit, General.OHLCV.CLOSE);
        for (BigDecimal i : closesUpbitList)
            System.out.println(i.setScale(2, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerUpbit = Upbit.ticker24hr("USDT-BTC");
        System.out.println("LastPrice: " + tickerUpbit.get("lastPrice") + "\nPrice Change: " + tickerUpbit.get("priceChange") + "\nPercent: " + tickerUpbit.get("priceChangePercent") + "\nVolume: " + tickerUpbit.get("volume"));*/

        //PROBIT
        /*List<String> symbolsProbit = Probit.getSymbols();
        for (String i : symbolsProbit)
            System.out.println(i);
        System.out.println("Size: " + symbolsProbit.size());
        List<Candlestick> closesProbit = Probit.klines("MONA-USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesProbitList = Probit.getValuesOfCandlestics(closesProbit, General.OHLCV.CLOSE);
        for (BigDecimal i : closesProbitList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerProbit = Probit.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerProbit.get("lastPrice") + "\nPrice Change: " + tickerProbit.get("priceChange") + "\nPercent: " + tickerProbit.get("priceChangePercent") + "\nVolume: " + tickerProbit.get("volume"));*/

        //CURRENCYCOM
        /*List<String> symbolsCurrencycom = Currencycom.getSymbols();
        for (String i : symbolsCurrencycom)
            System.out.println(i);
        System.out.println("Size: " + symbolsCurrencycom.size());
        List<Candlestick> closesCurrencycom = Currencycom.klines("ETH/USD", Interval.INT_1HOUR);
        List<BigDecimal> closesCurrencycomList = Currencycom.getValuesOfCandlestics(closesCurrencycom, General.OHLCV.CLOSE);
        for (BigDecimal i : closesCurrencycomList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerCurrencycom = Currencycom.ticker24hr("BTC/USDT");
        System.out.println("LastPrice: " + tickerCurrencycom.get("lastPrice") + "\nPrice Change: " + tickerCurrencycom.get("priceChange") + "\nPercent: " + tickerCurrencycom.get("priceChangePercent") + "\nVolume: " + tickerCurrencycom.get("volume"));*/

        //BITMEX
        /*List<String> symbolsBitMEX = BitMEX.getSymbols();
        for (String i : symbolsBitMEX)
            System.out.println(i);
        System.out.println("Size: " + symbolsBitMEX.size());
        List<Candlestick> closesBitMEX = BitMEX.klines("XBTUSD", Interval.INT_1HOUR);
        List<BigDecimal> closesBitMEXList = BitMEX.getValuesOfCandlestics(closesBitMEX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesBitMEXList)
            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerBitMEX = BitMEX.ticker24hr("XBTEURU21");
        System.out.println("LastPrice: " + tickerBitMEX.get("lastPrice") + "\nPrice Change: " + tickerBitMEX.get("priceChange") + "\nPercent: " + tickerBitMEX.get("priceChangePercent") + "\nVolume: " + tickerBitMEX.get("volume"));*/

        //BITBAY
        /*List<String> symbolsBitBay = BitBay.getSymbols();
        for (String i : symbolsBitBay)
            System.out.println(i);
        System.out.println("Size: " + symbolsBitBay.size());
        List<Candlestick> closesBitBay = BitBay.klines("BTC-USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesBitBayList = BitBay.getValuesOfCandlestics(closesBitBay, General.OHLCV.CLOSE);
        for (BigDecimal i : closesBitBayList)
            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerBitBay = BitBay.ticker24hr("BTC-USDT");
        System.out.println("LastPrice: " + tickerBitBay.get("lastPrice") + "\nPrice Change: " + tickerBitBay.get("priceChange") + "\nPercent: " + tickerBitBay.get("priceChangePercent") + "\nVolume: " + tickerBitBay.get("volume"));*/

        //CHANGELLYPRO
        /*List<String> symbolsChangellyPro = ChangellyPro.getSymbols();
        for (String i : symbolsChangellyPro)
            System.out.println(i);
        System.out.println("Size: " + symbolsChangellyPro.size());
        List<Candlestick> closesChangellyPro = ChangellyPro.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesChangellyProList = ChangellyPro.getValuesOfCandlestics(closesChangellyPro, General.OHLCV.CLOSE);
        for (BigDecimal i : closesChangellyProList)
            System.out.println(i.setScale(3, RoundingMode.HALF_UP));
        HashMap<String, BigDecimal> tickerChangellyPro = ChangellyPro.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerChangellyPro.get("lastPrice") + "\nPrice Change: " + tickerChangellyPro.get("priceChange") + "\nPercent: " + tickerChangellyPro.get("priceChangePercent") + "\nVolume: " + tickerChangellyPro.get("volume"));*/

        //COINDCX
        /*List<String> symbolsCoinDCX = CoinDCX.getSymbols();
        for (String i : symbolsCoinDCX)
            System.out.println(i);
        System.out.println("Size: " + symbolsCoinDCX.size());
        List<Candlestick> closesCoinDCX = CoinDCX.klines("B-BTC_USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesCoinDCXList = CoinDCX.getValuesOfCandlestics(closesCoinDCX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesCoinDCXList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerCoinDCX = CoinDCX.ticker24hr("I-FIL_INR");
        System.out.println("LastPrice: " + tickerCoinDCX.get("lastPrice") + "\nPrice Change: " + tickerCoinDCX.get("priceChange") + "\nPercent: " + tickerCoinDCX.get("priceChangePercent") + "\nVolume: " + tickerCoinDCX.get("volume"));*/

        //EXMO
        /*List<String> symbolsExmo = Exmo.getSymbols();
        for (String i : symbolsExmo)
            System.out.println(i);
        System.out.println("Size: " + symbolsExmo.size());
        List<Candlestick> closesExmo = Exmo.klines("BTC_USD", Interval.INT_1HOUR);
        List<BigDecimal> closesExmoList = Exmo.getValuesOfCandlestics(closesExmo, General.OHLCV.CLOSE);
        for (BigDecimal i : closesExmoList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerExmo = Exmo.ticker24hr("BTC_USD");
        System.out.println("LastPrice: " + tickerExmo.get("lastPrice") + "\nPrice Change: " + tickerExmo.get("priceChange") + "\nPercent: " + tickerExmo.get("priceChangePercent") + "\nVolume: " + tickerExmo.get("volume"));*/

        //NOVADAX
        /*List<String> symbolsNovaDAX = NovaDAX.getSymbols();
        for (String i : symbolsNovaDAX)
            System.out.println(i);
        System.out.println("Size: " + symbolsNovaDAX.size());
        List<Candlestick> closesNovaDAX = NovaDAX.klines("BTC_USDT", Interval.INT_1HOUR);
        List<BigDecimal> closesNovaDAXList = NovaDAX.getValuesOfCandlestics(closesNovaDAX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesNovaDAXList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerNovaDAX = NovaDAX.ticker24hr("BTC_USDT");
        System.out.println("LastPrice: " + tickerNovaDAX.get("lastPrice") + "\nPrice Change: " + tickerNovaDAX.get("priceChange") + "\nPercent: " + tickerNovaDAX.get("priceChangePercent") + "\nVolume: " + tickerNovaDAX.get("volume"));*/

        //THEROCK
        /*List<String> symbolsTheRock = TheRock.getSymbols();
        for (String i : symbolsTheRock)
            System.out.println(i);
        System.out.println("Size: " + symbolsTheRock.size());
        List<Candlestick> closesTheRock = TheRock.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesTheRockList = TheRock.getValuesOfCandlestics(closesTheRock, General.OHLCV.CLOSE);
        for (BigDecimal i : closesTheRockList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerTheRock = TheRock.ticker24hr("BTCUSDT");
        System.out.println("LastPrice: " + tickerTheRock.get("lastPrice") + "\nPrice Change: " + tickerTheRock.get("priceChange") + "\nPercent: " + tickerTheRock.get("priceChangePercent") + "\nVolume: " + tickerTheRock.get("volume"));*/

        //TIMEX
        /*List<String> symbolsTimeX = TimeX.getSymbols();
        for (String i : symbolsTimeX)
            System.out.println(i);
        System.out.println("Size: " + symbolsTimeX.size());
        List<Candlestick> closesTimeX = TimeX.klines("BTCUSDT", Interval.INT_1HOUR);
        List<BigDecimal> closesTimeXList = TimeX.getValuesOfCandlestics(closesTimeX, General.OHLCV.CLOSE);
        for (BigDecimal i : closesTimeXList)
            System.out.println(i);
        HashMap<String, BigDecimal> tickerTimeX = TimeX.ticker24hr("LTCAUDT");
        System.out.println("LastPrice: " + tickerTimeX.get("lastPrice") + "\nPrice Change: " + tickerTimeX.get("priceChange") + "\nPercent: " + tickerTimeX.get("priceChangePercent") + "\nVolume: " + tickerTimeX.get("volume"));*/
    }
}