package Chapter1;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class BankApplication {
    public BankApplication() {
    }

    public static void main(String[] args) {
        Scanner inputStreamScanner = new Scanner(System.in);
        System.out.println("Enter initial balance.");
        long initialBalance = (long)inputStreamScanner.nextInt();
        BankAccount myAccount = new BankAccount(1234567, initialBalance);
        System.out.println("Enter deposit amount.");
        long amount = (long)inputStreamScanner.nextInt();
        myAccount.deposit(amount);
        System.out.println("Enter withdrawal amount.");
        amount = (long)inputStreamScanner.nextInt();
        myAccount.withdraw(amount);
        System.out.println("Final balance: " + myAccount.getBalance());
    }

    private static void experimentsWithVariables() {
        int myValue = 0;
        int yourValue = 15;
        int hisValue = 20;
        hisValue += yourValue;
        System.out.println("Expecting false: " + (myValue == hisValue));
        BankAccount myAccount = null;
        new BankAccount(1234567, 15L);
        BankAccount hisAccount = new BankAccount(7654321, 20L);
        System.out.println("Expecting true: " + (hisAccount == hisAccount));
        myValue = 10;
        Integer myWrappedValue = myValue + 5;
        hisValue = myWrappedValue;
    }
}
