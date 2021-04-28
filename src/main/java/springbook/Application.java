package springbook;

import springbook.dao.user.UserDao;
import springbook.domain.user.User;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();

        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");
        userDao.addUser(user);
        System.out.println(user.getId() + "등록 성공");

        User user2 = userDao.getUser(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + "조회 성공");
    }
}
