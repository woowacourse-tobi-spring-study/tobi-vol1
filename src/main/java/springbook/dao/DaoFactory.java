package springbook.dao;

import springbook.dao.connection.ConnectionMaker;
import springbook.dao.connection.SimpleConnectionMaker;
import springbook.dao.user.UserDao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    public UserDao accountantDao() {
        return new UserDao(connectionMaker());
    }

    public UserDao developerDao() {
        return new UserDao(connectionMaker());
    }

    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
