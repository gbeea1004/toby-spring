package geon.hee.tobyspring.config;

import geon.hee.tobyspring.config.connection.ConnectionMaker;
import geon.hee.tobyspring.config.connection.CountingConnectionMaker;
import geon.hee.tobyspring.config.connection.MyConnectionMaker;
import geon.hee.tobyspring.repository.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new MyConnectionMaker();
    }
}
