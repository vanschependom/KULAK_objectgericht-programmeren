package Chapter2;

import be.kuleuven.cs.som.annotate.*;
import org.junit.jupiter.api.*;
import java.util.Arrays;

/**
 * A class collecting tests for the class of bank accounts.
 *
 * @version 2.0
 * @author  Eric Steegmans
 */
public class BankAccountTest {

    //    // Instance variable referencing bank accounts that may
    //    // change during individual tests.
    private BankAccount accountBalance1000, accountBalance2000, blockedAccount,
            unblockedAccount, accountWithTokens;

    //    // Class variables referencing bank accounts that do not
    //    // change during the entire test case.
    private static BankAccount accountBalance300, accountBalance500;

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
    // @post   The variable accountWithTokens references a new
    //         bank account with tokens abcdef and 123456.
    @BeforeEach
    public void setUpMutableFixture() {
        accountBalance1000 = new BankAccount(1111111, 1000);
        accountBalance2000 = new BankAccount(2222222, 2000);
        blockedAccount = new BankAccount(2121212, 333, true);
        unblockedAccount = new BankAccount(1212121);
        accountWithTokens = new BankAccount(135790,200,false,"abcdef", "123456");
    }

    @AfterEach
    public void tearDownMutableFixture() {
        accountBalance1000.terminate();
        accountBalance2000.terminate();
        blockedAccount.terminate();
        unblockedAccount.terminate();
        accountWithTokens.terminate();
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
        //        accountLowestBalance = new BankAccount(0000000, BankAccount
        //            .getCreditLimit());
        //        accountHighestBalance = new BankAccount(9999999, BankAccount
        //            .getBalanceLimit());
    }

    @Test
    public void extendedConstructor_LegalCase() {
        String tokens[] = { "abcdef", "012345" };
        BankAccount theAccount = new BankAccount(1234, 300, false, tokens[0],
                tokens[1]);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(300, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
        Assertions.assertTrue(Arrays.equals(tokens, theAccount.getTokens()));
        theAccount.terminate();
    }

    @Test
    public void mediumConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234, 300);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(300L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
        Assertions.assertEquals(0, theAccount.getNbTokens());
        theAccount.terminate();
    }

