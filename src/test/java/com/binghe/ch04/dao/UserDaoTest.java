package com.binghe.ch04.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.binghe.ch04.DaoFactory;
import com.binghe.ch04.domain.User;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    @Test
    void sqlExceptionTranslate() {
        userDao.deleteAll();

        User user1 = new User("mark", "binghe", "password");

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(dataSource);
//            System.out.println(set.translate(null, null, sqlEx));
            assertThat(set.translate(null, null, sqlEx)).isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    void addAndGet() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user1 = new User("mark", "binghe", "password");
        User user2 = new User("pika", "jiwoo", "password");

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User foundUser1 = userDao.get(user1.getId());
        User foundUser2 = userDao.get(user2.getId());

        assertAll(
            () -> assertThat(foundUser1.getId()).isEqualTo(user1.getId()),
            () -> assertThat(foundUser1.getName()).isEqualTo(user1.getName()),
            () -> assertThat(foundUser1.getPassword()).isEqualTo(user1.getPassword()),
            () -> assertThat(foundUser2.getId()).isEqualTo(user2.getId()),
            () -> assertThat(foundUser2.getName()).isEqualTo(user2.getName()),
            () -> assertThat(foundUser2.getPassword()).isEqualTo(user2.getPassword())
        );
    }

    @DisplayName("get 실패 테스트")
    @Test
    void get_negative() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDao.get("0"))
            .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void deteleAll() {
        User user1 = new User("binghe", "홍길동", "1234");
        User user2 = new User("toby", "토비", "4567");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    public void getCount() {
        User user1 = new User("binghe", "홍길동", "1234");
        User user2 = new User("toby", "토비", "4567");
        User user3 = new User("java", "자바", "8913");

        // 테스트 1
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        // 테스트 2
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        // 테스트 3
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        // 테스트 4
        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }
}