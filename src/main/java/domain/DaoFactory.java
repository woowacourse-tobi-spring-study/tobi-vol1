package domain;

import dao.ConnectionMaker;
import dao.MysqlConnection;
import dao.UserDao;

public class DaoFactory {

    public UserDao userDao() {
        UserDao dao = new UserDao(connectionMaker());
        return dao;
    }

    //추후 여러 DAO가 만들었을 때 중복 제거를 위한 메소드 분리
    public ConnectionMaker connectionMaker() {
        return new MysqlConnection();
    }
}
