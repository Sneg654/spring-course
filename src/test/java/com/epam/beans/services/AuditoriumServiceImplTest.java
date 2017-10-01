package com.epam.beans.services;

import com.epam.beans.configuration.AppConfiguration;
import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.mocks.DBAuditoriumDAOMock;
import com.epam.beans.models.Auditorium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class, com.epam.beans.configuration.TestAuditoriumConfiguration.class})
@Transactional
public class AuditoriumServiceImplTest {

    public static final int AUDITORIUMS_COUNT = 2;
    @Autowired
    private AuditoriumService   auditoriumService;
    @Autowired
    private ApplicationContext  applicationContext;
    @Autowired
    private DBAuditoriumDAOMock auditoriumDAOMock;

    private Auditorium testHall1;
    private Auditorium testHall2;

    @Before
    public void init() {
        auditoriumDAOMock.init();
        testHall1 = (Auditorium) applicationContext.getBean("testHall1");
        testHall2 = (Auditorium) applicationContext.getBean("testHall2");
    }

    @After
    public void cleanup() {
        auditoriumDAOMock.cleanup();
    }

    @Test
    public void testGetAuditoriums() throws Exception {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        assertEquals("Auditoriums number should match", AUDITORIUMS_COUNT, auditoriums.size());
    }

    @Test
    public void testGetByName() throws Exception {
        checkTestHall(testHall1);
        checkTestHall(testHall2);
    }

    @Test(expected = RuntimeException.class)
    public void testGetByName_Exception() throws Exception {
        auditoriumService.getSeatsNumber("bla-bla-bla");
    }

    private void checkTestHall(Auditorium testHall) {
        int seatsNumber = auditoriumService.getSeatsNumber(testHall.getName());
        List<Integer> vipSeats = auditoriumService.getVipSeats(testHall.getName());
        assertEquals("Auditorium seats number should match. Auditorium: [" + testHall + "]", testHall.getSeatsNumber(),
                     seatsNumber);
        assertEquals("Auditorium vip seats should match. Auditorium: [" + testHall + "]", testHall.getVipSeatsList(),
                     vipSeats);
    }
}
