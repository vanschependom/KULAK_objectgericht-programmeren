package model.oefening1.ownings;


import java.math.BigDecimal;


import org.junit.jupiter.api.*;
import model.oefening1.persons.Person;

import model.oefening1.exceptions.IllegalOwnerException;
import model.oefening1.exceptions.IllegalValueException;

import static org.junit.jupiter.api.Assertions.*;

public class OwnableTest {

	private Painting ownableWithOwner, ownableWithoutOwner;
	private Dog someOwnable, terminatedOwnable;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {}

	@BeforeEach
	public void setUp() throws Exception {
		someOwnable = new Dog("Bobby");

		Person person = new Person();
		ownableWithOwner = new Painting(person, BigDecimal.valueOf(100),
			"Title", new Person());
		person.addPainting(ownableWithOwner);

		ownableWithoutOwner = new Painting("Waterfront");
		terminatedOwnable = new Dog("Bessy");
		terminatedOwnable.terminate();
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableLegalValue() {
		assertTrue(someOwnable.canHaveAsValue(BigDecimal.ZERO));
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableIllegalValue() {
		assertFalse(someOwnable.canHaveAsValue(BigDecimal.valueOf(-1)));
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableNonEffectiveValue() {
		assertFalse(someOwnable.canHaveAsValue(null));
	}

	@Test
	public void canHaveAsValue_TerminatedOwnableNonEffectiveValue() {
		assertTrue(terminatedOwnable.canHaveAsValue(null));
	}

	@Test
	public void canHaveAsValue_TerminatedOwnableEffectiveValue() {
		// Not really needed in a black-box test.
		assertTrue(terminatedOwnable.canHaveAsValue(BigDecimal.ZERO));
	}

	@Test
	public void setValue_LegalEffectiveValue() throws Exception {
		BigDecimal theValue = BigDecimal.valueOf(100);
		someOwnable.setValue(theValue);
		assertSame(theValue, someOwnable.getValue());
	}

	@Test
	public void setValue_LegalNonEffectiveValue() throws Exception {
		someOwnable.setValue(null);
		assertNull(someOwnable.getValue());
	}

	@Test
	public void setValue_IllegalValue() throws Exception {
		assertThrows(IllegalValueException.class, () -> {
			someOwnable.setValue(BigDecimal.valueOf(-100));
		});
	}

	@Test
	public void set_ValueTerminatedOwnable() throws Exception {
		assertThrows(IllegalStateException.class, () -> {
			terminatedOwnable.setValue(BigDecimal.valueOf(-100));
		});
	}

	@Test
	public void canHaveAsOwner_NonEffectiveOwner() {
		assertTrue(someOwnable.canHaveAsOwner(null));
	}

	@Test
	public void canHaveAsOwner_TerminatedOwner() {
		Person terminatedPerson = new Person();
		terminatedPerson.terminate();
		assertFalse(someOwnable.canHaveAsOwner(terminatedPerson));
	}

	@Test
	public void canHaveAsOwner_TerminatedOwnable() {
		assertFalse(terminatedOwnable.canHaveAsOwner(new Person()));
	}

	@Test
	public void canHaveAsOwner_NotOwner() {
		Person otherPerson = new Person();
		assertTrue(ownableWithOwner.canHaveAsOwner(otherPerson));
	}

	@Test
	public void canHaveAsOwner_Owner() {
		assertTrue(ownableWithOwner.canHaveAsOwner(ownableWithOwner.getOwner()));
	}

	@Test
	public void hasOwner_TrueCase() {
		assertTrue(ownableWithOwner.hasOwner());
	}

	@Test
	public void hasOwner_FalseCase() {
		assertFalse(ownableWithoutOwner.hasOwner());
	}

	@Test
	public void changeOwner_LegalOwnerForOwnableWithoutOwner() throws Exception {
		Person newOwner = new Person();
		newOwner.addPainting(ownableWithoutOwner);
		ownableWithoutOwner.changeOwnerTo(newOwner);
		assertSame(newOwner, ownableWithoutOwner.getOwner());
		assertTrue(newOwner.hasAsOwning(ownableWithoutOwner));
	}

	@Test
	public void changeOwner_LegalOwnerForOwnableWithOwner() throws Exception {
		Person formerOwner = ownableWithOwner.getOwner();
		Person newOwner = new Person();
		formerOwner.removePainting(ownableWithOwner);
		newOwner.addPainting(ownableWithOwner);
		assertSame(newOwner, ownableWithOwner.getOwner());
		assertTrue(newOwner.hasAsOwning(ownableWithOwner));
		assertFalse(formerOwner.hasAsOwning(ownableWithOwner));
	}

	@Test
	public void changeOwner_LegalOwnerForOwnableWithSameOwner() throws Exception {
		Person formerOwner = ownableWithOwner.getOwner();
		ownableWithOwner.changeOwnerTo(formerOwner);
		assertSame(formerOwner, ownableWithOwner.getOwner());
		assertTrue(formerOwner.hasAsOwning(ownableWithOwner));
	}

	@Test
	public void changeOwner_IllegalOwner() {
		assertThrows(IllegalOwnerException.class, () -> {
			Person terminatedPerson = new Person();
			terminatedPerson.terminate();
			ownableWithoutOwner.changeOwnerTo(terminatedPerson);
		});
	}

	@Test
	public void changeOwner_NonEffectiveOwner() {
		assertThrows(IllegalArgumentException.class, () -> {
			ownableWithoutOwner.changeOwnerTo(null);
		});
	}

	@Test
	public void removeOwner_OwnableWithoutOwner() {
		ownableWithoutOwner.removeOwner();
		assertFalse(ownableWithoutOwner.hasOwner());
	}

	@Test
	public void removeOwner_OwnableWithOwner() {
		Person formerOwner = ownableWithOwner.getOwner();
		formerOwner.removePainting(ownableWithOwner);
		assertFalse(ownableWithOwner.hasOwner());
		assertFalse(formerOwner.hasAsOwning(ownableWithOwner));
	}
	
	@Test
	public void setOwner_SingleCase() {
		Person newOwner = new Person();
		someOwnable.setOwner(newOwner);
		assertEquals(newOwner,someOwnable.getOwner());
	}

	@Test
	public void terminate_OwnableNotOwned() {
		ownableWithoutOwner.terminate();
		assertTrue(ownableWithoutOwner.isTerminated());
	}

	@Test
	public void terminate_OwnableOwned() {
		Person formerOwner = ownableWithOwner.getOwner();
		formerOwner.removePainting(ownableWithOwner);
		ownableWithOwner.terminate();
		assertTrue(ownableWithOwner.isTerminated());
		assertFalse(formerOwner.hasAsOwning(ownableWithOwner));
	}

	@Test
	public void terminate_AlreadyTerminatedOwnable() {
		assertThrows(AssertionError.class, () -> {
			terminatedOwnable.terminate();
		});
	}

	@Test
	public void setIsTerminated_SingleCase() {
		someOwnable.setIsTerminated();
		assertTrue(someOwnable.isTerminated());
		assertTrue(someOwnable.canHaveAsValue(someOwnable.getValue()));
	}

}
