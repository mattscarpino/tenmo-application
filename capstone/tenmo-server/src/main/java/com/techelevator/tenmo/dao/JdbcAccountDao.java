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
    public Account getAccountsById(int user_id) {
        String sql = "SELECT account_id, user_id, balance\n" +
                "FROM account\n" +
                "WHERE user_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,user_id);
        Account a = null;
        if (result.next()){
            a = new Account(result.getInt("account_id"),
                                    result.getInt("user_id"),
                                    result.getDouble("balance"));
        }
        return a;
    }

    @Override
    public void update(int account_id, double balance) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        this.jdbcTemplate.update(sql,balance,account_id);
    }
}
