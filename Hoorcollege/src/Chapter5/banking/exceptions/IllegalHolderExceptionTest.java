package Chapter5.banking.exceptions;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import Chapter5.banking.*;
import Chapter5.state.*;

public class IllegalHolderExceptionTest {

	private static Person thePerson;

	@BeforeAll
	public static void setUpImmutableFixture() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		thePerson = new Person(cal.getTime());
	}

	@Test
		public void constructor_SingleCase() {
			BankAccount theAccount = new BankAccount(222,thePerson);
			IllegalHolderException exception = new IllegalHolderException(thePerson,theAccount);
			assertSame(thePerson,exception.getHolder());
			assertSame(theAccount,exception.getAccount());
			assertNull(exception.getMessage());
			assertNull(exception.getCause());
			theAccount.terminate();
		}
}
