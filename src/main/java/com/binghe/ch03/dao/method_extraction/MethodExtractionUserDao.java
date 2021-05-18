package com.binghe.ch03.dao.method_extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @제목 : 리팩토링 첫번째. 메서드 추출 기법을 이용하여 UserDao의 핵심로직을 분리
 *
 * @내용 : 핵심로직과 부가로직을 분리시키기 위한 첫번째 방안. 코드에서는 deleteAll()만 분리시켰다.
 *
 * @문제 : 부가로직을 따로 분리시키는 것이 좋은데, 이 방안은 핵심 로직을 분리시켰다.(반대가 되버림) 그러므로 코드가 더 복잡해졌다. 매 메서드마다 핵심로직에 대한 코드를 전부 따로 만들어줘야 한다. (중복 발생)
 */

public class MethodExtractionUserDao {

    private DataSource dataSource;

    public MethodExtractionUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException {
        // DB 연결 관심
        Connection con = null;

        // SQL 실행 관심
        PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();

            ps = deleteAllStatement(con);

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            ps.close();
            con.close();
        }
    }

    // deleteAll의 핵심 로직 메서드 추출
    private PreparedStatement deleteAllStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from users");
        return ps;
    }
}
