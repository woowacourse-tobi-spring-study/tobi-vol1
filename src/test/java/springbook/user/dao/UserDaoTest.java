package springbook.user.dao;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

class UserDaoTest {

    @Test
    void add() throws SQLException, ClassNotFoundException {
        //given
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("UserDao", UserDao.class);

        userDao.delete();

        User user = new User();
        user.setId("1");
        user.setName("웨웨지지");
        user.setPassword("wedge123");

        //when
        userDao.add(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + "조회 성공");
        //then
    }
}