import java.sql.*;

public class UserDao {
    public void add(User user) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/springbook?useSSL=false&serverTimezone=UTC&useUnicode=true&charEncoding=UTF-8", "root", "root");
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }


    public User get(String id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/springbook?useSSL=false&serverTimezone=UTC&useUnicode=true&charEncoding=UTF-8", "root", "root");
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE id =?");
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
}