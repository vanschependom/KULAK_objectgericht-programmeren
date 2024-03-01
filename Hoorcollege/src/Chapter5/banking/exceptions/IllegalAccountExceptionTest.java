package Chapter5.banking.exceptions;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import Chapter5.state.Person;
import Chapter5.banking.*;

public class IllegalAccountExceptionTest {

	private static Person someAdult;
	
	@BeforeAll
	public static void setUpImmutableFixture() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
	}

	@Test
	public void constructor_SingleCase() {
		BankAccount theAccount = new BankAccount(222,someAdult);
		BankCard theCard = new BankCard(theAccount,4567);
		IllegalAccountException exception = new IllegalAccountException(theAccount,theCard);
		assertSame(theAccount,exception.getAccount());
		assertSame(theCard,exception.getBankCard());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}

}
