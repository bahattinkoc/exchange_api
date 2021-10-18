package com.bkoc.exchangeapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    public static String response(String url) throws IOException {
//        URL url = new URL(urlLink);
//        URLConnection connection = url.openConnection();
//        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
//        connection.setRequestProperty("accept", "application/json");
//        InputStream responseStream = connection.getInputStream();
//        String text = new BufferedReader(
//                new InputStreamReader(responseStream, StandardCharsets.UTF_8))
//                .lines()
//                .collect(Collectors.joining("\n"));
//        return text;
        try {
            OkHttpClient innerClient = new OkHttpClient.Builder()
                    .writeTimeout(5, TimeUnit.SECONDS) // write timeout
                    .readTimeout(5, TimeUnit.SECONDS) // read timeout
                    .build();
            Request request = new Request.Builder().url(url).build();
            Response response = (innerClient).newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            response.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
