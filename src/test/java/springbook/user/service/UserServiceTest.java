package springbook.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("wedge1", "웨지", "p1", Level.BASIC, Level.MIN_LOGCOUNT_WITH_SILVER - 1, 0),
                new User("wedge2", "웨지", "p2", Level.BASIC, Level.MIN_LOGCOUNT_WITH_SILVER, 0),
                new User("wedge3", "웨지", "p3", Level.SILVER, 60, Level.MIN_RECOMMEND_WITH_GOLD - 1),
                new User("wedge4", "웨지", "p4", Level.SILVER, 60, Level.MIN_RECOMMEND_WITH_GOLD),
                new User("wedge5", "웨지", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    void 레벨을_업그레이드_한다() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    private void checkLevel(User user, boolean isUpgrade) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel() != user.getLevel()).isEqualTo(isUpgrade);
    }
}
