package springbook.domain.user;

import java.util.Arrays;

public enum Level {
    GOLD(3, null),
    SILVER(2, Level.GOLD),
    BASIC(1, Level.SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
    }

    public static Level of(int value) {
        return Arrays.stream(Level.values())
                .filter(t -> t.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getValue() {
        return value;
    }

    public Level getNext() {
        return next;
    }
}
