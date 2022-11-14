package com.techelevator.tenmoClient.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.techelevator.tenmoClient.model.Account;
import com.techelevator.tenmoClient.model.RegisterUserDTO;
import com.techelevator.tenmoClient.model.Transaction;
import com.techelevator.tenmoClient.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authtoken = null;

    public void setAuthtoken(String authtoken){this.authtoken = authtoken;};

    public double getBalance(){
        Account account = null;

        try{
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "user/account", HttpMethod.GET, makeAuthEntity(),Account.class);
            account = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            System.out.println("cannot find account");
        }

        double balance = account.getBalance();
        return balance;

    }

    public User[] listAllUsers(){
        User[] users = null;
        try{
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "user/list", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println("Invalid.");
        }
        return users;
    }

    public Transaction sendTransaction(int user_id, double amount){
        Transaction transaction = null;
//        ?receiver_id=1002&transfer_amount=15
        try{
            transaction = restTemplate.postForObject(API_BASE_URL+ "user/transaction/send?receiver_id=" + user_id +
                    "&transfer_amount=" + amount, makeAuthEntity(), Transaction.class);
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println("Invalid.");
        }
        return transaction;
    }

//    public boolean registerAccount(String username, String password){
//        RegisterUserDTO registerUserDTO = new RegisterUserDTO(username, password);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<RegisterUserDTO> response = new HttpEntity<>(registerUserDTO, headers);
//        try{
//            restTemplate.postForObject(API_BASE_URL + "register", response, Void.class);
//        } catch (RestClientResponseException | ResourceAccessException e){
//            System.out.println("could not complete registration");
//            return false;
//        }
//        return true;
//    }

    private HttpEntity<Transaction> makeTransactionEntity(Transaction transaction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authtoken);
        return new HttpEntity<>(transaction, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authtoken);
        return new HttpEntity<>(headers);
    }

}
