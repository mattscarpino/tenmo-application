package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    boolean create(int user_id);
    Account getAccountsById(int user_id);
    void update(int account_id, double balance);

}
