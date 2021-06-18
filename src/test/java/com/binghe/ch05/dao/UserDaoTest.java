package com.binghe.ch05.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.binghe.ch05.AppConfiguration;
import com.binghe.ch05.domain.Level;
import com.binghe.ch05.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfiguration.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        user1 = new User("binghe", "마크", "password", "binghe@test.com", Level.BASIC, 1, 0);
        user2 = new User("pika", "피카", "pikachu", "pika@test.com",Level.SILVER, 55, 10);
        user3 = new User("toby", "토비", "tobyspring", "toby@test.com",Level.GOLD, 100, 40);
    }

    @Test
    void addAndGet() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User userGet1 = userDao.get(user1.getId());
        checkSameUser(userGet1, user1);

        User userGet2 = userDao.get(user2.getId());
        checkSameUser(userGet2, user2);
    }

    @Test
    void getUserFailure() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDao.get("unknown_id"))
            .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void count() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getAll() {
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    void duplicateKey() {
        userDao.deleteAll();

        userDao.add(user1);
        assertThatThrownBy(() -> userDao.add(user1))
            .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void sqlExceptionTransalate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user2);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            DataAccessException transEx = set.translate(null, null, sqlEx);
            assertThat(transEx).isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    void update() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        User updateUser1 = new User(user1.getId(), "마크크", "pass", "mark@test.com", Level.GOLD, 1000, 999);
        userDao.update(updateUser1);

        User updatedUser1 = userDao.get(user1.getId());
        checkSameUser(updatedUser1, updateUser1);
        User sameUser2 = userDao.get(user2.getId());
        checkSameUser(sameUser2, user2);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}