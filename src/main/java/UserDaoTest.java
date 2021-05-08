import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
//        UserDao dao = new DaoFactory().userDao();
        /*
        제어의 역전 적용
        직접 자신이 어떤 ConnectionMaker 구현 클래스를 사용해 UserDao를 만드는지 결정하는 것이 아님
        DaoFactory가 찍어주는 대로 UserDao는 생성이 된다
        UserDaoTest도 사용할 오브젝트를 DaoFactory가 찍어주는 대로 사용해야함
         */

        User user = new User();
        user.setId("BEAN");
        user.setName("BEAN");
        user.setPassword("applied");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공!");

        final User daoUser = dao.get(user.getId());
        System.out.println(daoUser.getName());
        System.out.println(daoUser.getPassword());
        System.out.println(daoUser.getId() + "조회 성공!");
    }
}
