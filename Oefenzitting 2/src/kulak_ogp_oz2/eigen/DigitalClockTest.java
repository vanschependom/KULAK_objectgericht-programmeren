package kulak_ogp_oz2.eigen;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalClockTest {

    @Test
    public void testMutatorSetMinutes() {

        DigitalClock myClock = new DigitalClock();

        // Check constructor
        assertEquals(myClock.getHours(), 0);

        // Legal case
        myClock.setMinutes(30);
        assertEquals(myClock.getMinutes(), 30);

        // minutes < minimum
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            int minValue = DigitalClock.getMinMinutes();
            myClock.setMinutes(minValue - 10);
        });

        // minutes > maximum
        Assertions.assertThrows(IllegalAmountException.class, () -> {
            int maxValue = DigitalClock.getMaxMinutes();
            myClock.setMinutes(maxValue + 10);
        });

    }

    @Test
    public void testMutatorAdvanceSeconds() {

        DigitalClock clock1 = new DigitalClock();

        // Check constructor
        assertEquals(clock1.getSeconds(), 0);

        clock1.advanceSeconds();
        assertEquals(clock1.getSeconds(), 1);

        DigitalClock clock2 = new DigitalClock(0, 0, 59);

        clock2.advanceSeconds();
        assertEquals(clock2.getSeconds(), 0);
        assertEquals(clock2.getMinutes(), 1);
        assertEquals(clock2.getHours(), 0);

        DigitalClock clock3 = new DigitalClock(0, 59, 59);

        clock3.advanceSeconds();
        assertEquals(clock3.getSeconds(), 0);
        assertEquals(clock3.getMinutes(), 0);
        assertEquals(clock3.getHours(), 1);

    }

}
