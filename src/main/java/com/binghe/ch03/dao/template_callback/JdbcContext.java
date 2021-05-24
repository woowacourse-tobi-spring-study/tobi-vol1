package com.binghe.ch03.dao.template_callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @제목 : 여섯번째 리팩토링. 템플릿/콜백 패턴
 *
 * @내용 : 템플릿 -> 전략 패턴의 컨텍스트, 콜백 -> 익명 내부 클래스로 만들어지는 객체 (전략)
 */
public class JdbcContext {

    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // SQL Query문으로 실행되는 컨텍스트
    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement(query);
            }
        });
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
