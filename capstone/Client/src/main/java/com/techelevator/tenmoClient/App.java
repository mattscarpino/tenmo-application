package com.techelevator.tenmoClient;

import com.techelevator.tenmoClient.model.Transaction;
import com.techelevator.tenmoClient.services.AuthenticationService;
import com.techelevator.tenmoClient.services.ConsoleService;
import com.techelevator.tenmoClient.services.TenmoService;

public class App {

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final TenmoService tenmoService = new TenmoService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        int menuSelection = -1;
        while (menuSelection != 7) {
            consoleService.displayMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if(menuSelection == 0){
//                handleRegistration();
            } else if (menuSelection == 1) {
                handleLogin();
            } else if (menuSelection == 2) {
                displayBalance();
            } else if (menuSelection == 3) {
                makeTransaction();
            } else if (menuSelection == 4) {
            //    handleUpdateReservation();
            } else if (menuSelection == 5) {
           //     handleDeleteReservation();
            } else if (menuSelection == 6) {
            //    handleLogin();
            }
            else if (menuSelection == 7) {
                continue;
            } else {
                // anything else is not valid
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void handleLogin() {
        String username = consoleService.promptForString("Username: ");
        String password = consoleService.promptForString("Password: ");
        String token = authenticationService.login(username, password);
        if (token != null) {
            tenmoService.setAuthtoken(token);
        } else {
            consoleService.printErrorMessage();
            return;
        }
        displayBalance();

    }

    private void displayBalance(){
        consoleService.printBalance(tenmoService.getBalance());
    }

    private void makeTransaction(){
        consoleService.displayAllUsers(tenmoService.listAllUsers());
        int userIdInput = consoleService.promptForInt("Enter user-id: ");
        double amountInput = consoleService.promptForAmount("How much would you like to send?: ");

        tenmoService.sendTransaction(userIdInput,amountInput);

    }

//    private void handleRegistration(){
//        String username = consoleService.promptForString("Username: ");
//        String password = consoleService.promptForString("Password: ");
//        boolean complete = tenmoService.registerAccount(username, password);
//        consoleService.successfulRegistration(complete);
//    }
}
