package com.techelevator.tenmoClient.services;

import com.techelevator.tenmoClient.model.Account;
import com.techelevator.tenmoClient.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
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

//        try{
////            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "user/list");
//            return users;
//        }
        return users;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authtoken);
        return new HttpEntity<>(headers);
    }

}