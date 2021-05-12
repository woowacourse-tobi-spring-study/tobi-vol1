package chapter01;

import chapter01.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        System.out.println("userDao = " + userDao.getClass());
        UserDao userDao2 = context.getBean("userDao", UserDao.class);
        System.out.println("userDao = " + userDao2.getClass());

    }
}