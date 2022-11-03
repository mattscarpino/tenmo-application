package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userdao){
        this.userDao = userdao;
    }


    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public User getCurrentUser(Principal principal){
        return userDao.findByUsername(principal.getName());
    }


    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<String> listUsersForTransaction(){

        List<User> user = this.userDao.listAllUsers();
        List<String> idAndUsername = new ArrayList<>();
        for(User u : user){
            String word = "username: " + u.getUsername();
            idAndUsername.add(word);
            String www = "      user_id: " + u.getId();
            idAndUsername.add(www);
        }

        return idAndUsername;
    }
}
