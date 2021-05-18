package com.binghe.ch03.dao.jdbc_context_with_di;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @제목 : 다섯번째 리팩토링. 전략과 컨텍스트가 동거해서 DAO마다 새롭게 구현해야 했던 문제해결을 위해 컨텍스트를 클래스로 분리
 *
 * @내용 : 이전에 JDBC 전략 패턴에서 매 DAO마다 구현해야 했던 jdbcContextWithStatementStrategy() 메서드를 UserDao 클래스 밖으로 독립시켜서 모든 DAO가 사용할 수 있게 해보자.
 *
 * @문제 :
 */

// 컨텍스트
public class JdbcContext {

    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            ps = stmt.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e){}
            }
        }
    }
}
