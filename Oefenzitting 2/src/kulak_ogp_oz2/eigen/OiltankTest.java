package kulak_ogp_oz2.eigen;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OiltankTest {

    @Test
    public void testFill () {

        OilTank myOilTank = new OilTank(5000,0);

        assertEquals(myOilTank.getContents(), 0);
        assertEquals(myOilTank.getCapacity(), 5000);

        myOilTank.fill();

        assertEquals(myOilTank.getContents(),5000);

    }

}
