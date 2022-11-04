package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    AccountDao accountDao;
    UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Account getAllAccountsForUser(Principal principal){

        User user = userDao.findByUsername(principal.getName());
        Account a = this.accountDao.getAccountsById(user.getId());
        if (a == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        } else {
            return a;
        }
    }

}
