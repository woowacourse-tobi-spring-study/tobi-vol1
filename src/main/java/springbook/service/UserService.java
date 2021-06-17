package springbook.service;

import org.springframework.transaction.annotation.Transactional;
import springbook.domain.user.User;

import java.util.List;

@Transactional
public interface UserService {

    void upgradeLevels();

    void add(User user);

    @Transactional(readOnly = true)
    User get(String id);

    @Transactional(readOnly = true)
    List<User> getAll();

    void deleteAll();

    void update(User user);
}
