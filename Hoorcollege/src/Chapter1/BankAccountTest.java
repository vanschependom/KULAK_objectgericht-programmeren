package Chapter1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class collecting tests for the class of bank accounts.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
public class BankAccountTest {

    // Variable referencing a bank account with balance 1000,
    // respectively with balance.
    private static BankAccount accountBalance1000, accountBalance2000;

    // Variables referencing bank accounts with balance 300,
    // with balance 500, with lowest possible balance,
    // respectively with highest possible balance.
    private static BankAccount accountBalance300, accountBalance500,
            accountLowestBalance, accountHighestBalance;

    // Variables referencing a blocked bank account, respectively
    // an unblocked account.
    private static BankAccount blockedAccount, unblockedAccount;

    // Set up a mutable test fixture.
    //
    // @post   The variable accountBalance1000 references a new
    //         bank account with a balance of 1000.
    // @post   The variable accountBalance2000 references a new
    //         bank account with a balance of 2000.
    // @post   The variable blockedAccount references a new
    //         blocked bank account.
    // @post   The variable unblockedAccount references a new
    //         unblocked bank account.
    @BeforeEach
    public void setUpMutableFixture() {
        accountBalance1000 = new BankAccount(1111111, 1000);
        accountBalance2000 = new BankAccount(2222222, 2000);
        blockedAccount = new BankAccount(2121212, 333, true);
        unblockedAccount = new BankAccount(1212121);
    }

    // Set up an immutable test fixture.
    //
    // @post   The variable accountBalance300 references a new
    //         bank account with a balance of 300.
    // @post   The variable accountBalance500 references a new
    //         bank account with a balance of 500.
    // @post   The variable accountLowestBalance references a new
    //         bank account with the lowest possible balance.
    // @post   The variable accountHighestBalance references a new
    //         bank account with the highest possible balance.
    @BeforeAll
    public static void setUpImmutableFixture() {
        accountBalance300 = new BankAccount(1234567, 300);
        accountBalance500 = new BankAccount(7654321, 500);
        accountLowestBalance = new BankAccount(0000000,
                BankAccount.getCreditLimit());
        accountHighestBalance = new BankAccount(9999999,
                BankAccount.getBalanceLimit());
    }

    // Test for the extended constructor in case all the
    // actual arguments satisfy their conditions.
    @Test
    public void extendedConstructor_LegalCase() {
        BankAccount theAccount = new BankAccount(1234, 300, false);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(300L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
    }

    // Test for the extended constructor in case the given
    // number is negative.
    @Test
    public void extendedConstructor_NegativeNumber() {
        BankAccount theAccount = new BankAccount(-1, 300, false);
        Assertions.assertEquals(0, theAccount.getNumber());
        Assertions.assertEquals(300L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
    }

    // Test for the extended constructor in case the given
    // balance is below the credit limit.
    @Test
    public void extendedConstructor_BalanceBelowLimit() {
        // Test can only be performed if the credit limit is not the
        // most negative value in the type long.
        if (BankAccount.getCreditLimit() > Long.MIN_VALUE) {
            BankAccount theAccount = new BankAccount(12345,
                    (BankAccount.getCreditLimit() - 1), false);
            Assertions.assertEquals(12345, theAccount.getNumber());
            Assertions.assertEquals(0L, theAccount.getBalance());
            Assertions.assertEquals(false, theAccount.isBlocked());
        }
    }

    // Test for the extended constructor in case the given
    // balance is above the balance limit.
    @Test
    public void extendedConstructor_BalanceAboveLimit() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE) {
            // Test can only be performed if the balance limit is not the
            // most positive value in the type long.
            BankAccount theAccount = new BankAccount(12345,
                    (BankAccount.getBalanceLimit() + 1), false);
            Assertions.assertEquals(12345, theAccount.getNumber());
            Assertions.assertEquals(0L, theAccount.getBalance());
            Assertions.assertEquals(false, theAccount.isBlocked());
        }
    }

