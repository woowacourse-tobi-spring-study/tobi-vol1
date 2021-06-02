package springbook.user.domain;

import java.util.Arrays;

public enum Level {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Level valueOf(int value){
        return Arrays.stream(Level.values())
                .filter(level -> level.value == value)
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("해당 value를 가진 Level이 없습니다."));
    }
}
