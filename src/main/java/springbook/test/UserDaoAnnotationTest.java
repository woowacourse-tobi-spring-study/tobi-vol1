package springbook.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.dao.DaoFactory;
import springbook.dao.UserDaoJdbc;
import springbook.domain.user.User;

import java.sql.SQLException;

public class UserDaoAnnotationTest {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc userDaoJdbc = applicationContext.getBean("userDao", UserDaoJdbc.class);
        User user = new User();

        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");
        userDaoJdbc.addUser(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = userDaoJdbc.getUser(user.getId());
        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }
    }
}
