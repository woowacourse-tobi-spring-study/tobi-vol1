package dao;

import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {
    @Autowired


    @Test
    @DisplayName("추가와 가지고오기")
    public void addAndGet() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("asdf");
        user.setName("asdf");
        user.setPassword("asdf");

        dao.add(user);
        User user2 = dao.get(user.getId());
        assertThat(user2.getName()).isEqualTo(user.getName());
        assertThat(user2.getPassword()).isEqualTo(user2.getPassword());

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User qwer = new User();
        qwer.setId("qwer");
        qwer.setName("qwer");
        qwer.setPassword("qwer");

        dao.add(qwer);
        assertThat(dao.getCount()).isEqualTo(1);

        User qwer2 = dao.get(qwer.getId());
        assertThat(qwer2.getName()).isEqualTo(qwer.getName());
        assertThat(qwer2.getPassword()).isEqualTo(qwer.getPassword());

    }
}