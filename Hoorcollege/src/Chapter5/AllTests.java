import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Chapter5.banking.exceptions.IllegalNumberExceptionTest;
import Chapter5.banking.money.MoneyAmountTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ banking.money.CurrencyTest.class, banking.money.MoneyAmountTest.class, banking.BankAccountTest.class,
	banking.BankCardTest.class, state.PersonTest.class,  banking.exceptions.IllegalAmountExceptionTest.class,
	banking.exceptions.IllegalNumberExceptionTest.class, banking.exceptions.IllegalAccountExceptionTest.class,
	banking.exceptions.IllegalHolderExceptionTest.class})
public class AllTests {
	
	@Test
	public void overallTest() {
		assertTrue(true);
	}
}