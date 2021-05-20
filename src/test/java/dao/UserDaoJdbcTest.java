package dao;

import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserDaoJdbcTest {

    @Test
    @DisplayName("추가와 가지고오기")
    public void addAndGet() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc dao = context.getBean("userDaoJdbc", UserDaoJdbc.class);
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

    @Test
    @DisplayName("중복값 추가 확인")
    public void duplicateData() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDaoJdbc", UserDao.class);
        User user = new User();
        user.setId("kang");
        user.setName("seung");
        user.setPassword("yoon");

        dao.deleteAll();
        dao.add(user);

        assertThatThrownBy(() -> dao.add(user))
                .isInstanceOf(DataAccessException.class);
    }
}