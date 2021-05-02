package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        // given
        User user = new User();
        user.setId("test1");
        user.setName("민정");
        user.setPassword("1234");

        //when-then
        userDao.add(user);
    }
}