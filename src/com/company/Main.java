package com.company;

import java.util.Scanner;

public class Main {

    private static Scanner scannerNumber = new Scanner(System.in);
    private static Scanner scannerString = new Scanner(System.in);
    private static Calculator calculator = new Calculator();

    public static void main(String[] args) {

        controller();
    }

    private static void controller(){
        // manages the actions in the currency exchange program

        boolean run = true;

        while (run) {
            int choice = 0;

            //main menu
            try {
                System.out.println("Menu:\n 1 - Count exchange \n " +
                        "2 - All available currencies \n 3 - End program");
                choice = scannerNumber.nextInt();
            } catch (Exception InputMismatchException) {
                System.out.print("Enter a number:");
                scannerNumber.next();
            }
            switch (choice) {
                case 1:
                    currencyExchange();
                    break;
                case 2:
                    System.out.println("All available currencies (ISO 4217 format)");
                    System.out.println( calculator.writeAllCurrencies() );
                    break;
                case 3:
                    run = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void currencyExchange(){
        // manages the actions needed to count exchange value
        Double currencyAmount = 0.0;
        String currencyName = "";

        try {
            System.out.println("Enter amount of EURO that you want to exchange");
            currencyAmount = scannerNumber.nextDouble();
        } catch (Exception InputMismatchException) {
            System.out.print("Enter a number (decimal separator - , ):");
            scannerNumber.next();
        }
        try {
            System.out.println("Enter the currency you want to exchange your euro");
            currencyName = scannerString.nextLine();
        } catch (Exception InputMismatchException) {
            System.out.print("Enter a currency name:");
            scannerNumber.next();
        }
        //trim in case unwanted white characters
        currencyName = currencyName.trim();

        if(calculator.findIndexOfCurrency(currencyName)==-1){
            System.out.println("There is no chosen currency");
        }else {
            System.out.println( currencyAmount + " EUR = "
                    + calculator.countExchangeValue( currencyName, currencyAmount ));
        }
    }
}