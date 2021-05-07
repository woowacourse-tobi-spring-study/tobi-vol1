package com.binghe.user.dao.dao_factory;

import com.binghe.user.dao.ConnectionMaker;

/**
 * Dao의 생성 책임을 맡은 팩토리 클래스
 */
public class DaoFactory {

    public UserDao userDao() {
        // 펙토리의 메서드는 UserDao 타입의 객체를 어떻게 만들고, 어떻게 준비시킬지를 결정한다.
        ConnectionMaker connectionMaker = new HConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }

}
