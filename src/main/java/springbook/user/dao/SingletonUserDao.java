package springbook.user.dao;

public class SingletonUserDao {
    private static SingletonUserDao INSTANCE;

    private final ConnectionMaker connectionMaker;

    public SingletonUserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized SingletonUserDao getInstance() {
        if (INSTANCE == null) INSTANCE = new SingletonUserDao(new SimpleConnectionMaker());
        return INSTANCE;
    }
}
