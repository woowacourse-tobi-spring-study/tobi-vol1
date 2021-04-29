package user;

import user.dao.NUserDao;
import user.dao.UserDao;
import user.domain.User;

import java.sql.SQLException;

public class UserApplication {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new NUserDao();

        User user = new User();
        user.setId("xrabcde");
        user.setName("ara");
        user.setPassword("root");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");
    }
}
