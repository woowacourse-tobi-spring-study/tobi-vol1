package com.binghe.ch03.dao.jdbc_strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @제목 : 리팩토링 네번째. 마이크로 DI 방식의 JDBC 전략 패턴 (JdbcStrategy) -> 전략과 컨텍스트의 동거
 *        마이크로 DI : DI의 장점을 단순화해서 IoC 컨테이너의 도움 없이 코드 내에서 적용한 경우를 마이크로 DI라고도 한다. 또는 코드에 의한 DI라는 의미로 수동 DI라고 부를 수도 있다.
 *
 * @내용 : 컨텍스트가 어떤 전략을 사용할지 결정하는 것을 클라이언트에게 위임한다.
 *        가장 중요한 부분은 전략과 컨텍스트가 동거한다는 점이다. -> 클래스가 많아지는 문제를 해결할 수 있다.
 *
 * @문제 : UserDao가 아닌 ArticleDao등 다른 Dao를 만들때마다 jdbcContextWithStatementStrategy를 따로 구현해줘야 한다.
 */

public class JdbcStrategyUserDao {

    private DataSource dataSource;

    public JdbcStrategyUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // jdbc 컨텍스트 (부가기능)
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        // DB 연결 관심
        Connection conn = null;
        // SQL 실행 관심
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();

            ps = stmt.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            ps.close(); // SQLException으로 감싸줘야하는데 여기선 무시한다.
            conn.close();
        }
    }

    // 클라이언트
    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy (new StatementStrategy() {
                // 전략
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    return c.prepareStatement("delete from users");
                }
            }
        );
    }
}
