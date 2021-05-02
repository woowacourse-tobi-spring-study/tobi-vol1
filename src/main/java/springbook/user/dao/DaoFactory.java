package springbook.user.dao;

// userDao의 생성 책임을 맡은 팩토리 클래스
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new WoowaConnectionMaker();
    }
}
