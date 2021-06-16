package springbook.service;

import springbook.domain.user.User;

public interface UserService {

    void upgradeLevels();

    void add(User user);
}