    @Test
    public void shortConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234);
        Assertions.assertEquals(1234, theAccount.getNumber());
        Assertions.assertEquals(0L, theAccount.getBalance());
        Assertions.assertEquals(false, theAccount.isBlocked());
        Assertions.assertEquals(0, theAccount.getNbTokens());
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
        Assertions.assertTrue(accountBalance300.canHaveAsNumber(231));
    }

    @Test
    public void canHaveAsNumber_OwnNumber() {
        Assertions.assertTrue(accountBalance300.canHaveAsNumber(accountBalance300
                .getNumber()));
    }

    @Test
    public void canHaveAsNumber_NonPositiveValue() {
        Assertions.assertFalse(accountBalance300.canHaveAsNumber(0));
    }

    @Test
    public void canHaveAsNumber_DuplicateValue() {
        Assertions.assertFalse(accountBalance300.canHaveAsNumber(accountBalance500
                .getNumber()));
    }

    @Test
    public void isValidCreditNumber_LegalValue() {
        Assertions.assertTrue(BankAccount.isValidCreditLimit(0));
    }

    @Test
    public void isValidCreditNumber_IllegalValue() {
        Assertions.assertFalse(BankAccount.isValidCreditLimit(1));
    }

    @Test
    public void setCreditLimit_LegalCase() {
        BankAccount.setCreditLimit(-100);
        Assertions.assertEquals(-100, BankAccount.getCreditLimit());
    }

    @Test
    public void isValidBalanceLimit_LegalValue() {
        Assertions.assertTrue(BankAccount.isValidBalanceLimit(1));
    }

    @Test
    public void isValidBalanceLimit_IllegalValue() {
        Assertions.assertFalse(BankAccount.isValidBalanceLimit(0));
    }

    @Test
    public void isValidBalance_LegalValue() {
        Assertions.assertTrue(BankAccount.isValidBalance(0));
    }

    @Test
    public void isValidBalance_ValueBelowCreditLimit() {
        if (BankAccount.getCreditLimit() > Long.MIN_VALUE)
            Assertions.assertFalse(BankAccount
                    .isValidBalance(BankAccount.getCreditLimit() - 1));
    }

    @Test
    public void isValidBalance_ValueAboveBalanceLimit() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
            Assertions.assertFalse(BankAccount.isValidBalance(BankAccount
                    .getBalanceLimit() + 1));
    }

    @Test
    public void hasHigherBalanceThanLong_HigherCase() {
        Assertions.assertTrue(accountBalance300.hasHigherBalanceThan(200));
    }

    @Test
    public void hasHigherBalanceThanLong_LowerCase() {
        Assertions.assertFalse(accountBalance300.hasHigherBalanceThan(500));
    }

    @Test
    public void hasHigherBalanceThanLong_EqualCase() {
        // This test is not really needed in a black box strategy.
        Assertions.assertFalse(accountBalance300.hasHigherBalanceThan(300));
    }

    @Test
    public void hasHigherBalanceThanAccount_EffectiveCase() {
        Assertions.assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
    }

    @Test
    public void canAcceptForDeposit_NegativeAmount() {
        Assertions.assertFalse(accountBalance300.canAcceptForDeposit(-200));
    }

    @Test
    public void canAcceptForDeposit_LongOverflow() {
        Assertions.assertFalse(accountBalance300.canAcceptForDeposit(Long.MAX_VALUE));
    }

    @Test
    public void canAcceptForDeposit_ValueAboveBalanceLimit() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE) {
            accountBalance1000.deposit(BankAccount.getBalanceLimit()
                    - accountBalance1000.getBalance());
            Assertions.assertFalse(accountBalance1000.canAcceptForDeposit(1));
        }
    }

    @Test
    public void deposit_LegalCase() {
        accountBalance1000.deposit(200);
        Assertions.assertEquals(1200L, accountBalance1000.getBalance());
    }

    @Test
    public void testNegativeAmount() {
        Assertions.assertFalse(accountBalance1000.canAcceptForWithdraw(-200));
    }

    @Test
    public void testBlockedAccount() {
        Assertions.assertFalse(blockedAccount.canAcceptForWithdraw(200));
    }

    @Test
    public void testLongOverflow() {
        if (BankAccount.isValidBalance(-1)) {
            accountBalance1000.withdraw(accountBalance1000.getBalance() + 1);
            Assertions.assertFalse(accountBalance1000.canAcceptForWithdraw(Long.MIN_VALUE));
        }
    }

    @Test
    public void testValueBelowCreditLimit() {
        if (BankAccount.getCreditLimit() > Long.MIN_VALUE) {
            accountBalance1000.withdraw(-BankAccount.getCreditLimit()
                    + accountBalance1000.getBalance());
            Assertions.assertFalse(accountBalance1000.canAcceptForWithdraw(1));
        }
    }

    @Test
    public void withdraw_LegalCase() {
        accountBalance1000.withdraw(200);
        Assertions.assertEquals(800L, accountBalance1000.getBalance());
    }

    @Test
    public void transferTo_LegalCase() {
        accountBalance1000.transferTo(100, accountBalance2000);
        Assertions.assertEquals(900L, accountBalance1000.getBalance());
        Assertions.assertEquals(2100L, accountBalance2000.getBalance());
    }

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

    @Test
    public void block_SingleCase() {
        unblockedAccount.block();
        Assertions.assertTrue(unblockedAccount.isBlocked());
    }

    @Test
    public void unblock_SingleCase() {
        blockedAccount.unblock();
        Assertions.assertFalse(blockedAccount.isBlocked());
    }

    @Test
    public void isValidToken_TrueCase() {
        Assertions.assertTrue(BankAccount.isValidToken("Abc12Z"));
    }

    @Test
    public void isValidToken_NonEffectiveToken() {
        Assertions.assertFalse(BankAccount.isValidToken(null));
    }

    @Test
    public void isValidToken_InvalidLength() {
        Assertions.assertFalse(BankAccount.isValidToken("abc"));
    }

    @Test
    public void canHaveAsTokenAt_TrueCase() {
        Assertions.assertTrue(accountWithTokens.canHaveAsTokenAt("Abc12Z",2));
    }

    @Test
    public void canHaveAsTokenAt_InvalidToken() {
        Assertions.assertFalse(accountWithTokens.canHaveAsTokenAt("Abc",2));
    }

    @Test
    public void canHaveAsTokenAt_NonPositiveIndex() {
        Assertions.assertFalse(accountWithTokens.canHaveAsTokenAt("AbcDef",0));
    }

    @Test
    public void canHaveAsTokenAt_IndexTooHigh() {
        Assertions.assertFalse(accountWithTokens.canHaveAsTokenAt("AbcDef",3));
    }

    @Test
    public void canHaveAsTokenAt_DuplicateToken() {
        Assertions.assertFalse(accountWithTokens.canHaveAsTokenAt("abcdef",2));
    }

    @Test
    public void setTokenAt_LegalCase() {
        accountWithTokens.setTokenAt("zyxwvu",2);
        Assertions.assertEquals("zyxwvu",accountWithTokens.getTokenAt(2));
    }

    @Test
    public void getTokens_singleCase() {
        String[] tokens = accountWithTokens.getTokens();
        Assertions.assertEquals(2,tokens.length);
        Assertions.assertEquals("abcdef",tokens[0]);
        Assertions.assertEquals("123456",tokens[1]);
    }

}
