package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public final class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D사 DB connection 생성코드
        return null;
    }
}
