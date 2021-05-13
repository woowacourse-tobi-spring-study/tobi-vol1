package user.dao;

import user.domain.User;

import java.sql.*;

public class UserDao {
    private static UserDao INSTANCE;

    private ConnectionMaker connectionMaker;
    private Connection c;
    private User user;

    private UserDao() {
        DaoFactory daoFactory = new DaoFactory();
        this.connectionMaker = daoFactory.connectionMaker();
    }

    public static synchronized UserDao getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao(INSTANCE.connectionMaker);
        return INSTANCE;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection con = connectionMaker.makeConnection();

        PreparedStatement ps = con.prepareStatement(
            "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        this.c = connectionMaker.makeConnection();

        PreparedStatement ps = con.prepareStatement(
            "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        this.user.setId(rs.getString("id"));
        this.user.setName(rs.getString("name"));
        this.user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return this.user;
    }
}
