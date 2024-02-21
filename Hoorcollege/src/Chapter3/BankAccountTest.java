package Chapter3;

import be.kuleuven.cs.som.annotate.*;
import org.junit.jupiter.api.*;

public class BankAccountTest {

    private static BankAccount accountBalance0, accountBalanceMAX;

    private static BankAccount accountBalance100, accountBalance500;

    // Set up a mutable test fixture.
    //
    // @post   The variable accountBalance100 references a new
    //         bank account with a balance of 100.
    // @post   The variable accountBalance500 references a new
    //         bank account with a balance of 500.
    @BeforeEach
    public void setUpMutableFixture() {
        accountBalance100 = new BankAccount(1111111, 0, 100, false);
        accountBalance500 = new BankAccount(2222222, 0, 500, false);
    }

    @AfterEach
    public void tearDownMutableFixture() {
        accountBalance100.terminate();
        accountBalance500.terminate();
    }

    // Set up an immutable test fixture.
    //
    // @post   The variable accountBalance0 references a new
    //         bank account with a balance of 0.
    // @post   The variable accountBalance100 references a new
    //         bank account with a balance of 100.
    // @post   The variable accountBalanceMAX references a new
    //         bank account with the highest possible balance.
    @BeforeAll
    public static void setUpImmutableFixture() {
        accountBalance0 = new BankAccount(1, -2000, 0, false);
        accountBalanceMAX = new BankAccount(3, 0, Long.MAX_VALUE, false);
    }

    @Test
    public void extendedConstuctor$LegalCase() throws Exception {
        BankAccount theAccount = new BankAccount(12345, 0L, 1L, false);
        Assertions.assertEquals(12345, theAccount.getNumber());
        Assertions.assertEquals(0, theAccount.getCreditLimit());
        Assertions.assertEquals(1L, theAccount.getBalance());
        Assertions.assertFalse(theAccount.isBlocked());
        theAccount.terminate();
    }

