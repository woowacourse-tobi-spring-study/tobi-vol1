package springbook.dao.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

    public PreparedStatement makeStatement(Connection connection) throws SQLException;
}
