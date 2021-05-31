package user.dao;

import user.dao.statement.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(final String... query) throws SQLException, ClassNotFoundException {
        workWithStatementStrategy(
                c -> {
                    if (query.length == 1) {
                        return c.prepareStatement(query[0]);
                    }
                    return makeFullQuery(c, query);
                }
        );
    }

    private PreparedStatement makeFullQuery(Connection c, String... query) throws SQLException {
        final String[] splitQuery = query[0].split("values");

        String sqlStatement = splitQuery[0];
        sqlStatement.replace("(\\(*\\))", "");

        String userParameter = splitQuery[1];

        for (int i = 1; i < query.length; i++) {
            userParameter = userParameter.replaceFirst("\\?", "\'" + query[i] + "\'");
        }

        String sql = sqlStatement + " values " + userParameter;
        return c.prepareStatement(sql);
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = this.dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (c != null) { try { c.close(); } catch (SQLException e) {} }
        }
    }
}
