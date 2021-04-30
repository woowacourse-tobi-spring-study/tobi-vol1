package user.domain;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        connectionMaker = new SimpleConnectionMaker();
        /*
        이런 방식은 내부 구현체에 의존적이기 떄문에 확장에 한계가 있음
        UserDao가 DB커넥션을 담당하는 클래스에 대해 "너무 많이 알고있음"!!
        DB 커넥션을 제공하는 클래스에 대한 구체적인 정보는 제거했지만,
        초기에 한 반 어떤 클래스의 오브젝트를 사용할지 결정하는 생성자 코드는 남아있다!
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
}

/*
getConnection() 메서드는 어떤 Connection 클래스의 오브젝트를 어떻게 생성할 지를 결정하는 방법
서브 클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것을 "팩토리 메서드 패턴"이라고 한다

주로 인터페이스 타입으로 오브젝트를 리턴하기에, 서브클래스에서 정확히 어떤 클래스의 오브젝트 만들어 리턴할 지 슈퍼클래스는 모름
+ 관심도 없음

서브 클래스에서 오브젝트 생성 방법과 클래스를 결정할 수 있도록 미리 정의해둔 메서드를 팩토리 메서드라고 함
*/