package springbook.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.domain.user.Level;
import springbook.domain.user.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoJdbcTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = true)
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;
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
        user1 = new User("gyumee", "park", "springno1", "abc@naver.com", Level.BASIC, 1, 0);
        user2 = new User("leegw700", "lee", "springno2", "abafddac@naver.com", Level.SILVER, 55, 10);
        user3 = new User("bumjin", "박범진", "springno3", "abc11111@naver.com", Level.GOLD, 100, 40);
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isZero();

        userDao.addUser(user1);
        userDao.addUser(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User user1get = userDao.getUser(user1.getId());
        assertThat(user1get).isEqualTo(user1);

        User user2get = userDao.getUser(user2.getId());
        assertThat(user2get).isEqualTo(user2);
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

    @Test
    void getAll() {
        userDao.deleteAll();
        List<User> users0 = userDao.getAll();
        assertThat(users0).isEmpty();

        userDao.addUser(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1).hasSize(1);
        assertThat(user1).isEqualTo(users1.get(0));

        userDao.addUser(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2).hasSize(2);
        assertThat(user1).isEqualTo(users2.get(0));
        assertThat(user2).isEqualTo(users2.get(1));
    }

    @Test
    void duplicateKey() {
        userDao.deleteAll();
        userDao.addUser(user1);

        assertThatCode(() -> userDao.addUser(user1))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void sqlExceptionTranslate() {
        try {
            userDao.deleteAll();
            userDao.addUser(user1);
            userDao.addUser(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlEx)).isInstanceOf(DataAccessException.class);
        }
    }

    @Test
    void update() {
        userDao.deleteAll();
        userDao.addUser(user1);
        userDao.addUser(user2);

        user1.setName("another user");
        user1.setPassword("another pass");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        userDao.update(user1);
        User user1Update = userDao.getUser(user1.getId());
        User user2Get = userDao.getUser(user2.getId());

        assertThat(user1Update.equals(user1)).isTrue();
        assertThat(user2).isEqualTo(user2Get);
    }
}