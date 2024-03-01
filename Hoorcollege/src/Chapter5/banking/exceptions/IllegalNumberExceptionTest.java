package Chapter5.banking.exceptions;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import Chapter5.banking.BankAccount;
import Chapter5.state.Person;

public class IllegalNumberExceptionTest {

	private static Person someAdult;
	
	@BeforeAll
	public static void setUpImmutableFixture() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
	}


	@Test
	public void constructor_SingleCase() {
		BankAccount theAccount = new BankAccount(7777,someAdult);
		IllegalNumberException exception = new IllegalNumberException(2000,theAccount);
		assertEquals(2000,exception.getNumber());
		assertSame(theAccount,exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
