package springbook.user.dao;

public class DaoFactory {
    public UserDao UserDao() {
        return new UserDao(connectionMaker());
    }

    public SimpleConnectionMaker connectionMaker() {
        return new BaeminSimpleConnectionMaker();
    }
}
