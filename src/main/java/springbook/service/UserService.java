package springbook.service;

import springbook.domain.user.User;

import java.util.List;

public interface UserService {

    void upgradeLevels();

    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    void update(User user);
}
