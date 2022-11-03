package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao{

    JdbcTemplate jdbcTemplate;
    JdbcAccountDao jdbcAccountDao;
    JdbcUserDao jdbcUserDao;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate, JdbcAccountDao jdbcAccountDao, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAccountDao = jdbcAccountDao;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public boolean sendTransaction(int sender_id, int receiver_id, double transfer_amount) {
        String sql = "INSERT INTO transaction(sender_id, receiver_id, transfer_amount, status)\n" +
                    "VALUES ((SELECT account_id FROM account WHERE user_id =?), " +
                "(SELECT account_id FROM account WHERE user_id =?), ?, 'approved') RETURNING transaction_id;";
        try{
            Integer newId = this.jdbcTemplate.queryForObject(sql, Integer.class, sender_id, receiver_id, transfer_amount);

        } catch (DataAccessException e){
            return false;
        }
        return true;
    }


    @Override
    public List<Transaction> listTransactionsByUserId(long user_id) {
        String sql = "SELECT transaction_id, transfer_amount, status\n" +
                "FROM transaction\n" +
                "WHERE sender_id = (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR receiver_id = (SELECT account_id FROM account WHERE user_id = ?);";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id,user_id);

        List<Transaction> transactions = new ArrayList<>();
        while(results.next()){
            Transaction transaction = mapRowSet(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Transaction> listAllSentTransactions(long user_id) {
        String sql = "SELECT transaction_id, transfer_amount, status\n" +
                "FROM transaction\n" +
                "WHERE sender_id = (SELECT account_id FROM account WHERE user_id = ?);";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id);

        List<Transaction> transactions = new ArrayList<>();
        while(results.next()){
            Transaction transaction = mapRowSet(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<Transaction> listAllReceivedTransactions(long user_id) {
        String sql = "SELECT transaction_id, transfer_amount, status\n" +
                "FROM transaction\n" +
                "WHERE receiver_id = (SELECT account_id FROM account WHERE user_id = ?);";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, user_id);

        List<Transaction> transactions = new ArrayList<>();
        while(results.next()){
            Transaction transaction = mapRowSet(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public Transaction findTransactionById(int transaction_id, long user_id) {
        String sql = "SELECT transaction_id, transfer_amount, status " +
                "FROM transaction " +
                "WHERE transaction_id = ? AND (sender_id = (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR receiver_id = (SELECT account_id FROM account WHERE user_id = ?));";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, transaction_id, user_id, user_id);
        Transaction transaction = null;
        if(results.next()){
            transaction = mapRowSet(results);
        }
        return transaction;
    }

    public Transaction mapRowSet(SqlRowSet rowSet){
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rowSet.getInt("transaction_id"));
        transaction.setTransfer_amount(rowSet.getDouble("transfer_amount"));
        transaction.setStatus(rowSet.getString("status"));
        return transaction;
    }

}
