package tobi1.user.dao.templateMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDaoEx extends UserDao {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager
            .getConnection("jdbc:mysql://localhost/nabom", "nabom", "1234");
    }
}
