package step1.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        //N사의 독자적인 방법으로 connection을 생성하는 코드
        return null;
    }
}
