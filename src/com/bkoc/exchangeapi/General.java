package com.bkoc.exchangeapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (!Objects.isNull(data)) {
            switch (type) {
                case OPEN:
                    for (Candlestick cs : data)
                        temp.add(cs.getOpen().stripTrailingZeros());
                    break;

                case HIGH:
                    for (Candlestick cs : data)
                        temp.add(cs.getHigh().stripTrailingZeros());
                    break;

                case LOW:
                    for (Candlestick cs : data)
                        temp.add(cs.getLow().stripTrailingZeros());
                    break;

                case CLOSE:
                    for (Candlestick cs : data)
                        temp.add(cs.getClose().stripTrailingZeros());
                    break;

                case VOLUME:
                    for (Candlestick cs : data)
                        temp.add(cs.getVolume().stripTrailingZeros());
                    break;
            }
            return temp;
        }
        return null;
    }

    public static String response(String urlLink) throws IOException {
        URL url = new URL(urlLink);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        String text = new BufferedReader(
                new InputStreamReader(responseStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        System.out.println(text);
        return text;
    }
}
