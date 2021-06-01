package springbook.domain.user;

import java.util.Arrays;

public enum Level {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
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
}
