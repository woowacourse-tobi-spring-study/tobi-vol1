package springbook.user;

import java.util.Arrays;

public enum Level {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.getValue() == value)
                .findAny().orElseThrow(() -> new AssertionError("Unknown value: " + value));
    }

    public int getValue() {
        return value;
    }
}
