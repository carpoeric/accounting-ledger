package com.pluralsight;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.pluralsight.Ledger.*;
import static com.pluralsight.HomePage.scanner;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class Reports
{

    public static void showReports()
    {
        boolean done = false;
        while (!done)
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
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input)
            {
                case 1:
                    monthToDate();
                case 2:
                    previousMonths();
                case 3:
                    yearToDate();
                case 4:
                    previousYear();
                case 5:
                    searchByVendor();
                case 0:
                {
                    showReports();
                }
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }

    public static void monthToDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate firstOfCurrentMonth = today.withDayOfMonth(1);
        System.out.println("\n-------- All Transactions From " + firstOfCurrentMonth.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                today.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"--------");
        printHeader();

        for (TransactionTab i : transactions)
        {
            if (isBetween(today, firstOfCurrentMonth, i))
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }

    private static boolean isBetween(LocalDate today, LocalDate firstOfCurrentMonth, TransactionTab i)
    {
        return (i.getDate().isBefore(today) || i.getDate().isEqual(today))
                && (i.getDate().isAfter(firstOfCurrentMonth) || i.getDate().isEqual(firstOfCurrentMonth));
    }

    public static void previousMonths()
    {
        LocalDate today = LocalDate.now();
        LocalDate prevMonth = today.minusMonths(1);
        List<TransactionTab> prevMonthTransactions = new ArrayList<>();
        for (TransactionTab transaction : transactions)
        {
            LocalDate transactionDate = transaction.getDate();
            if (transactionDate.isAfter(prevMonth.withDayOfMonth(1).minusDays(1))
                    && transactionDate.isBefore(today.withDayOfMonth(1)))
            {
                prevMonthTransactions.add(transaction);
            }
        }
        System.out.println("\n------------------------------Previous Month: " + prevMonth.getMonth() + "--------------------------------");
        printHeader();
        for (TransactionTab i : prevMonthTransactions)
        {
            System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }

    public static void yearToDate()
    {
        LocalDate today = LocalDate.now();
        LocalDate begOfYear = today.with(firstDayOfYear());
        System.out.println("\n---------------- All Transactions From " + begOfYear.format(DateTimeFormatter.ofPattern("MMMM, dd")) + " To " +
                today.format(DateTimeFormatter.ofPattern("MMMM, dd")) +"---------------------\n");
        printHeader();

        for (TransactionTab i : transactions)
        {
            if (between(today, begOfYear, i))
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }


    private static boolean between(LocalDate today, LocalDate begOfYear, TransactionTab i)
    {
        return (i.getDate().isBefore(today) || i.getDate().isEqual(today)) && (i.getDate().isAfter(begOfYear) || i.getDate().isEqual(begOfYear));
    }

    public static void previousYear()
    {
        LocalDate today = LocalDate.now();
        System.out.println("--------------------------------Previous Year--------------------------------------\n");
        printHeader();
        for (TransactionTab i : transactions)
        {
            LocalDate year =  i.getDate();
            if (year.getYear() == today.getYear() -1)
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }


    public static void searchByVendor()
    {
        System.out.println("Enter the vendor's name you would like to search by: ");
        Scanner scanner = new Scanner(System.in);
        String vendor = scanner.nextLine();

        System.out.println("--------------------------------All Transactions From " + vendor + "-------------------------------- ");
        printHeader();
        for (TransactionTab i : transactions)
        {
            if (i.getVendor().equalsIgnoreCase(vendor))
            {
                System.out.printf("%-13s %-13s %-25s %-25s %-30.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }
}