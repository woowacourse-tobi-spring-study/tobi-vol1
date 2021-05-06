package springbook.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.dao.CountingDaoFactory;
import springbook.dao.connection.CountingConnectionMaker;
import springbook.dao.user.UserDao;
import springbook.domain.user.User;

import java.sql.SQLException;

public class UserDaoAnnotationTest {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        User user = new User();

        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");
        userDao.addUser(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = userDao.getUser(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + "조회 성공");

        CountingConnectionMaker countingConnectionMaker = applicationContext.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println(countingConnectionMaker.getCounter());
    }
}
