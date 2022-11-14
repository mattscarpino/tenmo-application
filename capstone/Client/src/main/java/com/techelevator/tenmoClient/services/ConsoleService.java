package com.techelevator.tenmoClient.services;

import com.techelevator.tenmoClient.model.User;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void displayMainMenu(){

        System.out.println();
        System.out.println("------- Welcome to Tenmo! --------");
        System.out.println("0) Sign Up");
        System.out.println("1) Login");
        System.out.println("2) Display balance");
        System.out.println("3) Send Money");
        System.out.println("4) Make Request");
        System.out.println("5) View Transfers"); // add menu to prompt for transfer id for more details
        System.out.println("6) View Pending Transfers"); // be able to approve or reject
        System.out.println("7) Exit");

    }

    public int promptForInt(String prompt) {
        int amount;
        while(true) {
            try {
                System.out.println(prompt);
                String amountAsString = scanner.nextLine();
                amount = Integer.parseInt(amountAsString);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid entry.");
            }
        }
        return amount;
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public double promptForAmount (String prompt){
        double amount;
        while(true) {
            try {
                System.out.println(prompt);
                String amountAsString = scanner.nextLine();
                amount = Double.parseDouble(amountAsString);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid entry.");
            }
        }
        return amount;
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printBalance(double balance){
        System.out.println();
        System.out.printf("Your current account balance is: $%.2f\n", balance);
    }

    public void displayAllUsers(User[] users){
        for(User u : users){
            System.out.println("username: " + u.getUsername() + "\t || \t user-id: "+ u.getId());
        }
    }

    public void successfulRegistration(boolean complete){
        if(complete){
            System.out.println("Registration was Successful!");
        } else{
            System.out.println("Registration failed");
        }
    }

}
