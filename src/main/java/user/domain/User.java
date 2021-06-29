package user.domain;

import java.util.Objects;

public class User {
    public static final int MINIMUM_LOGIN_FOR_SILVER = 50;
    public static final int MINIMUM_RECOMMEND_FOR_GOLD = 30;
    public static final String DEFAULT_EMAIL = "mail@mail.com";

    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;
    String email;

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this(id, name, password, level, login, recommend, DEFAULT_EMAIL);
    }

    public User(String id, String name, String password, int level, int login, int recommend) {
        this(id, name, password, Level.valueOf(level), login, recommend, DEFAULT_EMAIL);
    }

    public User() {
    }

    public boolean canUpgradeLevel() {
        if (level == Level.BASIC && login >= MINIMUM_LOGIN_FOR_SILVER) {
            return true;
        }

        if (level == Level.SILVER && recommend >= MINIMUM_RECOMMEND_FOR_GOLD) {
            return true;
        }

        return false;
    }

    public void upgradeLevel() {
        if (Objects.isNull(level.nextLevel())) {
            throw new IllegalStateException(level + "은 레벨업이 불가합니다.");
        }
        level = level.nextLevel();
    }

    public void checkNewbie() {
        if (Objects.isNull(level)) {
            level = Level.BASIC;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = Level.valueOf(level);
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Level getLevel() {
        return level;
    }

    public int getLogin() {
        return login;
    }

    public int getRecommend() {
        return recommend;
    }

    public String getEmail() {
        return email;
    }
}
