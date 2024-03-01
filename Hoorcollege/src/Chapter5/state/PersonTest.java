package Chapter5.state;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PersonTest {

	private static Date date80s, date00s;

	private static Calendar theCalendar;

	private static Person someAdult, someNonAdult;

	@BeforeEach
	public void setUpMutableFixture() throws Exception {
	}

	@BeforeAll
	public static void setUpImmutableFixture() throws Exception {
		theCalendar = new GregorianCalendar();
		theCalendar.set(1980, Calendar.MARCH, 4);
		date80s = theCalendar.getTime();
		theCalendar.set(2003, Calendar.SEPTEMBER, 11);
		date00s = theCalendar.getTime();
		someAdult = new Person(date80s);
		someNonAdult = new Person(date00s);
	}

	@Test
	public void extendedConstructor_LegalCase() {
		Person thePerson = new Person(date80s);
		assertEquals(date80s, thePerson.getBirthDate());
	}

	@Test
	public void extendedConstructor_IllegalBirthDate () {
		assertThrows(IllegalArgumentException.class, () -> new Person(null));
	}

	@Test
	public void isAdult_TrueCase() {
		assertTrue(someAdult.isAdult());
	}

	@Test
	public void isAdult_FalseCase() {
		assertFalse(someNonAdult.isAdult());
	}

	@Test
	public void isValidBirthDate_LegalCase() {
		assertTrue(Person.isValidBirthDate(date80s));
	}

	@Test
	public void isValidBirthDate_NonEffectiveDate() {
		assertFalse(Person.isValidBirthDate(null));
	}

	@Test
	public void isValidBirthDate_FutureDate() {
		theCalendar.set(2025, Calendar.JANUARY, 12);
		assertFalse(Person.isValidBirthDate(theCalendar.getTime()));
	}

}
