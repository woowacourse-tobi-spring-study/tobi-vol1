package dao.counting;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);

        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
