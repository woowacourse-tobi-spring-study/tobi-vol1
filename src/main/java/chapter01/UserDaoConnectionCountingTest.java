package chapter01;

import chapter01.dao.CountingConnectionsMaker;
import chapter01.dao.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.connections.getConnection();

        CountingConnectionsMaker con = context.getBean("connections", CountingConnectionsMaker.class);
        System.out.println("con.getCounter() = " + con.getCounter());
    }
}
