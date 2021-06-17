package springbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.dao.UserDao;
import springbook.domain.user.Level;
import springbook.domain.user.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private UserService testUserService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    private List<User> users;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDao mockUserDao;

    @Mock
    private MailSender mockMailSender;

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
    void upgradeLevels() {
        userServiceImpl.setUserLevelUpgradePolicy(new NormalUserLevelUpgradePolicy());
        when(mockUserDao.getAll()).thenReturn(users);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, Mockito.times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, Mockito.times(2)).send(simpleMailMessageArgumentCaptor.capture());

        List<SimpleMailMessage> mailMessages = simpleMailMessageArgumentCaptor.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
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
    @DirtiesContext
    void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        users.forEach(user -> userDao.addUser(user));

        try {
            testUserService.upgradeLevels();
        } catch (IllegalArgumentException e) {
            System.out.println("hi");
        }

        checkLevel(users.get(1), false);
    }

    @Test
    void advisorProxyCreator() {
        assertThat(testUserService).isInstanceOf(java.lang.reflect.Proxy.class);
    }

    static class TestUserServiceImpl extends UserServiceImpl {

        private String id = "madnite1";

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new IllegalArgumentException();
            }
            super.upgradeLevel(user);
        }
    }
}
