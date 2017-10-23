package com.epam.beans.configuration;

import com.epam.beans.aspects.CounterAspect;
import com.epam.beans.aspects.DiscountAspect;
import com.epam.beans.aspects.LuckyWinnerAspect;
import com.epam.beans.aspects.mocks.DiscountAspectMock;
import com.epam.beans.aspects.mocks.LuckyWinnerAspectMock;
import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@Configuration
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
        com.epam.beans.configuration.TestAspectsConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class TestAspectsConfiguration extends TestBookingServiceConfiguration {

    @Bean
    public CounterAspect counterAspect() {
        return new CounterAspect();
    }

    @Bean
    DiscountAspect discountAspectMock() {
        return new DiscountAspectMock();
    }

    @Bean
    LuckyWinnerAspect luckyWinnerAspect() {
        return new LuckyWinnerAspectMock(99);
    }
}
