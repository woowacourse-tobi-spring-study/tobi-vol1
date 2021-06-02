package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoTest {
    private static User USER1 = new User("wedge1", "이름1", "springno1", Level.BASIC, 1, 0);
    private static User USER2 = new User("wedge2", "이름2", "springno2", Level.SILVER, 55, 10);
    private static User USER3 = new User("wedge3", "이름3", "springno3", Level.GOLD, 100, 40);

    @Autowired
    private UserDao userDao;
    private User wedge;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void beforeEach() throws SQLException, ClassNotFoundException {
        wedge = new User("wedge1", "이름1", "springno1", Level.BASIC, 1, 0);

        userDao.deleteAll();
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
        assertThat(expectedWedge.getLevel()).isEqualTo(wedge.getLevel());
        assertThat(expectedWedge.getLogin()).isEqualTo(wedge.getLogin());
        assertThat(expectedWedge.getRecommend()).isEqualTo(wedge.getRecommend());
    }

    @Test
    void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(USER1);
            userDao.add(USER1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set
                    = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlEx)).isInstanceOf(DataAccessException.class);
        }
    }

    @ParameterizedTest
    @MethodSource
    void 유저_전체를_확인한다(List<User> users) {
        users.forEach(user -> userDao.add(user));
        assertThat(userDao.getAll()).containsAll(users);
    }

    static Stream<Arguments> 유저_전체를_확인한다() {
        return Stream.of(
                Arguments.of(Arrays.asList(USER1)),
                Arguments.of(Arrays.asList(USER1, USER2)),
                Arguments.of(Arrays.asList(USER1, USER2, USER3))
        );
    }
}