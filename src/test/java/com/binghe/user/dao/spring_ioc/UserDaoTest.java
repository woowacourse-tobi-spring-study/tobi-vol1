package com.binghe.user.dao.spring_ioc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.binghe.user.domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class UserDaoTest {

    private UserDao userDao;
    private ApplicationContext context;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        // SpringIoC 컨테이너 (빈 팩토리)를 생성한다. 생성하면서 `DaoFactory`라는 설정 정보를 이용하여 빈을 등록한다.
        context = new AnnotationConfigApplicationContext(DaoFactory.class);
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

    @DisplayName("스프링 IoC 컨테이너의 빈 싱글턴 테스트")
    @Test
    void singleton() {
        // given, when
        UserDao userDao1 = context.getBean("userDao", UserDao.class);
        UserDao userDao2 = context.getBean("userDao", UserDao.class);

        // then
        assertThat(userDao1).isSameAs(userDao2);
    }
}
