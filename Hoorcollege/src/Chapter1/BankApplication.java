package Chapter1;
import java.util.Scanner;

public class BankApplication {

    // Definition of a main method introducing some simple
    // experiments with bank accounts.
    public static void main(String args[]) {
        BankAccount myAccount;
        Scanner inputStreamScanner = new Scanner(System.in);
        // Create a new bank account with given initial balance.
        System.out.println("Enter initial balance.");
        long initialBalance = inputStreamScanner.nextInt();
        myAccount = new BankAccount(1234567, initialBalance);
        // Deposit a given amount of money.
        System.out.println("Enter deposit amount.");
        long amount = inputStreamScanner.nextInt();
        myAccount.deposit(amount);
        // Withdraw a given amount of money.
        System.out.println("Enter withdrawal amount.");
        amount = inputStreamScanner.nextInt();
        myAccount.withdraw(amount);
        // Display the balance.
        System.out.println("Final balance: " + myAccount.getBalance());
    }

    // Definition of a method collecting code snippets illustrating
    // the notions of value semantics, reference semantics and
    // of boxing/unboxing.
    private static void experimentsWithVariables() {
        // Illustrations of value semantics.
        {
            int myValue = 10;
            int yourValue = 15;
            int hisValue = 20;

            myValue = hisValue;
            hisValue = hisValue + yourValue;

            System.out.println("Expecting false: " + (myValue == hisValue));
        }
        // Illustrations of reference semantics.
        {
            BankAccount myAccount = null;
            final BankAccount yourAccount = new BankAccount(1234567, 15);
            BankAccount hisAccount = new BankAccount(7654321, 20);

            myAccount = hisAccount;
            // Instance variable balance not accessible at this point.
            //			  hisAccount.balance =
            //			      hisAccount.balance + yourAccount.balance;

            System.out.println("Expecting true: " + (myAccount == hisAccount));
        }
        // Illustrations of boxing/unboxing
        {
            int myValue = 10;
            Integer myWrappedValue = 20;

            // Boxing
            myWrappedValue = myValue + 5;

            // Unboxing
            int yourValue = myWrappedValue;
        }
    }

}
