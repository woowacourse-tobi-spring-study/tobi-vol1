package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoTest {
    @Autowired
    private UserDao userDao;
    private User wedge;

    @BeforeEach
    void beforeEach() throws SQLException, ClassNotFoundException {
        wedge = new User("1", "웨웨지지", "wedge123");
        userDao.delete();
    }

    @Test
    void 유저숫자를_확인한다() throws SQLException, ClassNotFoundException {
        //given
        //when
        for (int i = 0; i < 4; i++) {
            wedge.setId(String.valueOf(i));
            유저를_추가하고_확인한다();
        }
        //then
        assertThat(userDao.getCount()).isEqualTo(4);
    }

    @Test
    void 유저가_없다면_박살난다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> userDao.get("0"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void 유저를_추가하고_확인한다() throws SQLException, ClassNotFoundException {
        //given
        //when
        userDao.add(wedge);
        //then
        User expectedWedge = userDao.get(wedge.getId());
        assertThat(expectedWedge).isEqualTo(wedge);
        assertThat(expectedWedge.getName()).isEqualTo(wedge.getName());
        assertThat(expectedWedge.getPassword()).isEqualTo(wedge.getPassword());
    }

}