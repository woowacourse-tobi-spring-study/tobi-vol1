package springbook;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.User;
import springbook.user.dao.CountingConnectionMaker;
import springbook.user.dao.CountingDaoFactory;
import springbook.user.dao.UserDao;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);

        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
