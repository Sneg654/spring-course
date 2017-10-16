package com.epam.beans.configuration;

import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.mocks.UserDAOMock;
import com.epam.beans.models.User;
import com.epam.beans.models.UserRole;
import com.epam.beans.services.UserService;
import com.epam.beans.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

@Configuration
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
        com.epam.beans.configuration.TestAspectsConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestUserServiceConfiguration {

    @Bean
    public User testUser1() {
        return new User(0, "dmitriy.vbabichev@gmail.com", "Dmytro Babichev", "Dmitro", "123456", UserRole.REGISTERED_USER, java.time.LocalDate.of(1992, 4, 29));
    }

    @Bean
    public User testUser2() {
        return new User(1, "laory@yandex.ru", "s Babichev", "Oleg", "123456", UserRole.REGISTERED_USER, java.time.LocalDate.of(1992, 4, 29));
    }

    @Bean
    public UserDAOMock userDAO() {
        return new UserDAOMock(Arrays.asList(testUser1(), testUser2()));
    }

    @Bean(name = "testUserServiceImpl")
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDAO());
    }
}
