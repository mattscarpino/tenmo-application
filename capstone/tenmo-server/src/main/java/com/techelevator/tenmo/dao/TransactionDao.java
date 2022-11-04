package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.List;

public interface TransactionDao {

    boolean sendTransaction(long sender_id, long receiver_id, double transfer_amount);
    boolean createRequest(long sender_id, long receiver_id, double transfer_amount);
    boolean respondToRequest(long transaction_id, String status);
    List<Transaction> listTransactionsByUserId(long user_id);
    List<Transaction> listAllSentTransactions(long user_id);
    List<Transaction> listAllReceivedTransactions(long user_id);

    List<Transaction> listAllPendingTransactions(long user_id);

    Transaction findTransactionById(long transaction_id, long user_id);

    Transaction findAccountsByTransactionId(long transaction_id);
}
