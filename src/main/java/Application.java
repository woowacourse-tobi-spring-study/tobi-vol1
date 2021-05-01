import dao.abstract_class.NUserDao;
import dao.class_separate.ConnectionMaker;
import dao.class_separate.DConnectionMaker;
import dao.class_separate.NConnectionMaker;
import dao.class_separate.UserDao;
import domain.User;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // N, C 교체 가능
        ConnectionMaker connectionMaker = new DConnectionMaker();
//        ConnectionMaker connectionMaker = new NConnectionMaker();

        UserDao userDao = new UserDao(connectionMaker);

        userDao.init();

        User user = new User();
        user.setId("pika");
        user.setName("윤지우");
        user.setPassword("pass");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());

        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
