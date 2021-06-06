package com.binghe.ch05.domain;

public class User {
    private String id;
    private String name;
    private String password;
    private String email;
    private Level level;
    private int login;
    private int recommend;

    public User(String id, String name, String password, String email, Level level, int login,
        int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void upgradeLevel() {
        Level nextLevel = level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalArgumentException("더 이상 레벨 업이 불가능합니다.");
        }
        this.level = nextLevel;
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
