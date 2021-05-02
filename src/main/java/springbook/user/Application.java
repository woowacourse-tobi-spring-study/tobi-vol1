package springbook.user;

import java.sql.SQLException;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.NConnectionMaker;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new DaoFactory().userDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");
    }
}
