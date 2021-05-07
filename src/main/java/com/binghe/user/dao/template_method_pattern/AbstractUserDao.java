package com.binghe.user.dao.template_method_pattern;

import com.binghe.user.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 템플릿 메서드 패턴
 */
public abstract class AbstractUserDao {

    public void add(User user) throws SQLException, ClassNotFoundException {

        // DB 관심을 서브 클래스에게 넘긴다. (팩토리 메서드)
        Connection con = getConnection();

        PreparedStatement ps = con.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        // DB 관심을 서브 클래스에게 넘긴다. (팩토리 메서드)
        Connection con = getConnection();

        PreparedStatement ps = con.prepareStatement("select * from users where id =?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        con.close();

        return user;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        // DB 연결 관심
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/toby", "sa", "");

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("DELETE FROM users");
        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }

    // 추상화를 통해 DB 커넥션에 대한 관심을 하위 클래스에게 넘길 수 있다. (DB 연결에 대한 전략을 하위 클래스에서 구현한다)
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
