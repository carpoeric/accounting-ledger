package com.pluralsight;

import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static com.pluralsight.HomePage.scanner;

public class Ledger
{
    public static ArrayList<TransactionTab> transactions = getTransactions();

    public static ArrayList<TransactionTab> getTransactions()
    {

        ArrayList<TransactionTab> transactions = new ArrayList<>();
        try
        {
            FileReader fileReader = new FileReader("Transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input;
            while ((input = bufferedReader.readLine()) != null)
            {
                if (!input.isEmpty())
                {
                    String[] details = input.split("\\|");
                    LocalDate date = LocalDate.parse(details[0]);
                    LocalTime time = LocalTime.parse(details[1]);
                    String description = details[2];
                    String vendor = details[3];
                    double amount = Double.parseDouble(details[4]);
                    TransactionTab transaction = new TransactionTab(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error: File invalid. Please try again!");
            System.exit(0);
        }
        Comparator<TransactionTab> compareByDate = Comparator.comparing(TransactionTab::getDate).reversed();
        Comparator<TransactionTab> compareByTime = Comparator.comparing(TransactionTab::getTime).reversed();
        Comparator<TransactionTab> compareByDateTime = compareByDate.thenComparing(compareByTime);
        transactions.sort(compareByDateTime);
        return transactions;
    }

    public static void showLedger()
    {
        // LEDGER SCREEN

        boolean done = false;
        while (!done)
        {
            System.out.println("\n-----------------------Ledger----------------------\n");
            System.out.println("""
                    What would you like to do?
                    [A] - Display All Transactions
                    [D] - Display Deposits Only
                    [P] - Display Payments Only
                    [R] - View Reports
                    [H] - Go Back To Home Page
                    """);
            System.out.print("Please enter the LETTER for the desired action here: ");
            String input = scanner.nextLine();
            System.out.println();

            switch (input.toUpperCase())
            {
                case "A":
                    allTransactions();
                    break;
                case "D":
                    showDepositedEntries();
                    break;
                case "P":
                    showPaymentEntries();
                    break;
                case "R":
                {
                    showReports();
                    break;
                }
                case "H":
                {
                    System.out.println("\nReturning to the Home Page now.... \n");
                    done = true;
                    break;
                }
                default:
                    System.out.println("Invalid entry. Please try again.");
                    break;
            }
        }
    }

    public static void allTransactions()
    {
        System.out.println("--------------------------------All Transactions----------------------------------");
        printHeader();

        for (TransactionTab i : transactions)
        {
            System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }

    public static void showDepositedEntries()
    {
        System.out.printf("%30s", "--------------------------------All Deposits--------------------------------------\n");
        printHeader();

        for (TransactionTab i : transactions)
        {
            if (i.getAmount() > 0)
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }

    public static void showPaymentEntries()
    {
        System.out.println("--------------------------------All Payments---------------------------------- ");
        printHeader();

        for (TransactionTab i : transactions)
        {
            if (i.getAmount() < 0)
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }

    public static void showReports()
    {
        Reports.showReports();
    }

    public static void printHeader()
    {
        System.out.printf("%-13s %-13s %-25s %-25s %-30s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("----------------------------------------------------------------------------------");
    }
}