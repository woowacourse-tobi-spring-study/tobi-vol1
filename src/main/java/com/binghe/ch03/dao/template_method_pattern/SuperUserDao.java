package com.binghe.ch03.dao.template_method_pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @제목 : 리팩토링 두번째. 템플릿 메서드 패턴을 사용해서 핵심로직과 부가로직 분리
 *
 * @내용 : 슈퍼 클래스에서 부가로직(DB커넥션과 반납)을 담당하고, 서브 클래스에서 핵심로직(SQL문 실행)을 담당
 *        변하지 않는 부분은 슈퍼클래스에 두고 변하는 부 분은 추상 메소드로 정의해둬서 서브클래스에서 오버라이드하여 새롭게 정의해 쓰도록 하는 것이다.
 *
 * @문제 : 상속을 통한 구조는 느슨하지 않을뿐더러, 메서드 만큼 서브 클래스를 만들어줘야 한다. (상속 폭발 문제)
 *        게다가 컴파일 시점에 관계를 모두 정해줘야할 뿐 아니라, 스프링이라면 빈도 메서드만큼 여러개 만들어줘야 한다.
 */

public abstract class SuperUserDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException {
        // DB 연결 관심
        Connection con = null;

        // SQL 실행 관심
        PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();

            ps = makeStatement(con);
            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            ps.close();
            con.close();
        }
    }

    public abstract PreparedStatement makeStatement(Connection con) throws SQLException;
}
