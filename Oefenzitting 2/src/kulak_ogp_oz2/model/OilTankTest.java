package kulak_ogp_oz2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OilTankTest {
	
	@Test
	public void testCtorDefault() {
		OilTank myTank = new OilTank();
		assertEquals(myTank.getCapacity(), 5000);
		assertEquals(myTank.getContents(), 0);
	}
	
	@Test
	public void testCtorInitialCapacity() {
		// Case 1: positive capacity.
		OilTank myTank = new OilTank(1000);
		assertEquals(myTank.getCapacity(), 1000);
		assertEquals(myTank.getContents(), 0);
		
		// Case 2: negative capacity.
		OilTank yourTank = new OilTank(-1000);
		assertEquals(yourTank.getCapacity(), 0);
		assertEquals(yourTank.getContents(), 0);
	}
	
	@Test
	public void testCtorInitialCapacityContent() {
		// Case 1: positive capacity and contents.
		OilTank myTank = new OilTank(2000, 300);
		assertEquals(myTank.getCapacity(), 2000);
		assertEquals(myTank.getContents(), 300);
		
		// Case 2: negative capacity.
		myTank = new OilTank(-1000, 300);
		assertEquals(myTank.getCapacity(), 0);
		assertEquals(myTank.getContents(), 0);
		
		// Case 3: negative contents.
		myTank = new OilTank(1000, -300);
		assertEquals(myTank.getCapacity(), 1000);
		assertEquals(myTank.getContents(), 0);
		
		// Case 4: contents exceeding capacity.
		myTank = new OilTank(500, 800);
		assertEquals(myTank.getCapacity(), 500);
		assertEquals(myTank.getContents(), 500);
	}
	
	@Test
	public void testMutatorFill() {		
		// Default fill without arguments (completely fill tank)
		OilTank tank = new OilTank(3000);
		tank.fill();
		assertEquals(tank.getContents(), tank.getCapacity());
		
		// Fill method with provided arguments
		// Case 1: positive amount not exceeding the capacity.
		tank = new OilTank(1000);
		tank.fill(500);
		assertEquals(tank.getContents(), 500);
		
		// Case 2: negative amount.
		tank = new OilTank(3000, 2000);
		tank.fill(-300);
		assertEquals(tank.getContents(), 2000);
		
		// Case 3: amount exceeding the capacity.
		tank = new OilTank(3000, 2000);
		tank.fill(tank.getCapacity() + 100);
		assertEquals(tank.getContents(), tank.getCapacity());
	}
	
	@Test
	public void testMutatorExtract() {
		// Default extract without arguments
		OilTank tank = new OilTank(3000, 2000);
		tank.extract();
		assertEquals(tank.getContents(), 0);
		
		// Extract method with provided arguments
		// Case 1: positive amount not exceeding the contents.
		tank = new OilTank(1000, 1000);
		tank.extract(300);
		assertEquals(tank.getContents(), 700);
		
		// Case 2: negative amount.
		tank = new OilTank(1000, 500);
		tank.extract(-300); 
		assertEquals(tank.getContents(), 500);
		
		// Case 3: amount exceeding the contents.
		tank = new OilTank(1000, 500);
		tank.extract(tank.getContents() + 100);
		assertEquals(tank.getContents(), 0);
	}
	
	
	@Test
	public void testMutatorTransferFrom() {
		// Case 1: the contents of the source tank fully fits in the target tank.
		OilTank tank1 = new OilTank(3000, 200);
		OilTank tank2 = new OilTank(2500, 500);
		tank1.transferFrom(tank2);
		assertEquals(tank1.getContents(), 700);
		assertEquals(tank2.getContents(), 0);
		
		// Case 2: the contents of the source tank does not fully fit.
		tank1 = new OilTank(3000, 2000);
		tank2 = new OilTank(2500, 2000);
		tank1.transferFrom(tank2);
		assertEquals(tank1.getContents(), tank1.getCapacity());
		assertEquals(tank2.getContents(), 1000);
			
		// Case 3: non-effective source tank.
		tank1 = new OilTank(3000, 2000);
		tank1.transferFrom(null);
		assertEquals(tank1.getContents(), 2000);
			
		// Case 4: source tank coincides with target tank.
		tank1 = new OilTank(3000, 2000);
		tank1.transferFrom(tank1);
		assertEquals(tank1.getContents(), 2000);
	}

	@Test
	public void testInspectorGetFree() {
		OilTank myTank = new OilTank(1000, 700);
		assertEquals(myTank.getFree(), 300);
	}
	
	@Test
	public void testInspectorFullerThan() {
		// Case 1: the other tank exists.
		OilTank myTank = new OilTank(1000, 800);
		OilTank yourTank = new OilTank(500, 450);
		assertFalse(myTank.isFullerThan(yourTank));
		
		// Case 2: the other tank does not exist.
		myTank = new OilTank(1000, 800);
		assertFalse(myTank.isFullerThan(null));
	}

	@Test
	public void testInspectorPercentageFilled() {
		// Case 1: non zero capacity.
		OilTank myTank = new OilTank(1000, 750);
		assertEquals(myTank.getPercentageFilled(), 75.0, 0.001);
		
		// Case 2: zero capacity.
		myTank = new OilTank(0);
		assertEquals(myTank.getPercentageFilled(), 100.0, 0.001);
	}

	@Test
	public void testInspectorGetMostFree() {
		OilTank myTank = new OilTank(1000, 750);
		OilTank yourTank = new OilTank(2000, 1800);
		assertEquals(myTank.getMostFree(yourTank, null), myTank);
	}
	
	@Test
	public void testInspectorToString() {
		OilTank myTank = new OilTank(1000, 750);
		assertEquals(myTank.toString(), "Oil Tank with Capacity 1000 and Contents 750");
	}		

	@Test
	public void testInspectorIsSame() {
		OilTank myTank = new OilTank(1000, 750);
		OilTank yourTank = new OilTank(1000, 750);
		assertTrue(myTank.isSame(yourTank));
	}

	@Test
	public void testInspectorGetCopy() {
		OilTank myTank = new OilTank(1000, 750);
		OilTank yourTank = myTank.getCopy();

		assertTrue(myTank.isSame(yourTank));
	}
}
