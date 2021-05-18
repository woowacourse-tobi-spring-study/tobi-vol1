package com.binghe.ch03.dao.strategy_pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @제목 : 리팩토링 세번째. 전략 패턴 - 핵심로직(전략), 부가로직(컨텍스트)
 *
 * @내용 : DB 커넥션과 반환등의 부가로직은 Context로, SQL문 실행등 핵심로직은 Strategy 인터페이스를 구현한 전략 클래스로.
 *
 * @문제 : 컨텍스트가 직접 전략을 생성하고, 또한 이미 구체적인 클래스를 사용하도록 고정되어 있으므로 느슨하지 못한 코드가 되어버린다.
 *        전략 패턴은 필요에 따라 컨텍스트는 그대로 유지되면서(OCP의 폐쇄 원칙) 전략을 바꿔 쓸 수 있다(OCP의 개방 원칙)는 것인데,
 *        이렇게 컨텍스트 안에서 이미 구체적인 전략 클 래스인 DeleteAllStatement를 사용하도록 고정되어 있다면 뭔가 이상하다
 */

public class ContextUserDao {

    private DataSource dataSource;

    public ContextUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException {
        // DB 연결 관심
        Connection con = null;

        // SQL 실행 관심
        PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();

            // 전략 생성및 사용 (컨텍스트가 직접 전략을 생성하여 사용한다.)
            StatementStrategy strategy = new DeleteAllStrategy();
            ps = strategy.makePreparedStatement(con);
            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            ps.close();
            con.close();
        }
    }
}
