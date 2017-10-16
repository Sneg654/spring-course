package com.epam.beans.services;

import com.epam.beans.configuration.AppConfiguration;
import com.epam.beans.configuration.TestUserServiceConfiguration;
import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.mocks.UserDAOMock;
import com.epam.beans.models.User;
import com.epam.beans.models.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class, TestUserServiceConfiguration.class})
@Transactional
@WebAppConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserServiceImplTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Value("#{testUserServiceImpl}")
    private UserService userService;

    @Autowired
    private UserDAOMock userDAOMock;

    @Before
    public void init() {
        userDAOMock.init();
    }

    @After
    public void cleanup() {
        userDAOMock.cleanup();
    }

    @Test
    public void testRegister() throws Exception {
        User user = new User(999999L, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.REGISTERED_USER, LocalDate.now());
        long registeredId = userService.register(user).getId();
        assertEquals("User should be the same", userService.getUserByEmail(user.getEmail()), user.withId(registeredId));
    }

    @Test(expected = RuntimeException.class)
    public void testRegister_Exception() throws Exception {
        User testUser1 = (User) applicationContext.getBean("testUser1");
        userService.register(testUser1);
    }

    @Test
    public void testRemove() throws Exception {
        User testUser1 = (User) applicationContext.getBean("testUser1");
        userService.remove(testUser1);
        assertEquals("User should be the same", userService.getUserByEmail(testUser1.getEmail()), null);
    }

    @Test
    public void testUsersGetByName() throws Exception {
        User testUser1 = (User) applicationContext.getBean("testUser1");
        List<User> before = userService.getUsersByName(testUser1.getName());
        User addedUser =new User(-1, UUID.randomUUID().toString(), testUser1.getName(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.REGISTERED_USER, LocalDate.now());
        long registeredId = userService.register(addedUser).getId();
        List<User> after = userService.getUsersByName(testUser1.getName());
        before.add(addedUser.withId(registeredId));
        assertTrue("Users should change", before.containsAll(after));
        assertTrue("Users should change", after.containsAll(before));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User testUser1 = (User) applicationContext.getBean("testUser1");
        User foundUser = userService.getUserByEmail(testUser1.getEmail());
        assertEquals("User should match", testUser1, foundUser);
    }

    @Test
    public void testGetUserByEmail_Null() throws Exception {
        User foundUser = userService.getUserByEmail(UUID.randomUUID().toString());
        assertNull("There should not be such user", foundUser);
    }
}
