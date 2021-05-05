package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

/**
 * UserDao의 관심사항
 * 1. DB와 연결을 위한 커넥션을 어떻게 가져올까?
 * 2. 파라미터로 넘어온 사용자 정보를 Statement에 바인딩시키고, Statement에 담긴 SQL을 디비를 통해 실행시키는 방법
 * 3. 작업이 끝나면 사용한 리소스인 Statement와 커넥션 객체를 닫아주는 것.
 */
public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement preparedStatement = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement preparedStatement = c.prepareStatement(
                "select * from users where id = ?"
        );
        preparedStatement.setString(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        preparedStatement.close();
        c.close();

        return user;
    }
}
