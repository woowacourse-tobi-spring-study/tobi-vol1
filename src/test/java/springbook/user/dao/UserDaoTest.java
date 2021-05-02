package springbook.user.dao;

import org.junit.jupiter.api.Test;
import springbook.user.domain.User;

import java.sql.SQLException;

class UserDaoTest {
    UserDao userDao;

    @Test
    void add() throws SQLException, ClassNotFoundException {
        // given
        User user = new User();
        user.setId("woowa1");
        user.setName("우형이");
        user.setPassword("1234");

        //when
        userDao = new DaoFactory().userDao();

        //then
        userDao.add(user);
    }
}