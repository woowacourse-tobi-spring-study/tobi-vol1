package springbook.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void executeSql(String sql) throws SQLException {
        workWithStatementStrategy(connection -> connection.prepareStatement(sql));
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = stmt.makePreparedStatement(connection);
        ) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
