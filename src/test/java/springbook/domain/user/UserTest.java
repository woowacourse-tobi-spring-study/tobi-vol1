package springbook.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void upgradeLevel() {
        Level[] values = Level.values();
        for (Level level : values) {
            if (level.getNext() == null) {
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.getNext());
        }
    }

    @Test
    void cannotUpgradeLevel() {
        Level[] values = Level.values();
        for (Level level : values) {
            if (level.getNext() != null) {
                continue;
            }
            user.setLevel(level);
            assertThatCode(() -> user.upgradeLevel())
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
