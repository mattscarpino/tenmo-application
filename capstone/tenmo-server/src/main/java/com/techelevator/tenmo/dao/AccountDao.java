package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    boolean create(int user_id);
    List<Account> getAccountsById(int user_id);

}
