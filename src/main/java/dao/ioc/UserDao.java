package dao.ioc;

import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    /*
     * User 추가
     */
    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    /*
    * User 정보 가져오기
    */
    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        rs.close();
        ps.close();
        c.close();
        return user;
    }

    /*
     * User 삭제
     */
    public void delete(String id) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "delete from users where id = ?");
        ps.setString(1, id);
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    /*
     * User 정보 초기화
     */
    public void init() throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "TRUNCATE TABLE users");
        ps.executeUpdate();
        ps.close();
        c.close();
    }
}
