package com.epam.beans.aspects;

import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Aspect
@Component
@PropertySource({"classpath:aspects/aspects.properties"})
public class LuckyWinnerAspect {

    protected static final Set<String> luckyUsers = new HashSet<>();
    private final int luckyPercentage;

    @Autowired
    public LuckyWinnerAspect(@Value("${lucky.percentage}") int luckyPercentage) {
        this.luckyPercentage = luckyPercentage;
    }

    @Pointcut("(execution(* com.epam.beans.services.BookingService.bookTicket(com.epam.beans.models.User, com.epam.beans.models.Ticket)) && args(user, ticket))")
    private void bookTicket(User user, Ticket ticket) {
    }

    @Around("bookTicket(user, ticket)")
    public void countBookTicketByName(ProceedingJoinPoint joinPoint, User user, Ticket ticket) throws Throwable {
        final int randomInt = ThreadLocalRandom.current().nextInt(100 - luckyPercentage + 1);
        if (randomInt == 0) {
            Ticket luckyTicket = new Ticket(ticket.getEvent(), ticket.getDateTime(), ticket.getSeatsList(), ticket.getUser(), 0.0);
            luckyUsers.add(user.getEmail());
            joinPoint.proceed(new Object[] {user, luckyTicket});
        } else {
            joinPoint.proceed();
        }
    }

    public static List<String> getLuckyUsers() {
        return luckyUsers.stream().collect(Collectors.toList());
    }
}