    // Single test for the medium constructor.
    @Test
    public void mediumConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234, 300);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(300L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
    }

    // Single test for the least extended constructor.
    @Test
    public void shortConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(0L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
    }

    // Test for the method hasHigherBalanceThan(long) in case the
    // balance of the prime account is higher than the given amount.
    @Test
    public void hasHigherBalanceThanLong_HigherCase() {
        Assertions.assertTrue(accountBalance300.hasHigherBalanceThan(200));
    }

    // Test for the method hasHigherBalanceThan(long) in case the
    // balance of the prime account is lower than the given amount.
    @Test
    public void hasHigherBalanceThanLong_LowerCase() {
        Assertions.assertFalse(accountBalance300.hasHigherBalanceThan(500));
    }

    // Test for the method hasHigherBalanceThan(long) in case the
    // balance of the prime account is equal to the given amount.
    // This test is not really needed in a black box strategy.
    @Test
    public void hasHigherBalanceThanLong_EqualCase() {
        Assertions.assertFalse(accountBalance300.hasHigherBalanceThan(300));
    }

    // Test for the method hasHigherBalanceThan(Account) in case
    // the other account is effective.
    @Test
    public void hasHigherBalanceThanAccount_EffectiveCase() {
        Assertions.assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
    }

    // Test for the method hasHigherBalanceThan(Account) in case
    // the other account is not effective.
    @Test
    public void hasHigherBalanceThanAccount_NonEffectiveCase() {
        Assertions.assertFalse(accountBalance500.hasHigherBalanceThan(null));
    }

    // Test for the method deposit in case all conditions
    // imposed on such a transaction are satisfied.
    @Test
    public void deposit_LegalCase() {
        accountBalance1000.deposit(200);
        Assertions.assertEquals(1200L, accountBalance1000.getBalance());
    }

    // Test for the method deposit in case a negative
    // amount is withdrawn.
    @Test
    public void deposit_NegativeAmount() {
        accountBalance1000.deposit(-1200);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    // Test for the method withdraw in case the balance
    // is overdrawn.
    @Test
    public void deposit_BalanceOverflow() {
        accountBalance1000.deposit(Long.MAX_VALUE);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    // Test for the method withdraw in case all conditions
    // imposed on such a transaction are satisfied.
    @Test
    public void withdraw_LegalCase() {
        accountBalance1000.withdraw(200);
        Assertions.assertEquals(800L, accountBalance1000.getBalance());
    }

    ///Test for the method withdraw in case a negative
    // amount is withdrawn.
    @Test
    public void withdraw_NegativeAmount() {
        accountBalance1000.withdraw(-1200);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    /// Test for the method withdraw in case the balance
    // is overdrawn.
    @Test
    public void withdraw_BalanceOverflow() {
        accountBalance1000.withdraw(Long.MAX_VALUE);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    // Test for the method withdraw in case the account
    // is blocked.
    @Test
    public void withdraw_BlockedAccount() {
        long oldBalance = blockedAccount.getBalance();
        blockedAccount.withdraw(200);
        Assertions.assertEquals(oldBalance, blockedAccount.getBalance());
    }

    // Test for the method transferTo in case all conditions imposed
    // on a transfer are satisfied.
    @Test
    public void transferTo_LegalCase() {
        accountBalance1000.transferTo(100, accountBalance2000);
        Assertions.assertEquals(900L, accountBalance1000.getBalance());
        Assertions.assertEquals(2100L, accountBalance2000.getBalance());
    }

    // Test for the method transferTo in case the destination account
    // is not effective.
    @Test
    public void transferTo_NonEffectiveDestination() {
        accountBalance1000.transferTo(100, null);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    // Test for the method transferTo in case source and destination
    // account are the same account.
    @Test
    public void transferTo_SameAccount() {
        accountBalance1000.transferTo(100, accountBalance1000);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
    }

    // Test for the method transferTo in case the amount to be
    // transferred is negative.
    @Test
    public void transferTo_NegativeAmount() {
        accountBalance1000.transferTo(-100, accountBalance2000);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
        Assertions.assertEquals(2000L, accountBalance2000.getBalance());
    }

    // Test for the method transferTo in case the source account
    // is blocked.
    @Test
    public void transferTo_BlockedAccount() {
        long oldBalance = blockedAccount.getBalance();
        blockedAccount.transferTo(100, accountBalance2000);
        Assertions.assertEquals(oldBalance, blockedAccount.getBalance());
        Assertions.assertEquals(2000L, accountBalance2000.getBalance());
    }

    // Test for the method transferTo in case transfer causes a balance overflow for the
    // source account.
    @Test
    public void transferTo_BalanceOverflowOnSource() {
        accountLowestBalance.transferTo(100, accountBalance2000);
        Assertions.assertEquals(BankAccount.getCreditLimit(),
                accountLowestBalance.getBalance());
        Assertions.assertEquals(2000L, accountBalance2000.getBalance());
    }

    /// Test for the method transferTo in case the transfer causes
    // a balance overflow for the destination account.
    @Test
    public void transferTo_BalanceOverflowOnDestination() {
        accountBalance1000.transferTo(100, accountHighestBalance);
        Assertions.assertEquals(1000L, accountBalance1000.getBalance());
        Assertions.assertEquals(BankAccount.getBalanceLimit(),
                accountHighestBalance.getBalance());
    }

    // Test for the method setCreditLimit in case of a non-positive
    // value.
    @Test
    public void setCreditLimit_LegalCase() {
        BankAccount.setCreditLimit(-100);
        Assertions.assertEquals(-100L, BankAccount.getCreditLimit());
    }

    // Test for the metod setCreditLimit in case of a positive value.
    @Test
    public void setCreditLimit_IllegalCase() {
        long oldLimit = BankAccount.getCreditLimit();
        BankAccount.setCreditLimit(100);
        Assertions.assertEquals(oldLimit, BankAccount.getCreditLimit());
    }

    // Test for the method setBlocked for the true case.
    @Test
    public void setBlocked_TrueCase() {
        unblockedAccount.setBlocked(true);
        Assertions.assertTrue(unblockedAccount.isBlocked());
    }

    // Test for the method setBlocked for the false case.
    @Test
    public void setBlocked_FalseCase() {
        blockedAccount.setBlocked(false);
        Assertions.assertFalse(blockedAccount.isBlocked());
    }

    // Test for the method block (single case).
    @Test
    public void block_SingleCase() {
        unblockedAccount.block();
        Assertions.assertTrue(unblockedAccount.isBlocked());
    }

    // Test for the method unblock (single case).
    @Test
    public void unblock_SingleCase() {
        blockedAccount.unblock();
        Assertions.assertFalse(blockedAccount.isBlocked());
    }

}
