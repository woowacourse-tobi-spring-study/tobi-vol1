package dao.ioc;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeNewConnection() throws SQLException, ClassNotFoundException {
        return null;
    }
}
