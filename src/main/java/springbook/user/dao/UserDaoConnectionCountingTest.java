package springbook.user.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            CountingDaoFactory.class);

        UserDao dao = context.getBean("userDao", UserDao.class);

        // DAO 사용코드
        CountingConnectionMaker countingConnectionMaker = context
            .getBean("connectionMaker", CountingConnectionMaker.class);

        System.out.println("Connection counter : " + countingConnectionMaker.getCounter());
    }
}
