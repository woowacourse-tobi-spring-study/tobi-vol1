package springbook.user;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Level {
    GOLD(3, user -> false, null),
    SILVER(2, user -> user.getRecommend() >= 30, GOLD),
    BASIC(1, user -> user.getLogin() >= 50, SILVER);

    private final int value;
    private final Predicate<User> upgradeRule;
    private final Level next;

    Level(int value, Predicate<User> upgradeRule, Level next) {
        this.value = value;
        this.upgradeRule = upgradeRule;
        this.next = next;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.intValue() == value)
                .findAny().orElseThrow(() -> new AssertionError("Unknown value: " + value));
    }

    public boolean canUpgradeLevel(User user) {
        return this.upgradeRule.test(user);
    }

    public int intValue() {
        return value;
    }

    public Level next() {
        return this.next;
    }
}
