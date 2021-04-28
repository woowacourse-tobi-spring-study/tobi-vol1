import java.sql.SQLException;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User();
        user.setId("12");
        user.setName("조이");
        user.setPassword("pw");

        userDao.add(user);

        System.out.println(user.getId() + " 등록성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회성공");
    }
}
