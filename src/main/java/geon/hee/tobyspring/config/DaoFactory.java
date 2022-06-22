package geon.hee.tobyspring.config;

import geon.hee.tobyspring.repository.UserDaoJdbc;
import geon.hee.tobyspring.service.SendMailUserLevelUpgradePolicy;
import geon.hee.tobyspring.service.UserLevelUpgradePolicy;
import geon.hee.tobyspring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserService userService() {
        return new UserService(userLevelUpgradePolicy(), userDao(), platformTransactionManager());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new SendMailUserLevelUpgradePolicy(userDao());
    }

    @Bean
    public UserDaoJdbc userDao() {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/springbook");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
