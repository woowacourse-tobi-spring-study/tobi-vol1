package com.binghe.ch03.dao.jdbc_template;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.binghe.ch03.domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(DaoFactory.class)
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @DisplayName("UserDao 의존성 확인")
    @Test
    void setUserDao() {assertNotNull(userDao);}

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
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
    void get_negative() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDao.get("0"))
            .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void deteleAll() throws SQLException, ClassNotFoundException {
        User user1 = new User("binghe", "홍길동", "1234");
        User user2 = new User("toby", "토비", "4567");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    public void getCount() throws SQLException, ClassNotFoundException {
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