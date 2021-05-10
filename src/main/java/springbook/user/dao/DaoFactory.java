package springbook.user.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(new DConnectionMaker());
    }
}
