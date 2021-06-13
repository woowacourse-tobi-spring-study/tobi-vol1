package user.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        user.setLevel(Level.BASIC);
        user.setLogin(100);
        user.upgradeLevel();
        assertThat(user.getLevel()).isEqualTo(Level.SILVER);

        user.setLevel(Level.SILVER);
        user.setRecommend(100);
        user.upgradeLevel();
        assertThat(user.getLevel()).isEqualTo(Level.GOLD);

        user.setLevel(Level.GOLD);
        user.upgradeLevel();
        assertThat(user.getLevel()).isEqualTo(Level.GOLD);
    }
}
