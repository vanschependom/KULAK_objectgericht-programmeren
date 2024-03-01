package Chapter5.banking;

import java.math.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import Chapter5.state.Person;
import Chapter5.banking.exceptions.*;
import Chapter5.banking.money.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    private static BankAccount accountBalance0, accountBalance100, accountBalance500, accountUSD, accountWithBankCard;
    
    private static MoneyAmount EUR_50, EUR_100, EUR_M1000, USD_0;

    private static Person someAdult, someOtherAdult, someNonAdult;

	@BeforeEach
	public void setUpMutableFixture() {
        accountBalance0 = new BankAccount(1, someAdult, EUR_M1000, MoneyAmount.EUR_0, false);
		accountBalance100 = new BankAccount(1111111, someAdult, MoneyAmount.EUR_0, EUR_100, false);
		accountBalance500 = new BankAccount(2222222, someAdult, MoneyAmount.EUR_0, new MoneyAmount(new BigDecimal(500)), false);
	  	accountUSD = new BankAccount(2345,someAdult,USD_0,new MoneyAmount(BigDecimal.valueOf(200,2),Currency.USD),false);
	  	accountWithBankCard = new BankAccount(3456,someAdult);
	  	new BankCard(accountWithBankCard,1234);
	   }

	@AfterEach
	public void tearDownMutableFixture() {
		accountBalance0.terminate();
		accountBalance100.terminate();
		accountBalance500.terminate();
		accountUSD.terminate();
		accountWithBankCard.terminate();
	}

	@BeforeAll
	public static void setUpImmutableFixture() {
		EUR_50 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(50)));
		EUR_100 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(100)));
		EUR_M1000 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(-1000)));
		USD_0 = new MoneyAmount(BigDecimal.ZERO,Currency.USD);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
		cal.set(1978, Calendar.JANUARY, 28);
		someOtherAdult = new Person(cal.getTime());
		cal.set(2003, Calendar.SEPTEMBER, 11);
		someNonAdult = new Person(cal.getTime());
    }
    
    @Test
    public void extendedConstuctor$LegalCase() throws Exception {
        BankAccount theAccount = new BankAccount(12345, someAdult, MoneyAmount.EUR_0, MoneyAmount.EUR_1, false);
        assertEquals(12345, theAccount.getNumber());
        assertEquals(MoneyAmount.EUR_0, theAccount.getCreditLimit());
        assertEquals(MoneyAmount.EUR_1, theAccount.getBalance());
        assertFalse(theAccount.isBlocked());
        assertSame(someAdult,theAccount.getHolder());
        assertFalse(theAccount.hasBankCard());
        assertFalse(theAccount.isTerminated());
        theAccount.terminate();
    }

    @Test(expected = IllegalAmountException.class)
    public void extendedConstuctor$IllegalCreditLimit() throws Exception {
        new BankAccount(12345, someAdult, MoneyAmount.EUR_1, EUR_100, false);
        
    }
    @Test(expected = IllegalAmountException.class)
    public void extendedConstuctor$IllegalBalance() throws Exception {
        new BankAccount(12345, someAdult,  MoneyAmount.EUR_0, null, false);
    }

    @Test(expected = IllegalAmountException.class)
    public void extendedConstuctor$NonMatchingBalanceCreditLimit()
            throws Exception {
        new BankAccount(12345, someAdult, USD_0, EUR_100, false);
    }

    @Test(expected = IllegalAmountException.class)
    public void extendedConstuctor$NonMatchingBalanceCreditLimit2()
            throws Exception {
        // This case is not really needed in a pure black-box test.
        new BankAccount(12345, someAdult, MoneyAmount.EUR_0, MoneyAmount.EUR_0, false);
    }

    @Test(expected = IllegalNumberException.class)
    public void extendedConstuctor$InvalidNumber() throws Exception {
        new BankAccount(-10, someAdult, MoneyAmount.EUR_0, EUR_100, false);
    }

    @Test(expected = IllegalHolderException.class)
    public void extendedConstuctor$InvalidHolder() throws Exception {
        new BankAccount(1100, null, MoneyAmount.EUR_0, EUR_100, false);
    }

	@Test
	public void mediumConstructor_SingleCase() {
		BankAccount theAccount = new BankAccount(1234, someAdult);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(MoneyAmount.EUR_0, theAccount.getBalance());
		assertEquals(EUR_M1000, theAccount.getCreditLimit());
        assertFalse(theAccount.isBlocked());
        assertSame(someAdult,theAccount.getHolder());
        assertFalse(theAccount.hasBankCard());
        assertFalse(theAccount.isTerminated());
		theAccount.terminate();
	}
	
	@Test
	public void terminate_NonTerminatedAccountWithBankCard() {
		BankCard oldCard = accountWithBankCard.getBankCard();
		accountWithBankCard.terminate();
		assertTrue(accountWithBankCard.isTerminated());
		assertFalse(accountWithBankCard.hasBankCard());
		assertTrue(oldCard.isTerminated());
	}
	
	@Test
	public void terminate_NonTerminatedAccountWithoutBankCard() {
		accountBalance0.terminate();
		assertTrue(accountBalance0.isTerminated());
		assertFalse(accountBalance0.hasBankCard());
	}
	
	@Test
	public void terminate_TerminatedAccount() {
		accountWithBankCard.terminate();
		accountWithBankCard.terminate();
		assertTrue(accountWithBankCard.isTerminated());
	}

	@Test
	public void isValidBankCode_LegalValue() {
		assertTrue(BankAccount.isValidBankCode(100));
	}

	@Test
	public void isValidBankCode_IllegalValue() {
		assertFalse(BankAccount.isValidBankCode(-1));
	}

	@Test
	public void canHaveAsNumber_LegalValue() {
		assertTrue(accountBalance100.canHaveAsNumber(231));
	}

	@Test
	public void canHaveAsNumber_OwnNumber() {
		assertTrue(accountBalance100.canHaveAsNumber(accountBalance100
				.getNumber()));
	}

	@Test
	public void canHaveAsNumber_NonPositiveValue() {
		assertFalse(accountBalance100.canHaveAsNumber(0));
	}

	@Test
	public void canHaveAsNumber_DuplicateValue() {
		assertFalse(accountBalance100.canHaveAsNumber(accountBalance500
				.getNumber()));
	}
	
	@Test
	public void isValidHolder_TrueCase() {
		assertTrue(BankAccount.isValidHolder(someAdult));
	}
	
	@Test
	public void isValidHolder_NonEffectiveHolder() {
		assertFalse(BankAccount.isValidHolder(null));
	}
	
	@Test
	public void isValidHolder_NonAdultHolder() {
		assertFalse(BankAccount.isValidHolder(someNonAdult));
	}
	
	@Test
	public void setHolder_LegalCase() {
		accountBalance0.setHolder(someOtherAdult);
		assertSame(someOtherAdult,accountBalance0.getHolder());
	}

	@Test(expected=IllegalHolderException.class)
	public void setHolder_IllegalCase() throws Exception {
		accountBalance0.setHolder(someNonAdult);
	}
	
    @Test
    public void isPossibleCreditLimit$TrueCase() {
        assertTrue(BankAccount.isPossibleCreditLimit(EUR_M1000));
    }

    @Test
    public void isPossibleCreditLimit$NonEffectiveAmount() {
        assertFalse(BankAccount.isPossibleCreditLimit(null));
    }

    @Test
    public void isPossibleCreditLimit$NegativeAmount() {
        assertFalse(BankAccount.isPossibleCreditLimit(MoneyAmount.EUR_1));
    }

    @Test
    public void canHaveAsCreditLimit$TrueCase() throws Exception {
        assertTrue(accountBalance100.canHaveAsCreditLimit(EUR_M1000));
    }

    @Test
    public void canHaveAsCreditLimit$ImpossibleCreditLimit() throws Exception {
        assertFalse(accountBalance500.canHaveAsCreditLimit(EUR_100));
    }

    @Test
    public void canHaveAsCreditLimit$ConflictWithBalance() throws Exception {
        assertFalse(accountBalance0.canHaveAsCreditLimit(MoneyAmount.EUR_0));
    }

    @Test
    public void setCreditLimit$LegalCase() {
    	MoneyAmount EUR_M23 = new MoneyAmount(BigDecimal.valueOf(-2350, 2));
        accountBalance0.setCreditLimit(EUR_M23);
        assertEquals(EUR_M23, accountBalance0.getCreditLimit());
    }

    @Test(expected = IllegalAmountException.class)
    public void setCreditLimit$IllegalCase() {
        accountBalance0.setCreditLimit(EUR_100);
    }

    @Test
    public void isPossibleBalance$TrueCase() {
        assertTrue(BankAccount.isPossibleBalance(MoneyAmount.EUR_1));
    }

    @Test
    public void isPossibleBalance$FalseCase() {
        assertFalse(BankAccount.isPossibleBalance(null));
    }

    @Test
    public void canHaveAsBalance$TrueCase() throws Exception {
        assertTrue(accountBalance0.canHaveAsBalance(EUR_100));
    }

    @Test
    public void canHaveAsBalance$ImpossibleBalance() throws Exception {
        assertFalse(accountBalance0.canHaveAsBalance(null));
    }

    @Test
    public void canHaveAsBalance$ConflictWithCreditLimit() throws Exception {
        accountBalance0.setCreditLimit(EUR_M1000);
        assertFalse(accountBalance0.canHaveAsBalance(new MoneyAmount(BigDecimal.valueOf(-200000, 2))));
    }

    @Test
    public void matchesBalanceCreditLimit$TrueCase() {
        assertTrue(BankAccount.matchesBalanceCreditLimit(EUR_100, EUR_50));
    }

    @Test
    public void matchesBalanceCreditLimit$NonEffectiveBalance() {
        assertFalse(BankAccount.matchesBalanceCreditLimit(null, EUR_M1000));
    }

    @Test
    public void matchesBalanceCreditLimit$NonEffectiveCreditLimit() {
        assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_100,null));
    }
    @Test
    public void matchesBalanceCreditLimit$BalanceBelowLimit() {
        assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_50, EUR_100));
    }

    @Test
    public void matchesBalanceCreditLimit$EqualValues() {
        // Not really needed in a pure black-box test
        assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_50, EUR_50));
    }

    @Test
    public void hasHigherBalanceThanValue$TrueCase() {
        assertTrue(accountBalance0.hasHigherBalanceThan(EUR_M1000));
    }

    @Test
    public void hasHigherBalanceThanValue$BalanceLower() {
        assertFalse(accountBalance0.hasHigherBalanceThan(EUR_100));
    }

    @Test
    public void hasHigherBalanceThanValue$BalanceEqual() {
        assertFalse(accountBalance0.hasHigherBalanceThan(MoneyAmount.EUR_0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void hasHigherBalanceThanValue$NonEffectiveAmount() {
        accountBalance0.hasHigherBalanceThan((MoneyAmount)null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void hasHigherBalanceThanValue$DifferentCurrencies() {
        accountBalance0.hasHigherBalanceThan(USD_0);
    }

    @Test
    public void hasHigherBalanceThanAccount$TrueCase() {
        assertTrue(accountBalance100.hasHigherBalanceThan(accountBalance0));
    }

    @Test
    public void hasHigherBalanceThanAccount$FalseCase() {
        assertFalse(accountBalance0.hasHigherBalanceThan(accountBalance100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void hasHigherBalanceThanAccount$NonEffectiveAccount() throws Exception {
        accountBalance0.hasHigherBalanceThan((BankAccount)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void hasHigherBalanceThanAccount$DifferentCurrencies() throws Exception {
        accountBalance0.hasHigherBalanceThan(accountUSD);
    }

    @Test
    public void canAcceptForDeposit$TrueCase() {
        assertTrue(accountBalance0.canAcceptForDeposit(EUR_100));
    }

    @Test
    public void canAcceptForDeposit$NonEffectiveAmount() {
        assertFalse(accountBalance100.canAcceptForDeposit(null));
    }

    @Test
    public void canAcceptForDeposit$NegativeAmount() {
        assertFalse(accountBalance0.canAcceptForDeposit(EUR_M1000));
    }

    @Test
    public void canAcceptForDeposit$ZeroAmount() {
    	// This test is not really needed in a pure black-box test.
        assertFalse(accountBalance0.canAcceptForDeposit(MoneyAmount.EUR_0));
    }

    @Test
    public void deposit$LegalCase() throws Exception {
        accountBalance100.deposit(EUR_50);
        assertEquals(new MoneyAmount(new BigDecimal(150)), accountBalance100.getBalance());
    }

    @Test(expected = IllegalAmountException.class)
    public void deposit$UnacceptableAmount() throws Exception {
        accountBalance0.deposit(EUR_M1000);
    }

    @Test
    public void canAcceptForWithdraw$TrueCase() {
        assertTrue(accountBalance100.canAcceptForWithdraw(EUR_50));
    }

    @Test
    public void canAcceptForWithdraw$NonEffectiveAmount() {
        assertFalse(accountBalance100.canAcceptForWithdraw(null));
    }

    @Test
    public void canAcceptForWithdraw$NegativeAmount() {
    	accountBalance500.deposit(new MoneyAmount(new BigDecimal(2000)));
        assertFalse(accountBalance0.canAcceptForWithdraw(EUR_M1000));
    }

    @Test
    public void canAcceptForWithdraw$ZeroAmount() {
    	// Not really needed in a pure black-box test.
        assertFalse(accountBalance0.canAcceptForWithdraw(MoneyAmount.EUR_0));
    }

    @Test
    public void canAcceptForWithdraw$BlockedAccount() {
        accountBalance100.block();
        assertFalse(accountBalance100.canAcceptForWithdraw(EUR_50));
    }

    @Test
    public void canAcceptForWithdraw$ValueBelowCreditLimit() {
        accountBalance0.setCreditLimit(EUR_M1000);
        assertFalse(accountBalance0.canAcceptForWithdraw(new MoneyAmount(new BigDecimal(1001))));
    }

    @Test
    public void withdraw$LegalCase() throws Exception {
        accountBalance500.withdraw(EUR_50);
        assertEquals(new MoneyAmount(new BigDecimal(450)), accountBalance500.getBalance());
    }

    @Test
    public void withdraw$UnacceptableAmount() throws Exception {
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            accountBalance0.withdraw(null);
        });
    }
    
    @Test
    public void transferTo$LegalCase() {
        accountBalance100.transferTo(EUR_50, accountBalance500);
        assertEquals(new MoneyAmount(new BigDecimal(550)),accountBalance500.getBalance());
        assertEquals(EUR_50,accountBalance100.getBalance());
    }

    @Test
    public void transferTo$NonEffectiveDestination() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountBalance100.transferTo(EUR_50, null);
        });
    }

    @Test
    public void transferTo$SameDestination() {
        Assertions.assertThrows(IllegalAccountException.class, () -> {
            accountBalance100.transferTo(EUR_50, accountBalance100);
        });
    }
    
    @Test(expected = IllegalAmountException.class)
    public void transferTo$NonWithdrawableAmount() {
    	// Not really needed in a pure black-box test
        try {
            accountBalance100.setCreditLimit(EUR_M1000);
            accountBalance100.transferTo(new MoneyAmount(new BigDecimal(1500)), accountBalance500);
            fail();
        }
        catch (IllegalAmountException exc) {
            // Checking if the balance of both accounts is left untouched.
        	assertEquals(EUR_100,accountBalance100.getBalance());
            assertEquals(new MoneyAmount(new BigDecimal(500)), accountBalance500.getBalance());
            throw exc;
        }
    }
    
    @Test
    public void setBlocked$SingleCase() {
        accountBalance0.setBlocked(true);
        assertTrue(accountBalance0.isBlocked());
    }
    
    @Test
    public void block$SingleCase() {
        accountBalance0.block();
        assertTrue(accountBalance0.isBlocked());
    }
    
    @Test
    public void unblock$SingleCase() {
        accountBalance0.unblock();
        assertFalse(accountBalance0.isBlocked());
    }
    
    @Test
    public void hasBankCard_TrueCase() {
    	assertTrue(accountWithBankCard.hasBankCard());
    }
    
    @Test
    public void hasBankCard_FalseCase() {
    	assertFalse(accountBalance0.hasBankCard());
    }
    
    @Test
    public void hasProperBankCard_EffectiveCard() {
    	assertTrue(accountWithBankCard.hasProperBankCard());
    }
    
    @Test
    public void hasProperBankCard_NonEffectiveCard() {
    	assertTrue(accountBalance0.hasProperBankCard());
    }
    
    @Test
    public void setBankCard_EffectiveCard() {
    	BankCard oldCard = accountWithBankCard.getBankCard();
		accountWithBankCard.setBankCard(oldCard);
    	assertSame(oldCard,accountWithBankCard.getBankCard());
    }
    
    @Test
    public void setBankCard_NonEffectiveCardAccountWithoutBankCard() {
		accountBalance0.setBankCard(null);
    	assertNull(accountBalance0.getBankCard());
    }
    
    @Test
    public void setBankCard_NonEffectiveCardAccountWithBankCard() {
		accountWithBankCard.setBankCard(null);
    	assertNull(accountWithBankCard.getBankCard());
    }
    
    @Test
    public void toString_SingleCase() {
    	String result = accountBalance100.toString();
    	assertTrue(result.contains("Bank Account"));
    	assertTrue(result.contains(String.valueOf(accountBalance100.getNumber())));
    }

    @Test(expected=CloneNotSupportedException.class)
    public void clone_SingleCase() throws Exception {
    	accountBalance0.clone();
    }
}
