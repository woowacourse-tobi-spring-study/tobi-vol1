package com.binghe.user.dao.class_separate;

import com.binghe.user.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 클래스 분리 UserDao
 */
public class UserDao {

    // DB 커넥션은 클래스 분리를 통해 구현
    private final SimpleConnectionMaker simpleConnectionMaker;

    public UserDao() {
        // 직접 의존성을 생성해줘도 되고, 외부에서 주입해줘도 된다.
        this.simpleConnectionMaker = new SimpleConnectionMaker();
    }

    public void add(User user) throws SQLException, ClassNotFoundException {

        // 다른 클래스로부터 커넥션을 가져온다.
        Connection con = simpleConnectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        // 다른 클래스로부터 커넥션을 가져온다.
        Connection con = simpleConnectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("select * from users where id =?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        // 리소스를 반환하는 관심
        rs.close();
        ps.close();
        con.close();

        return user;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {

        // 다른 클래스로부터 커넥션을 가져온다.
        Connection con = simpleConnectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("DELETE FROM users");
        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }
}
