package model.oefening1.ownings;

import java.math.BigDecimal;

import model.oefening1.exceptions.IllegalFoodAmountException;
import model.oefening1.exceptions.IllegalNameException;
import model.oefening1.exceptions.IllegalOwnerException;
import model.oefening1.exceptions.IllegalValueException;

import org.junit.jupiter.api.*;
import model.oefening1.persons.Person;

import static org.junit.jupiter.api.Assertions.*;

public class DogTest {

	private static Person somePerson;

	private static BigDecimal someValue;

	private Dog someDog, terminatedDog;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		somePerson = new Person();
		someValue = BigDecimal.valueOf(1000);
	}

	@BeforeEach
	public void setUp() throws Exception {
		someDog = new Dog("Bobby");
		terminatedDog = new Dog("Bessy");
		terminatedDog.terminate();
	}

	@Test
	public void extendedConstructor_LegalCase() throws Exception {
		Dog theDog = new Dog(someValue, "Bobby", 100);
		somePerson.addDog(theDog);
		assertEquals(somePerson, theDog.getOwner());
		assertTrue(somePerson.hasAsOwning(theDog));
		assertEquals(someValue, theDog.getValue());
		assertEquals("Bobby", theDog.getName());
		assertEquals(100, theDog.getDailyFoodAmount());
	}

	@Test
	public void extendedConstructor_IllegalOwner() {
		assertThrows(IllegalOwnerException.class, () -> {
			Person terminatedPerson = new Person();
			terminatedPerson.terminate();
			new Dog(terminatedPerson, someValue, "Bobby", 100);
		});
	}

	@Test
	public void extendedConstructor_IllegalValue() {
		assertThrows(IllegalValueException.class, () -> {
			new Dog(somePerson, null, "Bobby", 100);
		});
	}

	@Test
	public void extendedConstructor_IllegalName() {
		assertThrows(IllegalNameException.class, () -> {
			new Dog(somePerson, someValue, null, 100);
		});
	}

	@Test
	public void extendedConstructor_IllegalDailyFoodAmount() {
		assertThrows(IllegalFoodAmountException.class, () -> {
			new Dog(somePerson, someValue, "Bobby", -100);
		});
	}

	@Test
	public void simpleConstructor_LegalCase() throws Exception {
		Dog theDog = new Dog("Bobby");
		assertNull(theDog.getOwner());
		assertEquals(BigDecimal.ZERO, theDog.getValue());
		assertEquals("Bobby", theDog.getName());
		assertEquals(500, theDog.getDailyFoodAmount());
	}

	@Test
	public void simpleConstructor_IllegalName() {
		assertThrows(IllegalNameException.class, () -> {
			new Dog(null);
		});
	}

	@Test
	public void testLegalName() {
		assertTrue(Dog.isValidName("Bobby"));
	}

	@Test
	public void testNonEffectiveName() {
		assertFalse(Dog.isValidName(null));
	}

	@Test
	public void testEmptyName() {
		assertFalse(Dog.isValidName(""));
	}

	@Test
	public void setName_LegalCase() throws Exception {
		someDog.setName("Lassy");
		assertEquals("Lassy", someDog.getName());
	}

	@Test
	public void setName_InvalidName() {
		assertThrows(IllegalNameException.class, () -> {
			someDog.setName("");
		});
	}

	@Test
	public void setName_TerminatedDog() {
		assertThrows(IllegalStateException.class, () -> {
			terminatedDog.setName("Lassy");
		});
	}
	
	@Test
	public void isValidDailyFoodAmount_TrueCase() {
		assertTrue(Dog.isValidDailyFoodAmount(1));
	}

	@Test
	public void isValidDailyFoodAmount_FalseCase() {
		assertFalse(Dog.isValidDailyFoodAmount(0));
	}

	@Test
	public void setDailyFoodAmount_LegalCase() throws Exception {
		someDog.setDailyFoodAmount(100);
		assertEquals(100, someDog.getDailyFoodAmount());
	}

	@Test
	public void setDailyFoodAmount_IllegalCase() {
		assertThrows(IllegalFoodAmountException.class, () -> {
			someDog.setDailyFoodAmount(0);
		});
	}

}
