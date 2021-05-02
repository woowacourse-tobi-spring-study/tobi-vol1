package domain;

import dao.ConnectionMaker;
import dao.MysqlConnection;
import dao.UserDao;

public class DaoFactory {

    public UserDao userDao() {
        ConnectionMaker connectionMaker = new MysqlConnection();
        UserDao dao = new UserDao(connectionMaker);

        return dao;
    }
}
