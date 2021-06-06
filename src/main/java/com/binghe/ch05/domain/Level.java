package com.binghe.ch05.domain;

import java.util.Arrays;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;
    private final Level nextLevel;

    Level(int value, Level nextLevel) {
        this.value = value;
        this.nextLevel = nextLevel;
    }

    public Level nextLevel() {
        return nextLevel;
    }

    public int intValue() {
        return value;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
            .filter(level -> level.intValue() == value)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException());
    }
}
