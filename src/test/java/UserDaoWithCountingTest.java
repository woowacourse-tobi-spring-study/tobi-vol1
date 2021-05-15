import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.CountingConnectionMaker;
import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

public class UserDaoWithCountingTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("CountJunit");
        user.setName("CountJunit");
        user.setPassword("applied");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공!");

        final User daoUser = dao.get(user.getId());
        System.out.println(daoUser.getName());
        System.out.println(daoUser.getPassword());
        System.out.println(daoUser.getId() + "조회 성공!");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("ccm.getCounter() = " + ccm.getCounter());
    }
}
