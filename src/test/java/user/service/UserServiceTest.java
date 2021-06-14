package user.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import user.DaoFactoryForTest;
import user.dao.UserDao;
import user.domain.Level;
import user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static user.domain.User.MINIMUM_LOGIN_FOR_SILVER;
import static user.domain.User.MINIMUM_RECOMMEND_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactoryForTest.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    MailSender mailSender;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("Naul", "나얼", "p1", Level.BASIC, MINIMUM_LOGIN_FOR_SILVER - 1, 0),
                new User("SeongHoon", "성훈", "p2", Level.BASIC, MINIMUM_LOGIN_FOR_SILVER, 0),
                new User("JeongYeop", "정엽", "p3", Level.SILVER, 60, MINIMUM_RECOMMEND_FOR_GOLD - 1),
                new User("YeongJoon", "영준", "p4", Level.SILVER, 60, MINIMUM_RECOMMEND_FOR_GOLD),
                new User("BES", "브아솔", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );

        userDao.deleteAll();
    }

    @Test
    public void upgradeLevels() {
        users.forEach(user -> userDao.add(user));

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        final User daoUser = userDao.get(user.getId());

        if (upgraded) {
            assertThat(daoUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
            return;
        }
        assertThat(daoUser.getLevel()).isEqualTo(user.getLevel());
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

    @Test
    public void upgradeAllOrNothingTransactional() {
        final UserService testUserService = new TestUserService(userDao, platformTransactionManager, mailSender, users.get(3).getId());

        users.forEach(user -> userDao.add(user));

        try {
            testUserService.upgradeLevels();
            fail("Test User Service 실패!");
        } catch (Exception e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    @DirtiesContext
    public void upgradeLevelsMocking() throws Exception {
        users.forEach(user -> userDao.add(user));

        final MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

        final List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size()).isEqualTo(2);
        assertThat(requests.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(requests.get(1)).isEqualTo(users.get(3).getEmail());
    }
}
