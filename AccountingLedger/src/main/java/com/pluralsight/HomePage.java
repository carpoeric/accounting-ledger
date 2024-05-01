package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class HomePage
{
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        homepage();
    }

    public static void homepage()
    {
        // HOME SCREEN
        boolean done = false;
        while (!done)
        {
            System.out.println("\n-------Welcome To The Accounting Ledger App!-------\n");
            System.out.println("""
                    Main Menu:
                    [D] - Add Deposit
                    [P] - Make A Payment
                    [L] - Access Ledger
                    [X] - Exit App
                    """);
            System.out.print("Please enter the LETTER for your desired action here: ");
            String input = scanner.nextLine();
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
                    done = true;
                    System.out.println("Thank you for using The Account Ledger App.");
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
                    break;
            }
        }
    }

    public static void addDeposit()
    {

        try (FileWriter fileWriter = new FileWriter("Transactions.csv", true))
        {
            System.out.print("Please enter a vendor: ");
            String vendor = scanner.nextLine();
            System.out.print("Please enter a description: ");
            String description = scanner.nextLine();
            System.out.print("Please enter an amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            fileWriter.write(String.format("%s|%s|%s|%s|%.2f\n", LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount));
            fileWriter.close();
            System.out.println("\nDeposit successful! ");
            System.out.println("\nReturning to the Home Page now.... \n");
        }
        catch (IOException e)
        {
            System.out.println("Invalid entry. Please try again.");
        }
        homepage();
    }

    public static void makePayment()
    {
        try (FileWriter fileWriter = new FileWriter("Transactions.csv", true))
        {
            System.out.print("Please enter a vendor: ");
            String vendor = scanner.nextLine();
            System.out.print("Please enter a description: ");
            String description = scanner.nextLine();
            System.out.print("Please enter an amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            fileWriter.write(String.format("%s|%s|%s|%s|-%.2f\n", LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount));
            fileWriter.close();
            System.out.println("\nPayment successful! ");
            System.out.println("\nReturning to the Home Page now.... \n");
        }
        catch (IOException e)
        {
            System.out.println("Invalid entry. Please try again.");
        }
        homepage();
    }

    public static void showLedger()
    {
        Ledger.showLedger();
    }
}