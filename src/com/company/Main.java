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
                        System.out.println("Enter a number from menu");
                        break;
                }
            } catch (Exception InputMismatchException) {
                System.out.print("Enter a number\n");
                scannerNumber.next();
            }

        }
    }

    private static void currencyExchange(){
        // manages the actions needed to count exchange value
        Double currencyAmount = 0.0;
        String currencyName = "";
        boolean incorrectData = false;

        do{
            try {
                System.out.println("Enter amount of EURO that you want to exchange");
                currencyAmount = scannerNumber.nextDouble();
                incorrectData = false;
            } catch (Exception InputMismatchException) {
                System.out.print("Enter a number (decimal separator - comma): \n");
                scannerNumber.next();
                incorrectData = true;
            }
        }while (incorrectData);

        incorrectData = false;
        do{
            try {
                System.out.println("Enter the currency you want to exchange your euro");
                currencyName = scannerString.nextLine();
                incorrectData = false;
            } catch (Exception InputMismatchException) {
                System.out.print("Enter a currency name:");
                scannerNumber.next();
                incorrectData = true;
            }
        }while (incorrectData);

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