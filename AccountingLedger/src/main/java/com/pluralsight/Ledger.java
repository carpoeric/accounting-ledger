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
        //create method to hold array lists for transactions. Additionally, parses data to sort out for certain criteria.
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
            System.out.println(Colors.BRIGHT_RED + "Error: File invalid. Please try again!" + Colors.TEXT_RESET);
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
            System.out.println(Colors.BRIGHT_BLUE + "\n-----------------------Ledger----------------------\n" + Colors.TEXT_RESET);
            System.out.println(Colors.BRIGHT_WHITE + """
                    What would you like to do?
                    
                    [A] - Display All Transactions
                    [D] - Display Deposits Only
                    [P] - Display Payments Only
                    [R] - View Reports
                    [H] - Go Back To Home Page
                    """ + Colors.TEXT_RESET);
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
                    System.out.println(Colors.BRIGHT_CYAN + "\nReturning to the Home Page now.... \n" + Colors.TEXT_RESET);
                    done = true;
                    break;
                }
                default:
                    System.out.println(Colors.BRIGHT_RED + "Invalid entry. Please try again." + Colors.TEXT_RESET);
                    break;
            }
        }
    }

    public static void allTransactions()
    //outputs all transactions
    {
        System.out.println(Colors.BRIGHT_BLUE + "------------------------------------All Transactions--------------------------------------" + Colors.TEXT_RESET);
        printHeader();

        for (TransactionTab i : transactions)
        {
            System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }

    public static void showDepositedEntries()
    // outputs positive x>0 transactions
    {
        System.out.printf(Colors.BRIGHT_BLUE + "%30s", "------------------------------------All Deposits------------------------------------------\n" + Colors.TEXT_RESET);
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
    // outputs negative x<0 transactions
    {
        System.out.println(Colors.BRIGHT_BLUE + "--------------------------------------All Payments---------------------------------------- " + Colors.TEXT_RESET);
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
    // Reports class/menu
    {
        Reports.showReports();
    }

    public static void printHeader()
    // prints the top part for each criteria chosen
    {
        System.out.printf(Colors.BRIGHT_WHITE + "%-13s %-13s %-25s %-25s %-30s\n", "Date", "Time", "Description", "Vendor", "Amount" + Colors.TEXT_RESET);
        System.out.println(Colors.BRIGHT_BLUE + "------------------------------------------------------------------------------------------" + Colors.TEXT_RESET);
    }
}