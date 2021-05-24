package springbook.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springbook.dao.UserDaoJdbc;
import springbook.domain.user.User;

import java.sql.SQLException;

public class UserDaoXmlTest {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDaoJdbc userDaoJdbc = applicationContext.getBean("userDao", UserDaoJdbc.class);
        User user = new User();

        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");
        userDaoJdbc.addUser(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = userDaoJdbc.getUser(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + "조회 성공");
    }
}
