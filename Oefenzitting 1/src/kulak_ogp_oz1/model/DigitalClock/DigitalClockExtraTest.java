package kulak_ogp_oz1.model.DigitalClock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DigitalClockExtraTest {

	@Test
	public void testMutatorSetMinutes() {
		// Case 1: valid amount of minutes
		DigitalClockExtra clock = new DigitalClockExtra();
		clock.setMinutes(30);
		assertEquals(clock.getMinutes(), 30);
		
		// Case 2: negative amount of minutes
		clock = new DigitalClockExtra();
		clock.setMinutes(-5);
		assertEquals(clock.getMinutes(), 0);
		
		// Case 3: amount of minutes exceeds allowed amount
		clock = new DigitalClockExtra();
		clock.setMinutes(75);
		assertEquals(clock.getMinutes(), 0);
	}
	
	@Test
	public void testMutatorAdvanceSeconds() {
		// Case 1: no overflow when advancing seconds
		DigitalClockExtra clock = new DigitalClockExtra(0, 0, 0);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 1);
		
		
		// Case 2: overflow when advancing seconds
		clock = new DigitalClockExtra(0, 0, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 1);
		assertEquals(clock.getSeconds(), 0);
		
		
		clock = new DigitalClockExtra(0, 59, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 1);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 0);
		
		
		clock = new DigitalClockExtra(23, 59, 59);
		clock.advanceSeconds();
		
		assertEquals(clock.getHours(), 0);
		assertEquals(clock.getMinutes(), 0);
		assertEquals(clock.getSeconds(), 0);
	}
	
	@Test
	public void testInspectorTimeString() {
		DigitalClockExtra clock = new DigitalClockExtra(0, 0, 0, true);
		assertEquals(clock.getTimeAsString(), "00:00:00");
		
		clock = new DigitalClockExtra(0, 0, 0, false);
		assertEquals(clock.getTimeAsString(), "12:00:00 AM");
		
		clock = new DigitalClockExtra(5, 20, 45, true);
		assertEquals(clock.getTimeAsString(), "05:20:45");
		
		clock = new DigitalClockExtra(5, 20, 45, false);
		assertEquals(clock.getTimeAsString(), "05:20:45 AM");
		
		clock = new DigitalClockExtra(19, 0, 5, true);
		assertEquals(clock.getTimeAsString(), "19:00:05");
		
		clock = new DigitalClockExtra(19, 0, 5, false);
		assertEquals(clock.getTimeAsString(), "07:00:05 PM");
		
		clock = new DigitalClockExtra(23, 59, 59, false);
		assertEquals(clock.getTimeAsString(), "11:59:59 PM");
	}
	
	@Test
	public void testMutatorChangeAMPMIndicator() {
		DigitalClockExtra clock = new DigitalClockExtra(19, 0, 5, false);
		assertEquals(clock.getTimeAsString(), "07:00:05 PM");
		clock.changeAMPMIndicator();
		assertEquals(clock.getTimeAsString(), "07:00:05 AM");
	}
}
