package abstract_class;

import dao.abstract_class.NUserDao;
import dao.abstract_class.UserDao;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao = new NUserDao();
        userDao.init();
    }

    @Test
    void addTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("pika");
        user.setName("윤지우");
        user.setPassword("pass");

        userDao.add(user);

        assertThat(userDao.get(user.getId())).isEqualTo(user);
    }
}
