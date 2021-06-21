package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import springbook.user.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoJdbcTest {
    @Autowired UserDao userDao;
    @Autowired DataSource dataSource;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test
    public void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlException)).isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    public void count() {

        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);

        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);

        userDao.add(user3);
        assertEquals(userDao.getCount(), 3);
    }

    @Test
    public void addAndGet() {

        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        userDao.add(user1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);

        User userGet1 = userDao.get(user1.getId());
        assertEquals(userGet1.getName(), user1.getName());
        assertEquals(userGet1.getPassword(), user1.getPassword());

        User userGet2 = userDao.get(user2.getId());
        assertEquals(userGet2.getName(), user2.getName());
        assertEquals(userGet2.getPassword(), user2.getPassword());
    }

    @Test
    public void duplicateKey() {
        userDao.deleteAll();

        userDao.add(user1);

        assertThatThrownBy(() -> userDao.add(user1)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    public void getUserFailure() {
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        assertThrows(EmptyResultDataAccessException.class, () -> userDao.get("unknown_id"));
    }

    @Test
    public void getAll() {
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0).hasSize(0);

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1).hasSize(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2).hasSize(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3).hasSize(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user, User otherUser) {
        assertThat(user).isEqualTo(otherUser);
    }
}