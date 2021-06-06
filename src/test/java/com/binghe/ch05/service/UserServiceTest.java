package com.binghe.ch05.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.binghe.ch05.TestAppConfiguration;
import com.binghe.ch05.dao.UserDao;
import com.binghe.ch05.domain.Level;
import com.binghe.ch05.domain.User;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfiguration.class)
class UserServiceTest {

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
            new User("binghe", "빙허", "password", "binghe@test.com", Level.BASIC, 49, 0),
            new User("jj", "멍청이", "babo", "jj@test.com", Level.BASIC, 50, 0),
            new User("ee", "토비토비", "toby", "ee@test.com",Level.SILVER, 60, 29),
            new User("mm", "포비포비", "poby", "mm@test.com",Level.SILVER, 60, 30),
            new User("gg", "갓", "god", "gg@test.com",Level.GOLD, 100, 100)
        );
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private MailSender mailSender;

    @Test
    void dependency() {
        assertThat(userService).isNotNull();
        assertThat(userDao).isNotNull();
    }

    @Test
    void upgradeLevels() throws SQLException {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(userDao, platformTransactionManager, mailSender, users.get(3).getId());

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            assertThatThrownBy(() -> testUserService.upgradeLevels())
                .isInstanceOf(TestUserServiceException.class);
        } catch (TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    static class TestUserService extends UserService {
        private String id;

        public TestUserService(UserDao userDao, PlatformTransactionManager platformTransactionManager, MailSender mailSender, String id) {
            super(userDao, platformTransactionManager, mailSender);
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}
