package Chapter1;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankAccountTest {
    private static BankAccount accountBalance1000;
    private static BankAccount accountBalance2000;
    private static BankAccount accountBalance300;
    private static BankAccount accountBalance500;
    private static BankAccount accountLowestBalance;
    private static BankAccount accountHighestBalance;
    private static BankAccount blockedAccount;
    private static BankAccount unblockedAccount;

    public BankAccountTest() {
    }

    @Before
    public void setUpMutableFixture() {
        accountBalance1000 = new BankAccount(1111111, 1000L);
        accountBalance2000 = new BankAccount(2222222, 2000L);
        blockedAccount = new BankAccount(2121212, 333L, true);
        unblockedAccount = new BankAccount(1212121);
    }

    @BeforeClass
    public static void setUpImmutableFixture() {
        accountBalance300 = new BankAccount(1234567, 300L);
        accountBalance500 = new BankAccount(7654321, 500L);
        accountLowestBalance = new BankAccount(0, BankAccount.getCreditLimit());
        accountHighestBalance = new BankAccount(9999999, BankAccount.getBalanceLimit());
    }

    @Test
    public void extendedConstructor_LegalCase() {
        BankAccount theAccount = new BankAccount(1234, 300L, false);
        Assert.assertEquals(1234L, (long)theAccount.getNumber());
        Assert.assertEquals(300L, theAccount.getBalance());
        Assert.assertEquals(false, theAccount.isBlocked());
    }

    @Test
    public void extendedConstructor_NegativeNumber() {
        BankAccount theAccount = new BankAccount(-1, 300L, false);
        Assert.assertEquals(0L, (long)theAccount.getNumber());
        Assert.assertEquals(300L, theAccount.getBalance());
        Assert.assertEquals(false, theAccount.isBlocked());
    }

    @Test
    public void extendedConstructor_BalanceBelowLimit() {
        if (BankAccount.getCreditLimit() > Long.MIN_VALUE) {
            BankAccount theAccount = new BankAccount(12345, BankAccount.getCreditLimit() - 1L, false);
            Assert.assertEquals(12345L, (long)theAccount.getNumber());
            Assert.assertEquals(0L, theAccount.getBalance());
            Assert.assertEquals(false, theAccount.isBlocked());
        }

    }

    @Test
    public void extendedConstructor_BalanceAboveLimit() {
        if (BankAccount.getBalanceLimit() < Long.MAX_VALUE) {
            BankAccount theAccount = new BankAccount(12345, BankAccount.getBalanceLimit() + 1L, false);
            Assert.assertEquals(12345L, (long)theAccount.getNumber());
            Assert.assertEquals(0L, theAccount.getBalance());
            Assert.assertEquals(false, theAccount.isBlocked());
        }

    }

    @Test
    public void mediumConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234, 300L);
        Assert.assertEquals(1234L, (long)theAccount.getNumber());
        Assert.assertEquals(300L, theAccount.getBalance());
        Assert.assertEquals(false, theAccount.isBlocked());
    }

    @Test
    public void shortConstructor_SingleCase() {
        BankAccount theAccount = new BankAccount(1234);
        Assert.assertEquals(1234L, (long)theAccount.getNumber());
        Assert.assertEquals(0L, theAccount.getBalance());
        Assert.assertEquals(false, theAccount.isBlocked());
    }

    @Test
    public void hasHigherBalanceThanLong_HigherCase() {
        Assert.assertTrue(accountBalance300.hasHigherBalanceThan(200L));
    }

    @Test
    public void hasHigherBalanceThanLong_LowerCase() {
        Assert.assertFalse(accountBalance300.hasHigherBalanceThan(500L));
    }

    @Test
    public void hasHigherBalanceThanLong_EqualCase() {
        Assert.assertFalse(accountBalance300.hasHigherBalanceThan(300L));
    }

    @Test
    public void hasHigherBalanceThanAccount_EffectiveCase() {
        Assert.assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
    }

    @Test
    public void hasHigherBalanceThanAccount_NonEffectiveCase() {
        Assert.assertFalse(accountBalance500.hasHigherBalanceThan((BankAccount)null));
    }

    @Test
    public void deposit_LegalCase() {
        accountBalance1000.deposit(200L);
        Assert.assertEquals(1200L, accountBalance1000.getBalance());
    }

    @Test
    public void deposit_NegativeAmount() {
        accountBalance1000.deposit(-1200L);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void deposit_BalanceOverflow() {
        accountBalance1000.deposit(Long.MAX_VALUE);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void withdraw_LegalCase() {
        accountBalance1000.withdraw(200L);
        Assert.assertEquals(800L, accountBalance1000.getBalance());
    }

    @Test
    public void withdraw_NegativeAmount() {
        accountBalance1000.withdraw(-1200L);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void withdraw_BalanceOverflow() {
        accountBalance1000.withdraw(Long.MAX_VALUE);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void withdraw_BlockedAccount() {
        long oldBalance = blockedAccount.getBalance();
        blockedAccount.withdraw(200L);
        Assert.assertEquals(oldBalance, blockedAccount.getBalance());
    }

    @Test
    public void transferTo_LegalCase() {
        accountBalance1000.transferTo(100L, accountBalance2000);
        Assert.assertEquals(900L, accountBalance1000.getBalance());
        Assert.assertEquals(2100L, accountBalance2000.getBalance());
    }

    @Test
    public void transferTo_NonEffectiveDestination() {
        accountBalance1000.transferTo(100L, (BankAccount)null);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void transferTo_SameAccount() {
        accountBalance1000.transferTo(100L, accountBalance1000);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
    }

    @Test
    public void transferTo_NegativeAmount() {
        accountBalance1000.transferTo(-100L, accountBalance2000);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
        Assert.assertEquals(2000L, accountBalance2000.getBalance());
    }

    @Test
    public void transferTo_BlockedAccount() {
        long oldBalance = blockedAccount.getBalance();
        blockedAccount.transferTo(100L, accountBalance2000);
        Assert.assertEquals(oldBalance, blockedAccount.getBalance());
        Assert.assertEquals(2000L, accountBalance2000.getBalance());
    }

    @Test
    public void transferTo_BalanceOverflowOnSource() {
        accountLowestBalance.transferTo(100L, accountBalance2000);
        Assert.assertEquals(BankAccount.getCreditLimit(), accountLowestBalance.getBalance());
        Assert.assertEquals(2000L, accountBalance2000.getBalance());
    }

    @Test
    public void transferTo_BalanceOverflowOnDestination() {
        accountBalance1000.transferTo(100L, accountHighestBalance);
        Assert.assertEquals(1000L, accountBalance1000.getBalance());
        Assert.assertEquals(BankAccount.getBalanceLimit(), accountHighestBalance.getBalance());
    }

    @Test
    public void setCreditLimit_LegalCase() {
        BankAccount.setCreditLimit(-100L);
        Assert.assertEquals(-100L, BankAccount.getCreditLimit());
    }

    @Test
    public void setCreditLimit_IllegalCase() {
        long oldLimit = BankAccount.getCreditLimit();
        BankAccount.setCreditLimit(100L);
        Assert.assertEquals(oldLimit, BankAccount.getCreditLimit());
    }

    @Test
    public void setBlocked_TrueCase() {
        unblockedAccount.setBlocked(true);
        Assert.assertTrue(unblockedAccount.isBlocked());
    }

    @Test
    public void setBlocked_FalseCase() {
        blockedAccount.setBlocked(false);
        Assert.assertFalse(blockedAccount.isBlocked());
    }

    @Test
    public void block_SingleCase() {
        unblockedAccount.block();
        Assert.assertTrue(unblockedAccount.isBlocked());
    }

    @Test
    public void unblock_SingleCase() {
        blockedAccount.unblock();
        Assert.assertFalse(blockedAccount.isBlocked());
    }
}
