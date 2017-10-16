package com.epam.beans.controllers;

import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import com.epam.beans.models.UserAccount;
import com.epam.beans.services.UserAccountService;
import com.epam.beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    @Qualifier("userAccountServiceImpl")
    private UserAccountService userAccountServiceImpl;

    /**
     * List of users to get data from Database
     */
    private List<User> userList = new ArrayList<>();

    private List<UserAccount> accountList = new ArrayList<>();


    /**
     * Saves the static list of users in model and renders it
     * via freemarker template.
     *
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) throws Exception {
        userList = userServiceImpl.getAll();
        accountList = userAccountServiceImpl.getAll();
        model.addAttribute("userList", userList);
        model.addAttribute("accountList", accountList);
        return "users";
    }

    /**
     * Register new user
     *
     * @param user
     * @return Redirect to /index page to display user list
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user,
                            ModelMap model) {
        user.setUserRole("REGISTERED_USER"); //default value
        User registeredUser = userServiceImpl.register(user);
        model.addAttribute("userList", userList.add(registeredUser));
        model.addAttribute("message",
                "User " + user.getName() + " was successfully registered");
        return "redirect:users.ftl";
    }


    /**
     * Remove user
     *
     * @param user
     * @return Redirect to /users page to display user list
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    public String remove(@ModelAttribute("model") User user) {
        userServiceImpl.remove(user);
        return "redirect:users.ftl";
    }


    /**
     * Get booked by given user tickets
     *
     * @param user
     * @return Redirect to /booked-tickets page to display tickets list
     */
    @RequestMapping(value = "/getBookedTickets", method = RequestMethod.GET)
    public String getBookedTickets(@ModelAttribute("model") User user, ModelMap model) {
        List<Ticket> bookedTickets = userServiceImpl.getBookedTickets(user);
        model.addAttribute("bookedTickets", bookedTickets);
        return "booked-tickets";
    }

    /**
     * Find users by name
     *
     * @param name
     * @return Redirect to /users page to display user list
     */
    @RequestMapping(value = "/userByName", method = RequestMethod.GET)
    public String getByName(@ModelAttribute("name") String name, ModelMap model) throws Exception {
        if(!userList.isEmpty()) userList.clear();
        userList = userServiceImpl.getUsersByName(name);
        model.addAttribute("userList", userList);
        return "found-users";
    }

    /**
     * Find user by email
     *
     * @param email
     * @return Redirect to /users page to display user list
     */
    @RequestMapping(value = "/userByEmail", method = RequestMethod.GET)
    public String getByEmail(@ModelAttribute("email") String email, ModelMap model) throws Exception {
        User user = userServiceImpl.getUserByEmail(email);
        if(!userList.isEmpty()) userList.clear();
        userList.add(user);
        model.addAttribute("userList", userList);
        return "found-users";
    }

    /**
     * Refill user account
     *
     * @param userAccount
     * @return Redirect to /users page to display user list
     */
    @RequestMapping(value = "/refillUserAccount", method = RequestMethod.POST)
    public String refillUserAccount(@ModelAttribute("UserAccount") UserAccount userAccount, ModelMap model) throws Exception {
        userAccountServiceImpl.refillAccount(userAccount);
        accountList = userAccountServiceImpl.getAll();
        model.addAttribute("userList", userList);
        model.addAttribute("accountList", accountList);
        return "redirect:users";
    }


}