package com.techelevator.tenmo.model;

public class Account {

    private long account_id;
    private long user_id;
    private double balance;

    public Account(long account_id, long user_id, double balance) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public Account(){

    }

    public long getAccount_id() {
        return account_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public double getBalance() {
        return balance;
    }

    public void addToBalance(double money){
        balance += money;
    }

    public void subtractFromBalance(double money){
        balance -= money;
    }

    @Override
    public String toString(){
        return "user_id: " + user_id + "\naccount_id: " + account_id + "\nbalance: " + balance;
    }
}
