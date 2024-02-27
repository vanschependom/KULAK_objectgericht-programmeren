package co.vis.kulak_ogp.oz1.model.DigitalClock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DigitalClockTest {

	@Test
	public void testMutatorSetMinutes() {
		// Case 1: valid amount of minutes
		DigitalClock clock = new DigitalClock();
		clock.setMinutes(30);
		assertEquals(clock.getMinutes(), 30);
		
		// Case 2: negative amount of minutes
		clock = new DigitalClock();
		clock.setMinutes(-5);
		assertEquals(clock.getMinutes(), 0);
		
		// Case 3: amount of minutes exceeds allowed amount
		clock = new DigitalClock();
		clock.setMinutes(75);
		assertEquals(clock.getMinutes(), 0);
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
