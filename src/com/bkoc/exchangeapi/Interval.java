package com.bkoc.exchangeapi;

public enum Interval {
    INT_1MIN("1m"),
    INT_3MIN("3m"),
    INT_5MIN("5m"),
    INT_15MIN("15m"),
    INT_30MIN("30m"),

    INT_1HOUR("1h"),
    INT_2HOURS("2h"),
    INT_4HOURS("4h"),
    INT_6HOURS("6h"),
    INT_8HOURS("8h"),
    INT_12HOURS("12h"),

    INT_1DAY("1d"),
    INT_3DAYS("3d"),
    INT_1WEEK("1w"),
    INT_1MONTH("1M")
    ;

    private final String value;

    Interval(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Interval getEnum(String value) {
        for (Interval e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
