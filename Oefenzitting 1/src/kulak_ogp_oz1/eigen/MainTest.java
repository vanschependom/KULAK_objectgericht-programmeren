package kulak_ogp_oz1.eigen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Een klasse om olietanks te testen.
 *
 * @version 1.0
 * @author  Vincent Van Schependom
 */
public class MainTest {

    Oiltank ot1;
    Oiltank ot2;

    @BeforeEach
    public void setUpMutableFixture() {
        ot1 = new Oiltank(5000,3000);
        ot2 = new Oiltank(3000, 2000);
    }

    @Test
    public void transfer_illegal() {
        float initieel1 = ot1.getOil();
        float initieel2 = ot2.getOil();
        // draag meer over dan inhoud
        ot1.transfer(ot2,4000);
        Assertions.assertEquals(ot1.getOil(), initieel1);
        Assertions.assertEquals(ot2.getOil(), initieel2);
    }

    /**
     * A test for testing the most concise constructor.
     */
    @Test
    public void shortConstructor_legalCase() {
        Oiltank olietank5000 = new Oiltank();
        Assertions.assertEquals(olietank5000.getOil(), 0);
        Assertions.assertEquals(olietank5000.getCapacity(), 5000);
    }



}
