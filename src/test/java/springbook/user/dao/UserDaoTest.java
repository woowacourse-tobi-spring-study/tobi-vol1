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
        user.setId("woowa");
        user.setName("우형이");
        user.setPassword("1234");

        //when
        userDao = new UserDao(new WoowaConnectionMaker());

        //then
        userDao.add(user);
    }
}