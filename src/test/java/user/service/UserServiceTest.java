package user.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.DaoFactoryForTest;
import user.dao.UserDao;
import user.domain.Level;
import user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactoryForTest.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("Naul", "나얼", "p1", Level.BASIC, 49, 0),
                new User("SeongHoon", "성훈", "p2", Level.BASIC, 50, 0),
                new User("JeongYeop", "정엽", "p3", Level.SILVER, 60, 29),
                new User("YeongJoon", "영준", "p4", Level.SILVER, 60, 30),
                new User("BES", "브아솔", "p5", Level.GOLD, 100, 100)
        );

        userDao.deleteAll();
    }

    @Test
    public void upgradeLevels() {
        users.forEach(user -> userDao.add(user));

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        final User daoUser = userDao.get(user.getId());
        assertThat(daoUser.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    public void add() {
        final User userWithLevel = users.get(4);
        final User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        final User daoUserWithLevel = userDao.get(userWithLevel.getId());
        final User daoUserWithoutLevel = userDao.get(userWithoutLevel.getId());

        assertThat(daoUserWithLevel.getId()).isEqualTo(userWithLevel.getId());
        assertThat(daoUserWithoutLevel.getId()).isEqualTo(userWithoutLevel.getId());
    }
}
