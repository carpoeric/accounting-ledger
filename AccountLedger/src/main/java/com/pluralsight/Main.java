package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String input;

        // HOME SCREEN
        do
        {
            System.out.println();
            System.out.println("Welcome to the Account Ledger Application!");
            System.out.println(("Main Menu"));
            System.out.println("---");
            System.out.println("[D] - Add Deposit");
            System.out.println("[P] - Make A Payment");
            System.out.println("[L] - Access Ledger");
            System.out.println("[X] - Exit Application");
            System.out.println("---");
            System.out.println("Please enter the LETTER for the desired action here:");

            input = scanner.nextLine();
            switch (input.toUpperCase())
            {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    System.out.println("Thank you for using Account Ledger!");
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
                    break;
            }
        } while (!input.equalsIgnoreCase("x"));
    }

    public static void addDeposit()
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the date(yyyy-MM-dd):");
        String date = scanner.nextLine();
        System.out.println("Please enter the time (HH:mm:SS):");
        String time = scanner.nextLine();
        System.out.println("Please enter a note for this deposit:");
        String description = scanner.nextLine();
        System.out.println("Please enter a vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter the amount you would like to deposit:");
        double amount = scanner.nextDouble();
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAmount = df.format(amount);


        try
        {
            FileWriter fileWriter = new FileWriter("Transactions.csv", true);

            fileWriter.write("\n" + date + "|" + time + "|" + description + "|" + vendor + "|" + formattedAmount);
            System.out.println("Deposit successfull!");
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Data input invalid.");
        }
    }

    public static void makePayment()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the date(yyyy-MM-dd):");
        String date = scanner.nextLine();
        System.out.println("Please enter the time (HH:mm:SS):");
        String time = scanner.nextLine();
        System.out.println("Please enter a note for this deposit:");
        String description = scanner.nextLine();
        System.out.println("Please enter a vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter the payment amount:");
        double amount = scanner.nextDouble() *-1;

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAmount = df.format(amount);

        try
        {
            FileWriter fileWriter = new FileWriter("Transactions.csv", true);
            fileWriter.write("\n" + date + "|" + time + "|" + description + "|" + vendor + "|" + formattedAmount);
            System.out.println("Payment successful!");
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Data input invalid.");
        }
    }

    public static void showLedger()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("Welcome to your Ledger. ");
            System.out.println(("Main Menu: "));
            System.out.println("---");
            System.out.println("[A] - Display All Transactions");
            System.out.println("[D] - Display Only Deposits");
            System.out.println("[P] - Display Only Payments");
            System.out.println("[R] - View Reports");
            System.out.println("[H] - Go Back To Home page");
            System.out.println("---");
            System.out.println("Please enter the LETTER for the desired action here:");

            String input = scanner.nextLine();
            switch (input.toUpperCase())
            {
                case "A":
                    Ledger.allEntries();
                case "D":
                   Ledger.showDepositedEntries();
                case "P":
                   Ledger.showPaymentEntries();
                case "R":
                   Ledger.showReports();
                case "H":
                    return;

                default:
                    System.out.println("Invalid entry. Please try again.");
            }
        }
    }
}