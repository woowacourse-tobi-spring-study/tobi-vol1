import dao.ConnectionMaker;
import dao.MysqlConnection;
import dao.UserDao;
import domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ConnectionMaker connectionMaker = new MysqlConnection();

        UserDao dao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("koda");
        user.setName("코다");
        user.setPassword("1234");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");
    }
}
