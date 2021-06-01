package springbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.dao.UserDao;
import springbook.domain.user.Level;
import springbook.domain.user.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Autowired
    private MailSender mailSender;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("bumjin", "a", "p1", "abc@naver.com", Level.BASIC, 49, 0),
                new User("joytouch", "b", "p2", "abc123@naver.com", Level.BASIC, 50, 0),
                new User("erwins", "c", "p3", "abcadf@naver.com", Level.SILVER, 60, 29),
                new User("madnite1", "d", "p4", "abc12312124@naver.com", Level.SILVER, 60, 30),
                new User("green", "e", "p5", "abafddafadc@naver.com", Level.GOLD, 100, 100)
        );
    }

    @Test
    void upgradeLevels() throws SQLException {
        userDao.deleteAll();
        users.forEach(user -> userDao.addUser(user));

        MockMailSender mailSender = new MockMailSender();
        userService.setMailSender(mailSender);

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

        List<String> request = mailSender.getRequest();
        assertThat(request).hasSize(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
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
    void upgradeAllOrNothing() {
        UserService userService = new TestUserService(users.get(3).getId());
        userService.setUserDao(userDao);
        userService.setUserLevelUpgradePolicy(new NormalUserLevelUpgradePolicy());
        userService.setTransactionManager(new DataSourceTransactionManager(dataSource));
        userService.setMailSender(mailSender);

        userDao.deleteAll();
        users.forEach(user -> userDao.addUser(user));

        try {
            userService.upgradeLevels();
        } catch (IllegalArgumentException e) {
            System.out.println("hi");
        }

        checkLevel(users.get(1), false);
    }

    static class MockMailSender implements MailSender {

        private List<String> requests = new ArrayList<>();

        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {
            requests.add(simpleMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage[] simpleMessages) throws MailException {

        }

        public List<String> getRequest() {
            return requests;
        }
    }
}
