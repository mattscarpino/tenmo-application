package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    boolean create(long user_id);
    Account getAccountsById(long user_id);

    Account getAccountByAccountId(long account_id);

    void update(long account_id, double balance);
}
