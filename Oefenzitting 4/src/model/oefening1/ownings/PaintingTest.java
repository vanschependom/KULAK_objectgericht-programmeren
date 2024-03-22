package model.oefening1.ownings;


import java.math.BigDecimal;



import model.oefening1.exceptions.*;

import org.junit.jupiter.api.*;
import model.oefening1.persons.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PaintingTest {

	private static Person someOwner, somePainter;

	private static BigDecimal someValue;
	private Painting somePainting;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		someOwner = new Person();
		somePainter = new Person();
		someValue = BigDecimal.valueOf(1000);
	}

	@BeforeEach
	public void setUp() throws Exception {
		somePainting = new Painting("Portrait");
	}

	@Test
	public void extendedConstructor_LegalCase() throws Exception {
		Painting thePainting = new Painting(someValue, "Landscape", somePainter);
		someOwner.addPainting(thePainting);
		
		assertEquals(someOwner, thePainting.getOwner());
		assertTrue(someOwner.hasAsOwning(thePainting));
		assertEquals(someValue, thePainting.getValue());
		assertEquals("Landscape", thePainting.getTitle());
		assertEquals(somePainter, thePainting.getPainter());
	}

	@Test
	public void extendedConstructor_IllegalOwner(){
		assertThrows(IllegalOwnerException.class, () -> {
			Person terminatedPerson = new Person();
			terminatedPerson.terminate();
			new Painting(terminatedPerson, someValue, "Landscape", somePainter);
		});
	}

	@Test
	public void extendedConstructor_IllegalValue() throws Exception {
		assertThrows(IllegalValueException.class, () -> {
			new Painting(someOwner, null, "Landscape", somePainter);
		});
	}
	
	@Test
	public void testLegalCase() throws Exception {
		Painting thePainting = new Painting("Landscape");
		assertNull(thePainting.getOwner());
		assertEquals(BigDecimal.ZERO, thePainting.getValue());
		assertEquals("Landscape", thePainting.getTitle());
		assertNull(thePainting.getPainter());
	}
	
	@Test
	public void isValidTitle_LegalTitle() {
		assertTrue(Painting.isValidTitle("Waterfront"));
	}

	@Test
	public void isValidTitle_NonEffectiveTitle() {
		assertFalse(Painting.isValidTitle(null));
	}

	@Test
	public void isValidTitle_EmptyTitle() {
		assertFalse(Painting.isValidTitle(""));
	}
	
	@Test
	public void setTitle_SingleCase() {
		somePainting.setTitle("Landscape");
		assertEquals("Landscape",somePainting.getTitle());
	}
	
	@Test
	public void setPainter_SingleCase() {
		Person newPainter = new Person();
		somePainting.setPainter(newPainter);
		assertEquals(newPainter,somePainting.getPainter());
	}

}
