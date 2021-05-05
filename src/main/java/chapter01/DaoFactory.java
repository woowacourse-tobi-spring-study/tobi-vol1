package chapter01;

import chapter01.dao.*;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connections());
    }

    public AccountDao accountDao() {
        return new AccountDao(connections());
    }

    public MessageDao messageDao() {
        return new MessageDao(connections());
    }

    private Connections connections() {
        return new DConnections();
    }
}
