package card;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class NumberedCardTest {

    NumberedCard card6, card7, card10;

    @BeforeEach
    public void setupBeforeEachTest() {
        card6 = new NumberedCard(6);
        card7 = new NumberedCard(7);
        card10 = new NumberedCard(10);
    }

    @Test
    public void testConstructorSingleCase() {
        assertEquals(card7.getValue(), 7);
    }

    @Test
    public void testInspectorMatchesOnJoker_Legal() {
        assertTrue(card6.matchesOnJoker(new JokerCard()));
    }

    @Test
    public void testInspectorMatchesOnJack_Legal1() {
        assertTrue(card10.matchesOnJack(new JackCard()));
    }

    @Test
    public void testInspectorMatchesOnJack_Legal2() {
        assertFalse(card6.matchesOnJack(new JackCard()));
    }

    @Test
    public void testInspectorMatchesOnNumbered_Legal1() {
        assertTrue(card6.matchesOnNumbered(new NumberedCard(5)));
    }

    @Test
    public void testInspectorMatchesOnNumbered_Legal2() {
        assertTrue(card6.matchesOnNumbered(new NumberedCard(7)));
    }

    @Test
    public void testInspectorMatchesOnNumbered_Legal3() {
        assertFalse(card6.matchesOnNumbered(new NumberedCard(8)));
    }

    @Test
    public void testInspectorMatchesOn_Legal1() {
        assertTrue(card6.matchesOn(new NumberedCard(5)));
    }

    @Test
    public void testInspectorMatchesOn_Legal2() {
        assertFalse(card6.matchesOn(new NumberedCard(3)));
    }
}

