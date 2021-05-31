package dao;

import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDaoJdbc userDaoJdbc = context.getBean("userDao", UserDaoJdbc.class);

        User user = new User();
        user.setId("bepoz");
        user.setName("강승윤");
        user.setPassword("positive");

        userDaoJdbc.add(user);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter: " + ccm.getCounter());
    }
}
