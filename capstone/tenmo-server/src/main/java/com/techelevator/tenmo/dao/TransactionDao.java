package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.List;

public interface TransactionDao {

    boolean sendTransaction(int sender_id, int receiver_id, double transfer_amount);
    boolean createRequest(int sender_id, int receiver_id, double transfer_amount);
    boolean respondToRequest(int transaction_id, String status);
    List<Transaction> listTransactionsByUserId(long user_id);
    List<Transaction> listAllSentTransactions(long user_id);
    List<Transaction> listAllReceivedTransactions(long user_id);
    Transaction findTransactionById(int transaction_id, long user_id);

    Transaction findAccountsByTransactionId(int transaction_id);
}
