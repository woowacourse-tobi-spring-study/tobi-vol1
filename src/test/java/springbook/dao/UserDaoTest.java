package springbook.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import springbook.domain.user.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = true)
    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    public static void main(String[] args) {
        //JUnitCore.main("springbook.dao.UserDaoTest"); IDE 지원을 안 받고 실행한다면
    }

    @BeforeEach
    void setUp() {
        System.out.println(this);
        System.out.println(this.applicationContext);
        user1 = new User("gyumee", "park", "springno1");
        user2 = new User("leegw700", "lee", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isZero();

        userDao.addUser(user1);
        userDao.addUser(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User user1get = userDao.getUser(user1.getId());
        assertThat(user1get.getName()).isEqualTo(user1.getName());
        assertThat(user1get.getPassword()).isEqualTo(user1.getPassword());

        User user2get = userDao.getUser(user2.getId());
        assertThat(user2get.getName()).isEqualTo(user2.getName());
        assertThat(user2get.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isZero();

        assertThatCode(() -> userDao.getUser("unknown"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void count() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isZero();

        userDao.addUser(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.addUser(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.addUser(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }
}
