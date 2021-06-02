package springbook.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    MailSender mailSender;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("wedge1", "웨지", "p1", Level.BASIC, Level.MIN_LOGCOUNT_WITH_SILVER - 1, 0, "fjzjqhdl@gmail.com"),
                new User("wedge2", "웨지", "p2", Level.BASIC, Level.MIN_LOGCOUNT_WITH_SILVER, 0, "fjzjqhdl@gmail.com"),
                new User("wedge3", "웨지", "p3", Level.SILVER, 60, Level.MIN_RECOMMEND_WITH_GOLD - 1, "fjzjqhdl@gmail.com"),
                new User("wedge4", "웨지", "p4", Level.SILVER, 60, Level.MIN_RECOMMEND_WITH_GOLD, "fjzjqhdl@gmail.com"),
                new User("wedge5", "웨지", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "fjzjqhdl@gmail.com")
        );
    }

    @Test
    @DirtiesContext
    void 레벨을_업그레이드_한다() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        MockMailSender mailSender = new MockMailSender();
        userService.setMailSender(mailSender);

        userService.upgradeLevels();
        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

        List<String> request = mailSender.getRequests();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkLevel(User user, boolean isUpgrade) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel() != user.getLevel()).isEqualTo(isUpgrade);
    }

    @Test
    public void upgradeAllOrNothing() {
        UserServiceImpl testUserService = new TestUserService(userDao, new DefaultUpgradePolicy(), platformTransactionManager, mailSender, users.get(3).getId());

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException");
        } catch (TestUserServiceException e) {
        }

        checkLevel(users.get(1), false);
    }

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<>();

        public List<String> getRequests() {
            return requests;
        }

        @Override
        public void send(SimpleMailMessage simpleMailMessage) throws MailException {
            requests.add(simpleMailMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {
        }

    }
}
