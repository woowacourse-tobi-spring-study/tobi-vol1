package springbook.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static springbook.user.UserLevelUpgradePolicy.*;

class UserTest {
    private User user;
    private UserLevelUpgradePolicy levelUpgradePolicy;

    @BeforeEach
    void setUp() {
        user = new User();
        levelUpgradePolicy = new UserLevelUpgradePolicyNormal();
    }

    @Test
    void canUpgradeLevel() {
        user.setLevel(Level.BASIC);
        user.setLogin(MIN_LOGCOUNT_FOR_SILVER);
        user.setRecommend(0);

        boolean result = levelUpgradePolicy.canUpgradeLevel(user);

        assertThat(result).isTrue();
    }

    @Test
    void cannotUpgradeLevel() {
        user.setLevel(Level.BASIC);
        user.setLogin(MIN_LOGCOUNT_FOR_SILVER - 1);
        user.setRecommend(0);

        boolean result = levelUpgradePolicy.canUpgradeLevel(user);

        assertThat(result).isFalse();
    }

    @Test
    void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.next() == null) continue;
            user.setLevel(level);
            levelUpgradePolicy.upgradeLevel(user);
            assertThat(user.getLevel()).isEqualTo(level.next());
        }
    }

    @Test
    void upgradeLevelCannot() {
        Level[] levels = Level.values();
        assertThatThrownBy(() -> {
            for (Level level : levels) {
                if (level.next() != null) continue;
                user.setLevel(level);
                levelUpgradePolicy.upgradeLevel(user);
            }
        }).isInstanceOf(IllegalStateException.class);
    }
}