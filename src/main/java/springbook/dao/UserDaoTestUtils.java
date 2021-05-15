package springbook.dao;

import springbook.domain.User;

import java.sql.SQLException;

public class UserDaoTestUtils {
    public static void insertAndSelect(UserDao dao, String id, String name, String password)
            throws ClassNotFoundException, SQLException {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
