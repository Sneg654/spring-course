package com.epam.beans.configuration;

import com.epam.beans.aspects.CounterAspect;
import com.epam.beans.aspects.DiscountAspect;
import com.epam.beans.aspects.LuckyWinnerAspect;
import com.epam.beans.aspects.mocks.DiscountAspectMock;
import com.epam.beans.aspects.mocks.LuckyWinnerAspectMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=false)
public class TestAspectsConfiguration extends TestBookingServiceConfiguration {

    @Bean
    public CounterAspect counterAspect() {
        return new CounterAspect();
    }

    @Bean
    DiscountAspect discountAspect() {
        return new DiscountAspectMock();
    }

    @Bean
    LuckyWinnerAspect luckyWinnerAspect() {
        return new LuckyWinnerAspectMock(99);
    }
}