    @Test
    public void extendedConstuctor$IllegaCreditLimit() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            new BankAccount(12345, 1L, 100L, false);
        });
    }

    @Test
    public void extendedConstuctor$IllegalBalance() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            new BankAccount(12345, Long.MIN_VALUE, Long.MIN_VALUE, false);
        });
    }

    @Test
    public void extendedConstuctor$NonMatchingBalanceCreditLimit() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            new BankAccount(12345, -100, -200, false);
        });
    }

    @Test
    public void extendedConstuctor$NonMatchingBalanceCreditLimit2() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            new BankAccount(12345, 0, 0, false);
        });
    }

    @Test
    public void extendedConstuctor$InvalidNumber() {
        Assertions.assertThrows(IllegalNumberException.class, () -> {
            new BankAccount(-10, 0, 100, false);
        });
    }

    @Test
    public void mediumConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(0L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
        theAccount.terminate();
    }

    @Test
    public void isValidBankCode_LegalValue() {
        Assertions.assertTrue(BankAccount.isValidBankCode(100));
    }

    @Test
    public void isValidBankCode_IllegalValue() {
        Assertions.assertFalse(BankAccount.isValidBankCode(-1));
    }

    @Test
    public void canHaveAsNumber_LegalValue() {
        Assertions.assertTrue(accountBalance0.canHaveAsNumber(231));
    }

    @Test
    public void canHaveAsNumber_OwnNumber() {
        Assertions.assertTrue(accountBalance0.canHaveAsNumber(accountBalance0
                .getNumber()));
    }

    @Test
    public void canHaveAsNumber_NonPositiveValue() {
        Assertions.assertFalse(accountBalance0.canHaveAsNumber(0));
    }

    @Test
    public void canHaveAsNumber_DuplicateValue() {
        Assertions.assertFalse(accountBalance0.canHaveAsNumber(accountBalance500
                .getNumber()));
    }

    @Test
    public void isPossibleCreditLimit$TrueCase() {
        Assertions.assertTrue(BankAccount.isPossibleCreditLimit(-12));
    }

    @Test
    public void isPossibleCreditLimit$FalseCase() {
        Assertions.assertFalse(BankAccount.isPossibleCreditLimit(1));
    }

    @Test
    public void canHaveAsCreditLimit$TrueCase() throws Exception {
        Assertions.assertTrue(accountBalance0.canHaveAsCreditLimit(-12));
    }

    @Test
    public void canHaveAsCreditLimit$ImpossibleCreditLimit() throws Exception {
        Assertions.assertFalse(accountBalance500.canHaveAsCreditLimit(100));
    }

    @Test
    public void canHaveAsCreditLimit$ConflictWithBalance() throws Exception {
        Assertions.assertFalse(accountBalance0.canHaveAsCreditLimit(0));
    }

    @Test
    public void setCreditLimit$LegalCase() {
        accountBalance0.setCreditLimit(-23);
        Assertions.assertEquals(-23, accountBalance0.getCreditLimit());
    }

    @Test
    public void setCreditLimit$IllegalCase() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            accountBalance0.setCreditLimit(31);
        });
    }

    @Test
    public void isValidBalanceLimit$TrueCase() {
        Assertions.assertTrue(BankAccount.isValidBalanceLimit(1));
    }

    @Test
    public void isValidBalanceLimit$FalseCase() {
        Assertions.assertFalse(BankAccount.isValidBalanceLimit(0));
    }

    @Test
    public void isPossibleBalance$TrueCase() {
        Assertions.assertTrue(BankAccount.isPossibleBalance(BankAccount.getBalanceLimit()));
    }

    @Test
    public void isPossibleBalance$FalseCase() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
            Assertions.assertFalse(BankAccount.isPossibleBalance(BankAccount
                    .getBalanceLimit() + 1));
    }

    @Test
    public void canHaveAsBalance$TrueCase() throws Exception {
        Assertions.assertTrue(accountBalance0.canHaveAsBalance(89));
    }

    @Test
    public void canHaveAsBalance$ImpossibleBalance() throws Exception {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
            Assertions.assertFalse(accountBalance0.canHaveAsBalance(Long.MAX_VALUE));
    }

    @Test
    public void canHaveAsBalance$ConflictWithCreditLimit() throws Exception {
        accountBalance0.setCreditLimit(-100);
        Assertions.assertFalse(accountBalance0.canHaveAsBalance(-100));
    }

    @Test
    public void matchesBalanceCreditLimit$TrueCase() {
        Assertions.assertTrue(BankAccount.matchesBalanceCreditLimit(100, 50));
    }

    @Test
    public void matchesBalanceCreditLimit$BalanceBelowLimit() {
        Assertions.assertFalse(BankAccount.matchesBalanceCreditLimit(10, 50));
    }

    @Test
    public void matchesBalanceCreditLimit$EqualValues() {
        // Not really needed in a pure black-box test
        Assertions.assertFalse(BankAccount.matchesBalanceCreditLimit(50, 50));
    }

    @Test
    public void hasHigherBalanceThanValue$TrueCase() {
        Assertions.assertTrue(accountBalance0.hasHigherBalanceThan(-100));
    }

    @Test
    public void hasHigherBalanceThanValue$FalseCase() {
        Assertions.assertFalse(accountBalance0.hasHigherBalanceThan(100));
    }

    @Test
    public void hasHigherBalanceThanAccount$TrueCase() {
        Assertions.assertTrue(accountBalance100.hasHigherBalanceThan(accountBalance0));
    }

    @Test
    public void hasHigherBalanceThanAccount$FalseCase() {
        Assertions.assertFalse(accountBalance0.hasHigherBalanceThan(accountBalance100));
    }

    @Test
    public void hasHigherBalanceThanAccount$IllegalCase() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountBalance0.hasHigherBalanceThan(null);
        });
    }

    @Test
    public void canAcceptForDeposit$TrueCase() {
        Assertions.assertTrue(accountBalance0.canAcceptForDeposit(200));
    }

    @Test
    public void canAcceptForDeposit$NegativeAmount() {
        Assertions.assertFalse(accountBalance0.canAcceptForDeposit(-200));
    }

    @Test
    public void canAcceptForDeposit$LongOverflow() {
        Assertions.assertFalse(accountBalance100.canAcceptForDeposit(Long.MAX_VALUE));
    }

    @Test
    public void canAcceptForDeposit$ValueAboveBalanceLimit() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE) {
            accountBalance100.deposit(BankAccount.getBalanceLimit()
                    - accountBalance100.getBalance());
            Assertions.assertFalse(accountBalance100.canAcceptForDeposit(1));
        }
    }

    @Test
    public void deposit$LegalCase() throws Exception {
        accountBalance100.deposit(1);
        Assertions.assertEquals(101, accountBalance100.getBalance());
    }

    @Test
    public void deposit$UnacceptableAmount() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            accountBalance0.deposit(-6);
        });
    }

    @Test
    public void canAcceptForWithdraw$TrueCase() {
        Assertions.assertTrue(accountBalance100.canAcceptForWithdraw(50));
    }

    @Test
    public void canAcceptForWithdraw$NegativeAmount() {
        Assertions.assertFalse(accountBalance0.canAcceptForWithdraw(-200));
    }

    @Test
    public void canAcceptForWithdraw$BlockedAccount() {
        accountBalance100.block();
        Assertions.assertFalse(accountBalance100.canAcceptForWithdraw(50));
    }

    @Test
    public void canAcceptForWithdraw$ValueBelowCreditLimit() {
        accountBalance0.setCreditLimit(-100);
        Assertions.assertFalse(accountBalance0.canAcceptForWithdraw(101));
    }

    @Test
    public void canAcceptForWithdraw$LongOverflow() {
        BankAccount theAccount = new BankAccount(123,-1000,-100,false);
        Assertions.assertFalse(theAccount.canAcceptForWithdraw(Long.MAX_VALUE));
        theAccount.terminate();
    }

    @Test
    public void withdraw$LegalCase() throws Exception {
        accountBalance100.withdraw(50);
        Assertions.assertEquals(50, accountBalance100.getBalance());
    }

    @Test
    public void withdraw$UnacceptableAmount() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            accountBalance0.withdraw(-6);
        });
    }

    @Test
    public void transferTo$LegalCase() {
        accountBalance100.transferTo(40, accountBalance500);
        Assertions.assertEquals(540,accountBalance500.getBalance());
        Assertions.assertEquals(60,accountBalance100.getBalance());
    }

    @Test
    public void transferTo$NonEffectiveDestination() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountBalance100.transferTo(50, null);
        });
    }

    @Test
    public void transferTo$SameDestination() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountBalance100.transferTo(50, accountBalance100);
        });
    }

    @Test
    public void transferTo$NonWithdrawableAmount() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            try {
                accountBalance0.setCreditLimit(-50);
                accountBalance0.transferTo(100, accountBalance100);
                Assertions.fail();
            }
            catch (IllegalAmountException exc) {
                // Checking if the balance of both accounts is left untouched.
                Assertions.assertEquals(0,accountBalance0.getBalance());
                Assertions.assertEquals(100, accountBalance100.getBalance());
                throw exc;
            }
        });
    }

    @Test
    public void transferTo$NonDepositableAmount() {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            try {
                accountBalance100.transferTo(50, accountBalanceMAX);
                Assertions.fail();
            }
            catch (IllegalAmountException exc) {
                // Checking if the balance of both accounts is left untouched.
                Assertions.assertEquals(100,accountBalance100.getBalance());
                Assertions.assertEquals(Long.MAX_VALUE, accountBalanceMAX.getBalance());
                throw exc;
            }
        });
    }

    @Test
    public void setBlocked$SingleCase() {
        accountBalance0.setBlocked(true);
        Assertions.assertTrue(accountBalance0.isBlocked());
    }

    @Test
    public void block$SingleCase() {
        accountBalance0.block();
        Assertions.assertTrue(accountBalance0.isBlocked());
    }

    @Test
    public void unblock$SingleCase() {
        accountBalance0.unblock();
        Assertions.assertFalse(accountBalance0.isBlocked());
    }

}
