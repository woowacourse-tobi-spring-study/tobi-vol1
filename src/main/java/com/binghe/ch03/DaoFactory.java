package com.binghe.ch03;

import com.binghe.ch03.dao.UserDao;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/toby");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
