import user.domain.ConnectionMaker;
import user.domain.SimpleConnectionMaker;
import user.domain.UserDao;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}

/*
UserDao의 객체 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 것!
오브젝트를 생성하는 책임을 가짐!
애플리케이션의 오브젝트를 구성하고, 그 관계를 정의하는 책임을 맡고 있음!
컴포넌트의 구조와 관계를 정의한 "설계도"
*/