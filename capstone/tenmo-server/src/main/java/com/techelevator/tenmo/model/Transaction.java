package com.techelevator.tenmo.model;

public class Transaction {

    private long transaction_id;
    private long sender_id;
    private long receiver_id;
    private double transfer_amount;
    private String status;

    public Transaction(){

    }

    public Transaction(long transaction_id, long sender_id, long receiver_id, double transfer_amount, String status) {
        this.transaction_id = transaction_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.transfer_amount = transfer_amount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public long getSender_id() {
        return sender_id;
    }

    public long getReceiver_id() {
        return receiver_id;
    }

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransaction_id(long id){
        transaction_id = id;
    }

    public void setTransfer_amount(double num){
        transfer_amount = num;
    }

    public void setSender_id(long sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id;
    }

    @Override
    public String toString(){
        return "transaction_id - " +transaction_id + "  |   transfer_amount - " + transfer_amount + "  |   status - " + status;
    }

}
