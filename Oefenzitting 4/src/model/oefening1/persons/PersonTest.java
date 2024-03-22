package model.oefening1.persons;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.NoSuchElementException;



import model.oefening1.exceptions.IllegalOwnerException;

import org.junit.jupiter.api.*;
import model.oefening1.ownings.Car;
import model.oefening1.ownings.Dog;
import model.oefening1.ownings.Ownable;
import model.oefening1.ownings.Painting;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

	private Person somePerson, personWithoutOwnings, personWithOwnings,
			terminatedPerson;

	private Ownable[] someOwnings;

	private Ownable terminatedOwnable;


	private static Date productionDate1Year, productionDateNow;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		int year = 365;
		productionDate1Year = Date.from(Instant.now().minus(year, ChronoUnit.DAYS));
		productionDateNow = Date.from(Instant.now());
	}

	@BeforeEach
	public void setUp() throws Exception {
		somePerson = new Person();
		personWithoutOwnings = new Person();
		personWithOwnings = new Person();
		terminatedPerson = new Person();
		terminatedPerson.terminate();

		Dog d = new Dog(BigDecimal.ZERO, "Bobby", 1000);
		Painting p = new Painting(BigDecimal.ZERO, "Waterfront", new Person());
		Car c = new Car(BigDecimal.valueOf(500.0), 1500, PersonTest.productionDate1Year);
		personWithOwnings.addDog(d);
		personWithOwnings.addPainting(p);
		personWithOwnings.addCar(c);

		someOwnings = new Ownable[3];
		someOwnings[0] = d;
		someOwnings[1] = p;
		someOwnings[2] = c;

		terminatedOwnable = new Dog("Bobby");
		terminatedOwnable.terminate();
	}

	@Test
	public void testSingleCase() {
		Person thePerson = new Person();
		assertEquals(0, thePerson.getNbOwnings());
		assertFalse(thePerson.isTerminated());
	}

	@Test
	public void terminate_PersonWithoutOwnings() {
		personWithoutOwnings.terminate();
		assertTrue(personWithoutOwnings.isTerminated());
	}

	@Test
	public void terminate_PersonWithOwnings() {
		personWithOwnings.terminate();
		assertTrue(personWithOwnings.isTerminated());
		for (Ownable owning : someOwnings)
			assertFalse(owning.hasOwner());
	}

	@Test
	public void terminate_PersonAlreadyTerminated() {
		terminatedPerson.terminate();
		assertTrue(terminatedPerson.isTerminated());
	}

	@Test
	public void sedtIsTerminated_SingleCase() {
		somePerson.setIsTerminated();
		assertTrue(somePerson.isTerminated());
	}

	@Test
	public void canHaveAsOwning_AcceptableOwnableOfOwner()
			throws Exception {
		Ownable owningOfSomePerson = new Dog(BigDecimal.TEN, "Bobby", 100);

		assertTrue(somePerson.canHaveAsOwning(owningOfSomePerson));
	}

	@Test
	public void canHaveAsOwning_AcceptableOwnableOfOtherPerson() {
		assertTrue(somePerson.canHaveAsOwning(someOwnings[0]));
	}

	@Test
	public void canHaveAsOwning_NonEffectiveOwnable() {
		assertFalse(somePerson.canHaveAsOwning(null));
	}

	@Test
	public void canHaveAsOwning_NonAcceptableOwnable() {
		someOwnings[0].terminate();
		assertFalse(somePerson.canHaveAsOwning(someOwnings[0]));
	}

	// Case in which bindings are not consistent cannot be tested.

	@Test
	public void getNbOwnings_SingleCase() {
		assertEquals(someOwnings.length, personWithOwnings.getNbOwnings());
	}

	@Test
	public void addToOwnings_LegalCase() throws Exception {
		Person formerOwner = someOwnings[0].getOwner();

		formerOwner.removeDog((Dog) someOwnings[0]);
		somePerson.addDog((Dog) someOwnings[0]);

		assertEquals(somePerson, someOwnings[0].getOwner());
		assertTrue(somePerson.hasAsOwning(someOwnings[0]));
		assertFalse(formerOwner.hasAsOwning(someOwnings[0]));
	}

	@Test
	public void addToOwnings_NonAcceptableOwnable() {
		assertThrows(IllegalOwnerException.class, () -> {
			terminatedOwnable.changeOwnerTo(somePerson);
		});
	}



	@Test
	public void getTotalValue_LegalCase() throws Exception {
		Dog d = new Dog(BigDecimal.valueOf(500), "Bobby", 1000);
		Painting p = new Painting(BigDecimal.valueOf(6000), "Waterfront", new Person());
		Car c = new Car(BigDecimal.valueOf(7500), 1500, PersonTest.productionDateNow);

		somePerson.addDog(d);
		somePerson.addPainting(p);
		somePerson.addCar(c);

		assertEquals(BigDecimal.valueOf(14000.0), somePerson.getTotalValue());
	}

	@Test
	public void getTotalValue_IllegalCase() {
		assertThrows(IllegalStateException.class, () -> {
			terminatedPerson.getTotalValue();
		});
	}

	@Test
	public void getTotalFoodAmount_LegalCase() throws Exception {
		Dog d1 = new Dog(somePerson, BigDecimal.valueOf(500), "Bobby", 1000);
		Painting p = new Painting(somePerson, BigDecimal.valueOf(6000), "Waterfront", new Person());
		Dog d2 = new Dog(somePerson, BigDecimal.valueOf(500), "Bessy", 400);
		Car c = new Car(somePerson, BigDecimal.valueOf(7500), 1500, PersonTest.productionDateNow);
		Dog d3 = new Dog(somePerson, BigDecimal.valueOf(500), "Lassy", 100);

		somePerson.addDog(d1);
		somePerson.addDog(d2);
		somePerson.addDog(d3);
		somePerson.addPainting(p);
		somePerson.addCar(c);

		assertEquals(BigInteger.valueOf(4500), somePerson.getTotalFoodAmount(3));
	}

	@Test
	public void getTotalFoodAmount_NegativeNumberOfDays(){
		assertThrows(IllegalArgumentException.class, () -> {
			somePerson.getTotalFoodAmount(-1);
		});
	}

	@Test
	public void getTotalFoodAmount_PersonAlreadyTerminated() {
		assertThrows(IllegalStateException.class, () -> {
			terminatedPerson.getTotalFoodAmount(3);
		});
	}

	@Test
	public void getMostPowerfulCar_LegalCase() throws Exception {

		Car car1 = new Car(BigDecimal.valueOf(3500), 1000, PersonTest.productionDateNow);
		Car car2 = new Car(BigDecimal.valueOf(5500), 1200, PersonTest.productionDateNow);
		Car car3 = new Car(BigDecimal.valueOf(7500), 800, PersonTest.productionDateNow);
		Car car4 = new Car(BigDecimal.valueOf(7500), 1500, PersonTest.productionDateNow);
		Dog dog = new Dog(BigDecimal.valueOf(500), "Bobby", 1000);
		Painting painting = new Painting(BigDecimal.valueOf(6000), "Waterfront", new Person());

		somePerson.addCar(car1);
		somePerson.addCar(car2);
		somePerson.addCar(car3);
		somePerson.addCar(car4);
		somePerson.addDog(dog);
		somePerson.addPainting(painting);

		assertSame(car4, somePerson.getMostPowerfulCar());
	}

	@Test
	public void getMostPowerfulCar_IllegalCase() {
		assertThrows(NoSuchElementException.class, () -> {
			personWithoutOwnings.getMostPowerfulCar();
		});
	}

	@Test
	public void getMostPowerfulCar_PersonAlreadyTerminated() {
		assertThrows(IllegalStateException.class, () -> {
			terminatedPerson.getMostPowerfulCar();
		});
	}

	@Test
	public void getPaintingBy_LegalCase() throws Exception {
		Person thePainter = new Person();

		Painting p1 = new Painting(somePerson, BigDecimal.valueOf(6000), "Waterfront", new Person());
		Dog d = new Dog(somePerson, BigDecimal.valueOf(500), "Bobby", 1000);
		Painting p2 = new Painting(somePerson, BigDecimal.valueOf(6000), "Landscape", new Person());
		Painting p3 = new Painting(somePerson, BigDecimal.valueOf(6000), "Portrait", thePainter);

		somePerson.addDog(d);
		somePerson.addPainting(p1);
		somePerson.addPainting(p2);
		somePerson.addPainting(p3);

		assertSame(p3, somePerson.getPaintingBy(thePainter));
	}

	@Test
	public void getPaintingBy_PersonWithoutPaintingsByThePainter() {
		assertThrows(NoSuchElementException.class, () -> {
			personWithoutOwnings.getPaintingBy(somePerson);
		});
	}

	@Test
	public void getPaintingBy_TerminatedPerson() {
		assertThrows(IllegalStateException.class, () -> {
			terminatedPerson.getPaintingBy(somePerson);
		});
	}



}
