package springbook.user;

import java.util.Arrays;

public enum Level {
    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.intValue() == value)
                .findAny().orElseThrow(() -> new AssertionError("Unknown value: " + value));
    }

    public int intValue() {
        return value;
    }

    public Level next() {
        return this.next;
    }
}
