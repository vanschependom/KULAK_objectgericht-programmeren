package kulak_ogp_oz2.model.Defensive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitalClockTest {
	
	@Test
	public void testInvalidAmtInConstructor() {

		assertThrows(InvalidTimeException.class, () ->{
			new DigitalClock(-5, 45, 45);
		});
	}
	
	@Test
	public void testInvalidAmtOfHours() {
		assertThrows(InvalidTimeException.class, () -> {

		DigitalClock clock = new DigitalClock();
		clock.setHours(-5);
	});
	}
	
	@Test
	public void testInvalidAmtOfMinutes() {
		assertThrows(InvalidTimeException.class, () -> {
			DigitalClock clock = new DigitalClock();
			clock.setMinutes(65);
		});
	}
	
	@Test
	public void testInvalidAmtOfSeconds() {
		assertThrows(InvalidTimeException.class, () -> {
			DigitalClock clock = new DigitalClock();
			clock.setSeconds(60);
		});
	}

	@Test
	public void testMutatorSetMinutes() {
		// Case 1: valid amount of minutes
		DigitalClock clock = new DigitalClock();
		clock.setMinutes(30);
		assertEquals(clock.getMinutes(), 30);
	}
	
	@Test
	public void testMutatorAdvanceSeconds() {
		// Case 1: no overflow when advancing seconds
		DigitalClock clock = new DigitalClock(0, 0, 0);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 1);
		
		
		// Case 2: overflow when advancing seconds
		clock = new DigitalClock(0, 0, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 1);
		assertEquals(clock.getSeconds(), 0);
		
		
		clock = new DigitalClock(0, 59, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 1);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 0);
		
		
		clock = new DigitalClock(23, 59, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 0);
	}
	
}
