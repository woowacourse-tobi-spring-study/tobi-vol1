package springbook.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        UserDaoTestUtils.insertAndSelect(dao, "whiteship", "백기선", "married");

//        DaoFactory daoFactory = new DaoFactory();
//        UserDao dao1 = daoFactory.userDao();
//        UserDao dao2 = daoFactory.userDao();
//
//        System.out.println(dao1);
//        System.out.println(dao2);
//
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dao3 = context.getBean("userDao", UserDao.class);
//        UserDao dao4 = context.getBean("userDao", UserDao.class);
//
//        System.out.println(dao3);
//        System.out.println(dao4);
//        System.out.println(dao3 == dao4);
    }
}
