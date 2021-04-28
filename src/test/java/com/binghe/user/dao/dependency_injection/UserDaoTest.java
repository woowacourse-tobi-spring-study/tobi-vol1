package com.binghe.user.dao.dependency_injection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.binghe.user.dao.ConnectionMaker;
import com.binghe.user.domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        // DB 연결 전략 - H2
        ConnectionMaker connectionMaker = new HConnectionMaker();
        userDao = new UserDao(connectionMaker);
        userDao.deleteAll();
    }

    @Test
    void insert_and_get() throws SQLException, ClassNotFoundException {
        // given
        String id = "binghe";
        User user = new User(id, "마크", "mark");

        // when
        userDao.add(user);
        User foundUser = userDao.get(id);

        // then
        assertAll(
            () -> assertThat(foundUser.getId()).isEqualTo(user.getId()),
            () -> assertThat(foundUser.getName()).isEqualTo(user.getName()),
            () -> assertThat(foundUser.getPassword()).isEqualTo(user.getPassword())
        );
    }
}
