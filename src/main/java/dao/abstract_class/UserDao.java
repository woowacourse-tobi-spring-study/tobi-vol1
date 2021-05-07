package dao.abstract_class;

import domain.User;

import java.sql.*;

public abstract class UserDao {

    /*
     * User 추가
     */
    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection c = getConnection();
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

        Connection c = getConnection();
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

        Connection c = getConnection();
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

        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
                "TRUNCATE TABLE users");
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    /*
     * DB 연결 추상화 //h2, mySQL.. 등
     */
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
