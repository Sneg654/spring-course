package com.epam.beans.configuration;

import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.AuditoriumDAO;
import com.epam.beans.daos.mocks.DBAuditoriumDAOMock;
import com.epam.beans.models.Auditorium;
import com.epam.beans.services.AuditoriumService;
import com.epam.beans.services.AuditoriumServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
        com.epam.beans.configuration.TestAspectsConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestAuditoriumConfiguration {

    @Bean
    public Auditorium testHall1() {
        return new Auditorium(1, "Test auditorium", 15, Arrays.asList(1, 2, 3, 4, 5));
    }

    @Bean
    public Auditorium testHall2() {
        return new Auditorium(2, "Test auditorium 2", 8, Collections.singletonList(1));
    }

    @Bean
    public AuditoriumDAO auditoriumDAOMock() {
        return new DBAuditoriumDAOMock(Arrays.asList(testHall1(), testHall2()));
    }

    @Bean
    public AuditoriumService auditoriumServiceImpl() {
        return new AuditoriumServiceImpl(auditoriumDAOMock());
    }
}
