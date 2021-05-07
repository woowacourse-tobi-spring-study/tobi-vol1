package springbook.user.dao;

public final class AccountDao {

    private final ConnectionMaker connectionMaker;

    public AccountDao(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
