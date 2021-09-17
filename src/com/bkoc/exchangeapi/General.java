package com.bkoc.exchangeapi;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class General {
    public enum OHLCV{
        OPEN,
        HIGH,
        LOW,
        CLOSE,
        VOLUME
    }

    public static List<BigDecimal> getValuesOfCandlestics(List<Candlestick> data, OHLCV type) {
        List<BigDecimal> temp = new LinkedList<>();
        switch (type){
            case OPEN:
                for(Candlestick cs : data)
                    temp.add(cs.getOpen().stripTrailingZeros()); break;

            case HIGH:
                for(Candlestick cs : data)
                    temp.add(cs.getHigh().stripTrailingZeros()); break;

            case LOW:
                for(Candlestick cs : data)
                    temp.add(cs.getLow().stripTrailingZeros()); break;

            case CLOSE:
                for(Candlestick cs : data)
                    temp.add(cs.getClose().stripTrailingZeros()); break;

            case VOLUME:
                for(Candlestick cs : data)
                    temp.add(cs.getVolume().stripTrailingZeros()); break;
        }
        return temp;
    }

    public static String response(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = (new OkHttpClient()).newCall(request).execute();
        return response.body().string();
    }
}
