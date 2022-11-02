package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user/{user_id}/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String getAllAccountsForUser(@PathVariable int user_id){
        Account a = this.accountDao.getAccountsById(user_id);
        if (a == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        } else {
            return a.toString();
        }
    }

}
