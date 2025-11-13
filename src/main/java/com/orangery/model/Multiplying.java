package com.orangery.model;

public enum Multiplying {
    LEAVES("leaves"),
    CUTTINGS("cuttings"),
    SEEDS("seeds");

    private final String value;

    Multiplying(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Multiplying fromString(String s) {
        for (Multiplying m : values()) {
            if (m.value.equalsIgnoreCase(s)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unknown multiplying: " + s);
    }
}