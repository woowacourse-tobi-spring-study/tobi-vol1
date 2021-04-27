package com.binghe.user.dao.template_method_pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.binghe.user.domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractUserDaoTest {

    private AbstractUserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao = new HUserDao();
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