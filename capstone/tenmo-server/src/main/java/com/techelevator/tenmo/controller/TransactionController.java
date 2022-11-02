package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
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
@RequestMapping("user/{sender_id}/transaction")
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

    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public List<String> listUsersForTransaction(){

        List<User> user = this.userDao.listAllUsers();
        List<String> idAndUsername = new ArrayList<>();
        for(User u : user){
            String word = "username: " + u.getUsername();
            idAndUsername.add(word);
            String www = "      user_id: " + u.getId();
            idAndUsername.add(www);
        }

        return idAndUsername;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{receiver_id}")
    public void makeTransaction(@RequestParam int sender_id, @RequestParam int receiver_id, @RequestParam double transfer_amount){

        Account userAccount = accountDao.getAccountsById(sender_id);

        if(userAccount.getBalance() < transfer_amount || transfer_amount <= 0 || sender_id == receiver_id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction.");
        }

        boolean b = transactionDao.create(sender_id,receiver_id,transfer_amount);

        if (!b) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to make transaction");
        }

        Account otherAccount = accountDao.getAccountsById(receiver_id);

        otherAccount.addToBalance(transfer_amount);
        userAccount.subtractFromBalance(transfer_amount);


        accountDao.update(userAccount.getAccount_id(), userAccount.getBalance());
        accountDao.update(otherAccount.getAccount_id(), otherAccount.getBalance());

    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<String> listAllTransactionByUser(Principal principal){

        String username = principal.getName();
        User user = userDao.findByUsername(username);

        List<Transaction> transactions = transactionDao.listTransactionsByUserId(user.getId());
        List<String> all = new ArrayList<>();

        for(Transaction t : transactions){
            all.add(t.toString());
        }

        return all;
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/sent")
    public List<String> listAllTransactionsSentByUser(Principal principal){

        String username = principal.getName();
        User user = userDao.findByUsername(username);

        List<Transaction> transactions = transactionDao.listAllSentTransactions(user.getId());
        List<String> all = new ArrayList<>();

        for(Transaction t : transactions){
            all.add(t.toString());
        }

        return all;
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/received")
    public List<String> listAllTransactionReceivedByUser(Principal principal){

        String username = principal.getName();
        User user = userDao.findByUsername(username);

        List<Transaction> transactions = transactionDao.listAllReceivedTransactions(user.getId());
        List<String> all = new ArrayList<>();

        for(Transaction t : transactions){
            all.add(t.toString());
        }

        return all;
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{transaction_id}")
    public String getTransactionById(@PathVariable int transaction_id,Principal principal){

        String username = principal.getName();
        User user = userDao.findByUsername(username);
        Transaction transaction = this.transactionDao.findTransactionById(transaction_id,user.getId());
        if (transaction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ID found");
        }
        return transaction.toString();
    }

}
