import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("interface");
        user.setName("interface");
        user.setPassword("applied");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공!");

        final User daoUser = dao.get(user.getId());
        System.out.println(daoUser.getName());
        System.out.println(daoUser.getPassword());
        System.out.println(daoUser.getId() + "조회 성공!");
    }
}
