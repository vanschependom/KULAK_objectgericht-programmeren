package model.oefening1.ownings;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


import model.oefening1.exceptions.IllegalOwnerException;
import model.oefening1.exceptions.IllegalValueException;

import org.junit.jupiter.api.*;
import model.oefening1.persons.Person;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

	private static Person someOwner;

	private static BigDecimal someValue;

	private Car someCar, terminatedCar;
	private static Date productionDate1Year, productionDate2Years, productionDate10Years, productionDateNow;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		someOwner = new Person();
		someValue = BigDecimal.valueOf(1000.0);

		int year = 365;
		productionDate1Year = Date.from(Instant.now().minus(year, ChronoUnit.DAYS));
		productionDate2Years = Date.from(Instant.now().minus(year * 2, ChronoUnit.DAYS));
		productionDate10Years = Date.from(Instant.now().minus(year * 10, ChronoUnit.DAYS));
		productionDateNow = Date.from(Instant.now());
	}

	@BeforeEach
	public void setUp() throws Exception {
		Person p = new Person();

		someCar = new Car(BigDecimal.valueOf(22222), 2000, CarTest.productionDate1Year);
		p.addCar(someCar);

		Person q = new Person();
		terminatedCar = new Car(BigDecimal.valueOf(30303), 1000, CarTest.productionDate2Years);
		q.addCar(terminatedCar);
		q.removeCar(terminatedCar);
		terminatedCar.terminate();
	}

	@Test
	public void extendedConstructor_LegalCase() throws Exception {
		Car theCar = new Car(someValue, 1500, CarTest.productionDate1Year);
		someOwner.addCar(theCar);
		assertEquals(someOwner, theCar.getOwner());
		assertTrue(someOwner.hasAsOwning(theCar));
		assertEquals(someValue.multiply(BigDecimal.valueOf(0.9)).doubleValue(), theCar.getValue().doubleValue(), 0.1);
		assertEquals(1500, theCar.getMotorVolume());
	}

	@Test
	public void extendedConstructor_IllegalMotorVolume() throws Exception {
		Car theCar = new Car(someValue, -100, CarTest.productionDateNow);
		someOwner.addCar(theCar);
		assertEquals(someOwner, theCar.getOwner());
		assertTrue(someOwner.hasAsOwning(theCar));
		assertEquals(someValue.doubleValue(), theCar.getValue().doubleValue(), 0.1);
		assertEquals(1000, theCar.getMotorVolume());
	}

	@Test
	public void extendedConstructor_IllegalOwner() {
		assertThrows(IllegalOwnerException.class, () -> {
			Person terminatedPerson = new Person();
			terminatedPerson.terminate();
			new Car(terminatedPerson, someValue, 1500, CarTest.productionDateNow);
		});
	}

	@Test
	public void extendedConstructor_IllegalValue() throws Exception {
		assertThrows(IllegalValueException.class, () -> {
			new Car(someOwner, null, 1500, CarTest.productionDateNow);
		});
	}

	@Test
	public void defaultConstructor_LegalCase() {
		Car theCar = new Car();
		assertNull(theCar.getOwner());
		assertEquals(BigDecimal.ZERO.compareTo(theCar.getValue()), 0);
		assertEquals(1000, theCar.getMotorVolume());
	}

	@Test
	public void isValidMotorVolume_IllegalVolume() {
		assertFalse(Car.isValidMotorVolume(-1));
	}

	@Test
	public void setMotorVolume_LegalVolume() {
		someCar.setMotorVolume(1000);
		assertEquals(1000, someCar.getMotorVolume());
	}

	@Test
	public void setMotorVolume_IllegalVolume() {
		if (!Car.isValidMotorVolume(-1000)) {
			someCar.setMotorVolume(-100);
			assertEquals(2000, someCar.getMotorVolume());
		}
	}

	@Test
	public void setMotorVolume_TerminatedCar() {
		int oldVolume = terminatedCar.getMotorVolume();
		terminatedCar.setMotorVolume(1000);
		assertEquals(oldVolume, terminatedCar.getMotorVolume());
	}

	@Test
	public void getCarValue_LegalCase() throws IllegalValueException {
		Car car1 = new Car(BigDecimal.valueOf(1000), 1000, CarTest.productionDate2Years);
		assertEquals(car1.getValue().doubleValue(), 800.0, 0.1);

		Car car2 = new Car(BigDecimal.valueOf(2000), 1000, CarTest.productionDateNow);
		assertEquals(car2.getValue().doubleValue(), 2000.0, 0.1);

		Car car3 = new Car(BigDecimal.valueOf(5000), 1000, CarTest.productionDate10Years);
		assertEquals(car3.getValue().doubleValue(), 2500.0, 0.1);
	}

}
