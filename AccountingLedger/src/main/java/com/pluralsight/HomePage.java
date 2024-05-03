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
        homepage(); //used to recall back to the homepage
    }

    public static void homepage()
    {
        // HOME SCREEN
        boolean done = false;
        while (!done)
        {
            System.out.println(Colors.BRIGHT_BLUE + "\n---------Welcome To The Accounting Ledger!---------\n" + Colors.TEXT_RESET);
            System.out.println(Colors.BRIGHT_WHITE + """
                    Main Menu:
                    
                    [D] - Add Deposit
                    [P] - Make A Payment
                    [L] - Access Ledger
                    [X] - Exit App
                    """ + Colors.TEXT_RESET);
            System.out.print("Please enter the LETTER for your desired action here: ");
            String input = scanner.nextLine();
            System.out.println();

            // switch statement used parallel with the choices above
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
                    System.out.println(Colors.BRIGHT_CYAN + "Thank you for using " + Colors.TEXT_RESET + Colors.BRIGHT_WHITE + "The Accounting Ledger" + Colors.TEXT_RESET + Colors.BRIGHT_CYAN + "!" + Colors.TEXT_RESET);
                    System.out.println();
                    System.out.println(Colors.BRIGHT_CYAN + "Goodbye!" + Colors.TEXT_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Colors.BRIGHT_RED + "Invalid entry. Please try again." + Colors.TEXT_RESET);
                    break;
            }
        }
    }

    public static void addDeposit()
    {
        // Deposit prompt. Auto adds time/seconds/date
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
            System.out.println(Colors.BRIGHT_GREEN + "\nDeposit successful! " + Colors.TEXT_RESET);
            System.out.println(Colors.BRIGHT_CYAN + "\nReturning to the Home Page now.... \n" + Colors.TEXT_RESET);
        }
        catch (IOException e)
        {
            System.out.println(Colors.BRIGHT_RED + "Invalid entry. Please try again." + Colors.TEXT_RESET);
        }
        homepage();
    }

    public static void makePayment()
    {
        // same as deposit, but with payments. (-% for negative in the record book)
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
            System.out.println(Colors.BRIGHT_GREEN + "\nPayment successful! " + Colors.TEXT_RESET);
            System.out.println(Colors.BRIGHT_CYAN + "\nReturning to the Home Page now.... \n" + Colors.TEXT_RESET);
        }
        catch (IOException e)
        {
            System.out.println(Colors.BRIGHT_RED + "Invalid entry. Please try again." + Colors.TEXT_RESET);
        }
        homepage();
    }

    public static void showLedger()
    {
        Ledger.showLedger();
    }
}