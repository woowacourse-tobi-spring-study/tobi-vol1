import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.dao.UserDao;
import user.domain.Level;
import user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactoryForTest.class)
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Autowired
    DataSource dataSource;

    @Before
    public void setUp() {
        dao.deleteAll();
    }

    @Test
    public void addAndGet() {
        final User user = new User("hello1", "world1", "pw1", Level.BASIC, 1, 0);
        dao.add(user);

        final User daoUser = dao.get(user.getId());
        checkSameUser(user, daoUser);

        assertThat(dao.getCount()).isEqualTo(1);
    }

    private void checkSameUser(User user, User daoUser) {
        assertThat(daoUser.getId()).isEqualTo(user.getId());
        assertThat(daoUser.getName()).isEqualTo(user.getName());
        assertThat(daoUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(daoUser.getLevel()).isEqualTo(user.getLevel());
        assertThat(daoUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(daoUser.getRecommend()).isEqualTo(user.getRecommend());
    }

    @Test
    public void getCount() {
        final User user1 = new User("hello1", "world1", "pw1", Level.BASIC, 1, 0);
        final User user2 = new User("hello2", "world2", "pw2", Level.SILVER, 55, 10);
        final User user3 = new User("hello3", "world3", "pw3", Level.GOLD, 100, 40);

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        assertThat(dao.getCount()).isEqualTo(3);
    }

    @Test
    public void getAll() {
        final User user1 = new User("hello1", "world1", "pw1", Level.BASIC, 1, 0);
        final User user2 = new User("hello2", "world2", "pw2", Level.SILVER, 55, 10);
        final User user3 = new User("hello3", "world3", "pw3", Level.GOLD, 100, 40);

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        final List<User> users = dao.getAll();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateKey() {
        final User user1 = new User("hello1", "world1", "pw1", Level.BASIC, 1, 0);
        dao.add(user1);
        dao.add(user1);
    }

    @Test
    public void sqlExceptionTranslate() {
        try {
            final User user1 = new User("hello1", "world1", "pw1", Level.BASIC, 1, 0);
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLExceptionTranslator SQLET = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            final DataAccessException dataAccessException = SQLET.translate(null, null, sqlEx);
            assertThat(dataAccessException).isInstanceOf(DuplicateKeyException.class);
        }
    }
}
