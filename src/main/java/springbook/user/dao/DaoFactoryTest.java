package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DaoFactoryTest {
    public static void main(String[] args) {
        UserDaoJdbc userDaoJdbc1 = DaoFactory.userDao();
        UserDaoJdbc userDaoJdbc2 = DaoFactory.userDao();

        System.out.println(userDaoJdbc1);
        System.out.println(userDaoJdbc2);
        System.out.println(userDaoJdbc1 == userDaoJdbc2);

        System.out.println();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDaoJdbc bean1 = context.getBean("userDao", UserDaoJdbc.class);
        UserDaoJdbc bean2 = context.getBean("userDao", UserDaoJdbc.class);

        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean1 == bean2);
    }
}
