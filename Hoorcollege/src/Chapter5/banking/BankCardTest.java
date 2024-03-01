package Chapter5.banking;

import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import Chapter5.state.Person;
import Chapter5.banking.exceptions.*;
import Chapter5.banking.money.MoneyAmount;

public class BankCardTest {

	private static BankCard someCard, terminatedCard;

	private static BankAccount someAccount, otherAccount;

	private static Person someAdult;

	@BeforeAll
	public static void setUpImmutableFixture() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
	}

	@BeforeEach
	public void setUpMutableFixture() {
		someAccount = new BankAccount(2345, someAdult);
		otherAccount = new BankAccount(4567, someAdult);
		terminatedCard = new BankCard(someAccount, 2468);
		terminatedCard.terminate();
		someCard = new BankCard(someAccount, 7531);
	}

	@AfterEach
	public void tearDown() {
		someAccount.terminate();
		otherAccount.terminate();
	}

	@Test
	public void constructor_SingleCase() {
		BankAccount theAccount = new BankAccount(56789, someAdult);
		BankCard theCard = new BankCard(theAccount, 3680);
		assertSame(theAccount, theCard.getAccount());
		assertSame(theCard, theAccount.getBankCard());
		assertTrue(theCard.hasAsPinCode(3680));
		theAccount.terminate();
	}

	@Test
	public void terminate_NonTerminatedCard() {
		someCard.terminate();
		assertTrue(someCard.isTerminated());
		assertNull(someCard.getAccount());
		assertFalse(someAccount.hasBankCard());
	}

	@Test
	public void terminate_TerminatedCard() {
		terminatedCard.terminate();
		assertTrue(terminatedCard.isTerminated());
		assertNull(terminatedCard.getAccount());
	}

	@Test
	public void canHaveAsAccount_TerminatedCardTrueCase() {
		assertTrue(terminatedCard.canHaveAsAccount(null));
	}

	@Test
	public void canHaveAsAccount_TerminatedCardEffectiveAccount() {
		assertFalse(terminatedCard.canHaveAsAccount(someAccount));
	}

	@Test
	public void canHaveAsAccount_NonTerminatedCardTrueCase() {
		assertTrue(someCard.canHaveAsAccount(someAccount));
	}

	@Test
	public void canHaveAsAccount_NonTerminatedCardNonEffectiveAccount() {
		assertFalse(someCard.canHaveAsAccount(null));
	}

	@Test
	public void canHaveAsAccount_NonTerminatedCardTerminatedAccount() {
		BankAccount theAccount = new BankAccount(56789, someAdult);
		theAccount.terminate();
		assertFalse(someCard.canHaveAsAccount(theAccount));
	}

	@Test
	public void hasProperAccount_NonTerminatedCard() {
		assertTrue(someCard.hasProperAccount());
	}

	@Test
	public void hasProperAccount_TerminatedCard() {
		assertTrue(terminatedCard.hasProperAccount());
	}

	@Test
	public void transferTo_CardAttachedToOtherAccount() {
		BankAccount formerAccount = someCard.getAccount();
		someCard.transferTo(otherAccount);
		assertSame(otherAccount, someCard.getAccount());
		assertSame(someCard, otherAccount.getBankCard());
		assertFalse(formerAccount.hasBankCard());
	}

	@Test
	public void transferTo_CardAttachedToSameAccount() {
		BankAccount formerAccount = someCard.getAccount();
		someCard.transferTo(formerAccount);
		assertSame(formerAccount, someCard.getAccount());
		assertSame(someCard, formerAccount.getBankCard());
	}

	@Test
	public void transferTo_TerminatedCard() {
		assertThrows(IllegalStateException.class, () -> terminatedCard.transferTo(otherAccount));
	}

	@Test
	public void transferTo_IllegalAccount() {
		assertThrows(IllegalAccountException.class, () -> someCard.transferTo(null));
	}

	@Test
	public void transferTo_AccountWithOtherBankCard() {
		assertThrows(IllegalAccountException.class, () -> {
			new BankCard(otherAccount, 9876);
			someCard.transferTo(otherAccount);
		});
	}

	@Test
	public void isValidPinCode_TrueCase() {
		assertTrue(BankCard.isValidPinCode(1));
	}

	@Test
	public void isValidPinCode_NegativeCode() {
		assertFalse(BankCard.isValidPinCode(-1));
	}

	@Test
	public void isValidPinCode_CodeTooLarge() {
		assertFalse(BankCard.isValidPinCode(10000));
	}

	@Test
	public void setPinCode_LegalCase() {
		someCard.setPinCode(5678);
		someCard.setPinCode(9999);
		assertTrue(someCard.hasAsPinCode(9999));
	}

	@Test
	public void setPinCode_IllegalCase() {
		assertThrows(IllegalPinCodeException.class, () -> someCard.setPinCode(-100));
	}

	@Test
	public void withdraw_LegalCase() {
		someCard.getAccount().deposit(MoneyAmount.EUR_1);
		MoneyAmount oldBalance = someCard.getAccount().getBalance();
		MoneyAmount amount = new MoneyAmount(new BigDecimal(500));
		someCard.setPinCode(2345);
		someCard.getAccount().deposit(amount);
		someCard.withdraw(amount, 2345);
		assertEquals(oldBalance, someCard.getAccount().getBalance());
	}

}