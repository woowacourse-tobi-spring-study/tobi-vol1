package user.dao;

import user.connection.ConnectionMaker;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        /*
        클래스 사이에 관계가 만들어 진다는 것은 한 클래스가 인터페이스 없이 다른 클래스를 직접 사용한다는 뜻
        클래스가 아닌, 오브젝트-오브젝트 연결 관계가 설정이 되어야 함
        UserDao의 생성자에서 Connection을 생성하는 것은 얘의 역할이 아니야
        */
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeNewConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

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

    public int getCount2() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeNewConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = connectionMaker.makeNewConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = connectionMaker.makeNewConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}

/*
getConnection() 메서드는 어떤 Connection 클래스의 오브젝트를 어떻게 생성할 지를 결정하는 방법
서브 클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것을 "팩토리 메서드 패턴"이라고 한다

주로 인터페이스 타입으로 오브젝트를 리턴하기에, 서브클래스에서 정확히 어떤 클래스의 오브젝트 만들어 리턴할 지 슈퍼클래스는 모름
+ 관심도 없음

서브 클래스에서 오브젝트 생성 방법과 클래스를 결정할 수 있도록 미리 정의해둔 메서드를 팩토리 메서드라고 함
*/