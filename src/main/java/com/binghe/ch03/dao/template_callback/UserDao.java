package com.binghe.ch03.dao.template_callback;

import com.binghe.ch03.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
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

    // 핵심 SQL문만을 컨텍스트의 콜백 객체에 넘겨서 실행하도록한다.
    public void deleteAll() throws SQLException {
        this.jdbcContext.executeSql("delete from users");
    }
}
