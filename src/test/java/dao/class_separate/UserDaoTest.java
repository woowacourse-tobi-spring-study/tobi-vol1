package dao.class_separate;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        userDao = new UserDao(connectionMaker);
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
