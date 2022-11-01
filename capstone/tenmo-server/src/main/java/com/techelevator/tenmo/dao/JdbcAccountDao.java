package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(int user_id) {

        String sql = "INSERT INTO account (user_id, balance)\n" +
                "VALUES (?,1000);";
        try{
            jdbcTemplate.queryForObject(sql,Integer.class, user_id);

        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Account> getAccountsById(int user_id) {
        String sql = "SELECT account_id, user_id, balance\n" +
                "FROM account\n" +
                "WHERE user_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,user_id);
        List<Account> accounts = new ArrayList<>();
        while(result.next()){
            Account a = new Account(result.getInt("account_id"),
                                    result.getInt("user_id"),
                                    result.getDouble("balance"));
            accounts.add(a);
        }
        return accounts;
    }
}
