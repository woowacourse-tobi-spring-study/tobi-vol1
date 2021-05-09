package com.binghe.user.dao.spring_ioc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.binghe.user.domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = context.getBean("userDao", UserDao.class);
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