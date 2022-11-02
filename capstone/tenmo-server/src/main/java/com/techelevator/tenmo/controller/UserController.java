package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userdao;

    public UserController(UserDao userdao){
        this.userdao = userdao;
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping()
//    public List<User> getAllUsers(){
//        return userdao.findAll();
//    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public User getCurrentUser(Principal principal){
        return userdao.findByUsername(principal.getName());
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/list")
//

}
