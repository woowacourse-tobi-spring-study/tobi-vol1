package springbook.user.dao;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {
    @Test
    void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext(
                "applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        User user1 = new User();
        user1.setId("gyumee");
        user1.setName("박성철");
        user1.setPassword("springno1");

        dao.add(user1);

        User user2 = dao.get(user1.getId());

        assertThat(user2.getName()).isEqualTo(user1.getName());
        assertThat(user2.getPassword()).isEqualTo(user1.getPassword());
    }

}