package chapter01;

import chapter01.dao.Connections;
import chapter01.dao.DConnections;
import chapter01.dao.UserDao;
import chapter01.domain.User;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connections connections = new DConnections();

        UserDao userDao = new UserDao(connections);
        User user = new User("1", "tobi", "bito");
        userDao.add(user);
        System.out.println("등록성공 아이디: " + user.getId());

        User user2 = userDao.get(user.getId());
        System.out.println("user2.getId() = " + user2.getId());
        System.out.println("user2.getName() = " + user2.getName());
    }
}
