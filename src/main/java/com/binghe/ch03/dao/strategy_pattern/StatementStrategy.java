package com.binghe.ch03.dao.strategy_pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 전략 패턴에 사용되는 전략 인터페이스
 */
public interface StatementStrategy {

    PreparedStatement makePreparedStatement(Connection conn) throws SQLException;
}
