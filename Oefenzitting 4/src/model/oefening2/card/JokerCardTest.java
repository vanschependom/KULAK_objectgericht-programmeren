package card;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class JokerCardTest {

    JokerCard joker;

    @BeforeEach
    public void setupBeforeEachTest() {
        joker = new JokerCard();
    }

    @Test
    public void testInspectorMatchesOnJoker() {
        assertTrue(joker.matchesOnJoker(new JokerCard()));
    }

    @Test
    public void testInspectorMatchesOnJack() {
        assertTrue(joker.matchesOnJack(new JackCard()));
    }

    @Test
    public void testInspectorMatchesOnNumbered() {
        assertTrue(joker.matchesOnNumbered(new NumberedCard(10)));
    }

    @Test
    public void testInspectorMatchesOn() {
        assertTrue(joker.matchesOn(new NumberedCard(5)));
    }
}

