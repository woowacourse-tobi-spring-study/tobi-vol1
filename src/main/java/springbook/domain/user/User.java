package springbook.domain.user;

import java.util.Date;
import java.util.Objects;

public class User {

    private String id;
    private String name;
    private String password;
    private String email;
    private Level level;
    private int login;
    private int recommend;

    private Date lastUpgraded;

    public User() {
    }

    public User(String id, String name, String password, String email, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public Date getLastUpgraded() {
        return lastUpgraded;
    }

    public void setLastUpgraded(Date lastUpgraded) {
        this.lastUpgraded = lastUpgraded;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void upgradeLevel() {
        Level next = level.getNext();
        if (next == null) {
            throw new IllegalArgumentException("cannot upgrae this level " + level);
        }
        level = next;
        lastUpgraded = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login == user.login && recommend == user.recommend && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && level == user.level && Objects.equals(lastUpgraded, user.lastUpgraded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, level, login, recommend, lastUpgraded);
    }
}
