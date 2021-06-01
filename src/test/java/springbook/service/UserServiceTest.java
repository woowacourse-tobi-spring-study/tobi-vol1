package springbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.dao.UserDao;
import springbook.domain.user.Level;
import springbook.domain.user.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDao userDao;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("bumjin", "a", "p1", Level.BASIC, 49, 0),
                new User("joytouch", "b", "p2", Level.BASIC, 50, 0),
                new User("erwins", "c", "p3", Level.SILVER, 60, 29),
                new User("madnite1", "d", "p4", Level.SILVER, 60, 30),
                new User("green", "e", "p5", Level.GOLD, 100, 100)
        );
    }

    @Test
    void upgradeLevels() throws SQLException {
        userDao.deleteAll();
        users.forEach(user -> userDao.addUser(user));

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    private void checkLevel(User user, boolean isUpgraded) {
        Level level = userDao.getUser(user.getId()).getLevel();
        if (isUpgraded) {
            assertThat(user.getLevel().getNext()).isEqualTo(level);
        } else {
            assertThat(user.getLevel()).isEqualTo(level);
        }
    }


    @Test
    void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.getUser(userWithLevel.getId());
        User userWithoutLevelRead = userDao.getUser(userWithoutLevel.getId());

        assertThat(userWithLevel.getLevel()).isEqualTo(userWithLevelRead.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void upgradeAllOrNothing() throws SQLException {
        UserService userService = new TestUserService(users.get(3).getId());
        userService.setUserDao(userDao);
        userService.setUserLevelUpgradePolicy(new NormalUserLevelUpgradePolicy());
        userService.setTransactionManager(new DataSourceTransactionManager(dataSource));
        
        userDao.deleteAll();
        users.forEach(user -> userDao.addUser(user));

        try {
            userService.upgradeLevels();
        } catch (IllegalArgumentException e) {
            System.out.println("hi");
        }

        checkLevel(users.get(1), false);
    }
}
