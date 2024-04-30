package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger
{
    public static ArrayList<Transaction> transactionsList = getTransactions();
    public static ArrayList<Transaction> getTransactions()
    {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try
        {
            FileReader fileReader = new FileReader("Transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input;
            while ((input = bufferedReader.readLine()) != null)
            {
                String[] details = input.split("\\|");

                LocalDate date = LocalDate.parse(details[0]);

                LocalTime time = LocalTime.parse(details[1]);

                String description = details[2];

                String vendor = details[3];

                double amount = Double.parseDouble(details[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        Comparator<Transaction> compareByDate = Comparator.comparing(Transaction::getDate).reversed();
        Comparator<Transaction> compareByTime = Comparator.comparing(Transaction::getTime).reversed();
        Comparator<Transaction> compareByDateTime = compareByDate.thenComparing(compareByTime);
        transactions.sort(compareByDateTime);
        return transactions;
    }


    public static void showReports()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("Here are your reports! ");
            System.out.println(("How would you like to view them? "));
            System.out.println("---");
            System.out.println("1 - Month to Date");
            System.out.println("2 - Previous months");
            System.out.println("3 - Year to Date");
            System.out.println("4 - Previous Year");
            System.out.println("5 - Search by Vendor");
            System.out.println("H - Go Back To Home page");
            System.out.println("---");
            System.out.println("Please enter the LETTER/NUMBER for the desired action here:");

            String input = scanner.nextLine();
            switch (input.toUpperCase())
            {
                case "1":
                    monthtoDate();
                case "2":
                    previousMonths();
                case "3":
                    yeartoDate();
                case "4":
                    previousYears();
                case "5":
                    searchbyVendor();
                case "H":
                    return;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }


    public static void searchbyVendor()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Please enter the vendor name: ");
        String vendorName = scanner.nextLine();
        System.out.println("Here are all your transaction from" + " " + vendorName);
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");;

        for (Transaction item : transactionsList)
        {
            if (item.getVendor().equalsIgnoreCase(vendorName))
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void previousYears()
    {
        System.out.println(" Here is your report for the previous year: ");
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");
        LocalDate currentDate = LocalDate.now();
        int previousYear = currentDate.minusYears(1).getYear();

        for (Transaction item : transactionsList)
        {
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getYear() == previousYear)
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void yeartoDate()
    {
        System.out.println("Here is your year to date report: ");
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfTheYear = currentDate.withDayOfYear(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MMM dd");
        System.out.println("From" + " " + startOfTheYear.format(formatter) + " " + "to" + " " + currentDate.format(formatter));
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");

        for (Transaction item : transactionsList)
        {
            if (item.getDate().isAfter(startOfTheYear.minusDays(1)) || item.getDate().isEqual(currentDate))
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void previousMonths()
    {
        System.out.println(" Here is your report for the previous month: ");
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");
        LocalDate currentDate = LocalDate.now();
        int previousMonth = currentDate.minusMonths(1).getMonthValue();

        for (Transaction item : transactionsList)
        {
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getMonthValue() == previousMonth && transactionDate.getYear() == currentDate.getYear())
            {

                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void monthtoDate()
    {
        System.out.println("Here is your month to date report: ");
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfTheCurrentMonth = currentDate.withDayOfMonth(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        System.out.println("From" + " " + startOfTheCurrentMonth.format(formatter) + " to " + currentDate.format(formatter));
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");

        for (Transaction item : transactionsList)
        {
            if (item.getDate().isAfter(startOfTheCurrentMonth.minusDays(1)) || item.getDate().isEqual(currentDate))
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void showPaymentEntries()
    {
        System.out.println(" Deposits");
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");
        for (Transaction item : transactionsList)
        {
            if (item.getAmount() < 0)
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }


    public static void showDepositedEntries()
    {
        System.out.println(" Deposits");
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");
        for (Transaction item : transactionsList)
        {

            if (item.getAmount() > 0)
            {
                System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

    public static void allEntries()
    {
        System.out.println(" All Entries");
        System.out.println("Date        " + "  Time          " +"Description                                      " + "  Vendor             " + "       Amount         ");
        for (Transaction item : transactionsList)
        {
            System.out.printf("%-13s %-13s %-50s %-25s %-30.2f\n", item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
        }
    }
}