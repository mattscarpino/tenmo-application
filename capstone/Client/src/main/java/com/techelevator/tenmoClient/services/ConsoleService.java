package com.techelevator.tenmoClient.services;

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
        System.out.println("1) Login");
        System.out.println("2) Display balance");
        System.out.println("3) Send Money");
        System.out.println("4) Make Request");
        System.out.println("5) View Transfers"); // add menu to prompt for transfer id for more details
        System.out.println("6) View Pending Transfers"); // be able to approve or reject
        System.out.println("7) Exit");

    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
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


}
