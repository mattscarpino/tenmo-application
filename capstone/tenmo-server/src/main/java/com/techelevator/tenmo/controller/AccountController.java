package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user/{user_id}/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Account> getAllAccountsForUser(@PathVariable int user_id){
        return this.accountDao.getAccountsById(user_id);
    }

}
