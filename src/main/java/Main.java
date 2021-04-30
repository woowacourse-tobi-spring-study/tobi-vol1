import user.domain.SimpleConnectionMaker;
import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao(new SimpleConnectionMaker());
        /*
        클라이언트는 자기가 사용해야할 UserDao의 세부전략인 ConnectionMaker의 구현 클래스를 선택해야한다
        선택한 클래스의 오브젝트를 통해 UserDao와 런타임 연결관계를 맺어줄 수 있다
         */

        User user = new User();
        user.setId("inject");
        user.setName("inject");
        user.setPassword("applied");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공!");

        final User daoUser = dao.get(user.getId());
        System.out.println(daoUser.getName());
        System.out.println(daoUser.getPassword());
        System.out.println(daoUser.getId() + "조회 성공!");
    }
}
