package com.binghe.user.dao.spring_ioc;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:h2:tcp://localhost/~/toby");
        simpleDriverDataSource.setUsername("sa");
        simpleDriverDataSource.setPassword("");
        return simpleDriverDataSource;
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(dataSource());
    }
}
