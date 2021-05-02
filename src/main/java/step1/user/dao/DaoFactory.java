package step1.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker();

        return new UserDao(connectionMaker);
    }

}
