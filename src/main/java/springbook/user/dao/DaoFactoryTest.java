package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DaoFactoryTest {
    public static void main(String[] args) {
        UserDao userDao1 = DaoFactory.userDao();
        UserDao userDao2 = DaoFactory.userDao();

        System.out.println(userDao1);
        System.out.println(userDao2);
        System.out.println(userDao1 == userDao2);

        System.out.println();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao bean1 = context.getBean("userDao", UserDao.class);
        UserDao bean2 = context.getBean("userDao", UserDao.class);

        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean1 == bean2);
    }
}
