package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    boolean create(int user_id);
    Account getAccountsById(long user_id);
    void update(int account_id, double balance);
    Account getAccountByAccountId(int account_id);
}
