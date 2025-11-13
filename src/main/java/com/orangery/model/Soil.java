package com.orangery.model;

public enum Soil {
    PODZOLIC("podzolic"),
    GROUND("ground"),
    SOD_PODZOLIC("sod-podzolic");

    private final String value;

    Soil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Soil fromString(String s) {
        for (Soil soil : values()) {
            if (soil.value.equalsIgnoreCase(s)) {
                return soil;
            }
        }
        throw new IllegalArgumentException("Unknown soil type: " + s);
    }
}