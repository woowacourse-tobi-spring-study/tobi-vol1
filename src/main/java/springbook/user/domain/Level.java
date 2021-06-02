package springbook.user.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;

public enum Level {
    BASIC(1, (login, recommend) -> login >= Level.MIN_LOGCOUNT_WITH_SILVER),
    SILVER(2, (login, recommend) -> recommend >= Level.MIN_RECOMMEND_WITH_GOLD),
    GOLD(3, (login, recommend) -> false);

    public static final int MIN_LOGCOUNT_WITH_SILVER = 50;
    public static final int MIN_RECOMMEND_WITH_GOLD = 30;

    private final int value;
    private final BiPredicate<Integer, Integer> levelUpPredicate;

    Level(int value, BiPredicate<Integer, Integer> levelUpPredicate) {
        this.value = value;
        this.levelUpPredicate = levelUpPredicate;
    }

    public int getValue() {
        return value;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(Level.values())
                .filter(level -> level.value == value)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 value를 가진 Level이 없습니다."));
    }

    public Level predicateLevel(int login, int recommend) {
        if (this.levelUpPredicate.test(login, recommend)) {
            return Level.valueOf(getValue() + 1);
        }
        return this;
    }
}
