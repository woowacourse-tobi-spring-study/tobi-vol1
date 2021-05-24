package com.binghe.ch03.dao.jdbc_context_with_di;

import com.binghe.ch03.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * UserDao는 클라이언트 역할을 한다. -> 컨텍스트에 전략을 생성하여 주입
 */
public class UserDao {

    private JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    // 클라이언트
    public void add(User user) throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            // 전략
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });
    }

    // 클라이언트
    public void deleteAll() throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            // 전략
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from users");
            }
        });
    }
}
