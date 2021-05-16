package springbook.dao.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("delete from users");
    }
}
