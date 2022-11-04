package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user/transaction")
@PreAuthorize("isAuthenticated()")
public class TransactionController {

    TransactionDao transactionDao;
    UserDao userDao;
    AccountDao accountDao;

    public TransactionController(TransactionDao transactionDao, UserDao userDao, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }


//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/send")
//    public void initiateTransaction(@RequestParam int sender_id, @RequestParam int receiver_id, @RequestParam double transfer_amount){
//
//        Account userAccount = accountDao.getAccountsById(sender_id);
//
//
//        if(userAccount.getBalance() < transfer_amount || transfer_amount <= 0 || sender_id == receiver_id){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction.");
//        }
//
//        boolean b = transactionDao.sendTransaction(sender_id,receiver_id,transfer_amount);
//
//        if (!b) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction");
//        }
//
//        Account otherAccount = accountDao.getAccountsById(receiver_id);
//
//        otherAccount.addToBalance(transfer_amount);
//        userAccount.subtractFromBalance(transfer_amount);
//
//
//        accountDao.update(userAccount.getAccount_id(), userAccount.getBalance());
//        accountDao.update(otherAccount.getAccount_id(), otherAccount.getBalance());
//
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/send")
    public void initiateTransaction(Principal principal, @RequestParam long receiver_id, @RequestParam double transfer_amount){

        User user = userDao.findByUsername(principal.getName());
        Account userAccount = accountDao.getAccountsById(user.getId());


        if(userAccount.getBalance() < transfer_amount || transfer_amount <= 0 || user.getId() == receiver_id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction.");
        }

        boolean b = transactionDao.sendTransaction(user.getId(),receiver_id,transfer_amount);

        if (!b) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction");
        }

        Account otherAccount = accountDao.getAccountsById(receiver_id);

        otherAccount.addToBalance(transfer_amount);
        userAccount.subtractFromBalance(transfer_amount);


        accountDao.update(userAccount.getAccount_id(), userAccount.getBalance());
        accountDao.update(otherAccount.getAccount_id(), otherAccount.getBalance());

    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/request")
//    public void initiateRequest(Principal principal, @RequestParam int sender_id, @RequestParam int receiver_id, @RequestParam double transfer_amount, @PathVariable int user_id){
//
//        User user = userDao.findByUsername(principal.getName());
//        Account account = accountDao.getAccountsById(user.getId());
//
//        if(user.getId() == sender_id || transfer_amount <= 0 || transfer_amount > account.getBalance()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer attempt");
//        }
//
//        transactionDao.createRequest(sender_id,receiver_id,transfer_amount);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/request")
    public void initiateRequest(Principal principal, @RequestParam long sender_id, @RequestParam double transfer_amount){
        User user = userDao.findByUsername(principal.getName());
        Account account = accountDao.getAccountsById(user.getId());

        if(user.getId() == sender_id || transfer_amount <= 0 || transfer_amount > account.getBalance()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer attempt");
        }

        transactionDao.createRequest(sender_id,user.getId(),transfer_amount);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/request/response")
    public void initiateResponseToRequest(@RequestParam long transaction_id, @RequestParam String status) {

        if(!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("rejected")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad status type");
        }

        transactionDao.respondToRequest(transaction_id, status);


        if(status.equalsIgnoreCase("approved")){

            Transaction transaction = this.transactionDao.findAccountsByTransactionId(transaction_id);
            Account senderAccount = accountDao.getAccountByAccountId(transaction.getSender_id());
            Account receiverAccount = accountDao.getAccountByAccountId(transaction.getReceiver_id());

            senderAccount.subtractFromBalance(transaction.getTransfer_amount());
            receiverAccount.addToBalance(transaction.getTransfer_amount());

            accountDao.update(senderAccount.getAccount_id(),senderAccount.getBalance());
            accountDao.update(receiverAccount.getAccount_id(), receiverAccount.getBalance());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<String> listAllTransactionByUser(Principal principal, @RequestParam String type){

        if(!type.equalsIgnoreCase("sent") && !type.equalsIgnoreCase("received") && !type.equalsIgnoreCase("pending")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect path");
        }

        String username = principal.getName();
        User user = userDao.findByUsername(username);

        List<Transaction> transactions;

        if(type.equalsIgnoreCase("sent")){

            transactions = transactionDao.listAllSentTransactions(user.getId());

        } else if(type.equalsIgnoreCase("received")){

            transactions = transactionDao.listAllReceivedTransactions(user.getId());

        } else{

            transactions = transactionDao.listAllPendingTransactions(user.getId());
        }
        List<String> all = new ArrayList<>();

        for(Transaction t : transactions){
            all.add(t.toString());
        }

        return all;
    }


    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/find")
    public String getTransactionById(@RequestParam long transaction_id,Principal principal){

        String username = principal.getName();
        User user = userDao.findByUsername(username);
        Transaction transaction = this.transactionDao.findTransactionById(transaction_id,user.getId());
        if (transaction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ID found");
        }
        return transaction.toString();
    }

}
