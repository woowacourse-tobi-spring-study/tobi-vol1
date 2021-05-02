package springbook.user.dao;

public final class DaoFactory {

    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(getConnectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(getConnectionMaker());
    }

    private ConnectionMaker getConnectionMaker() {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        return connectionMaker;
    }
}
