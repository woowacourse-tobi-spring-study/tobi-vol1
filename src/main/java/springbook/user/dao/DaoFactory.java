package springbook.user.dao;

public class DaoFactory {
    public static UserDao userDao() {
        UserDao userDao = new UserDao(simpleConnectionMaker());
        return userDao;
    }

    //TODO Connection 도 팩터리 클래스를 만든다면?
    private static ConnectionMaker simpleConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
