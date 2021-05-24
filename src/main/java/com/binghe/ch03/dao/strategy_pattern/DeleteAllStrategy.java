package com.binghe.ch03.dao.strategy_pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy {

    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from users");
        return ps;
    }
}
