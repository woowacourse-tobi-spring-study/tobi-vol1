package springbook;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.User;
import springbook.user.dao.CountingConnectionMaker;
import springbook.user.dao.CountingDaoFactory;
import springbook.user.dao.UserDaoJdbc;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDaoJdbc userDaoJdbc = context.getBean("userDao", UserDaoJdbc.class);

        userDaoJdbc.deleteAll();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        userDaoJdbc.add(user);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);

        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
