import user.domain.JoelUserDao;
import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new JoelUserDao();

        User user = new User();
        user.setId("joel610");
        user.setName("Joel");
        user.setPassword("helloJoel");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공!");

        final User daoUser = dao.get(user.getId());
        System.out.println(daoUser.getName());
        System.out.println(daoUser.getPassword());
        System.out.println(daoUser.getId() +  "조회 성공!");
    }
}
