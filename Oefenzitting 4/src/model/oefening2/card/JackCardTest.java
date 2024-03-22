package card;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class JackCardTest {

    JackCard jack;

    @BeforeEach
    public void setupBeforeEachTest() {
        jack = new JackCard();
    }

    @Test
    public void testInspectorMatchesOnJoker_Legal1() {
        assertTrue(jack.matchesOn(new JokerCard()));
    }

    @Test
    public void testInspectorMatchesOnJack_Legal2() {
        assertFalse(jack.matchesOn(new JackCard()));
    }

    @Test
    public void testInspectorMatchesOnNumbered_Legal1() {
        assertTrue(jack.matchesOnNumbered(new NumberedCard(10)));
    }

    @Test
    public void testInspectorMatchesOnNumbered_Legal2() {
        assertFalse(jack.matchesOnNumbered(new NumberedCard(8)));
    }

    @Test
    public void testInspectorMatchesOn_Legal1() {
        assertTrue(jack.matchesOn(new NumberedCard(10)));
    }

    @Test
    public void testInspectorMatchesOn_Legal2() {
        assertFalse(jack.matchesOn(new JackCard()));
    }
}

