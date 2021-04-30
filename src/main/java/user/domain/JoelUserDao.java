package user.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JoelUserDao extends UserDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:13306/springbook", "root", "root");
    }
}

/*
이렇게 슈퍼클래스에 기본적인 로직의 흐름을 만들고,
그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 protected 메서드 등으로 만든 뒤,
서브클래스에서 이런 메서드를 필요에 맞게 구현해서 사용하도록 하는 방법을
"템플릿 메서드 패턴" 이라고 한다.

슈퍼클래스의 기능을 확장하자!!
변하지 않는 기능은 슈퍼클래스에, 변경되고 확장될 기능은 서브클래스에!
슈퍼클래스에 미리 추상메서드를 만들어 이를 활용토록 하자
*/
