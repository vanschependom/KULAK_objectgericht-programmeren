package deck;


import card.JokerCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelpDeckTest {

    @Test
    public void testConstructorSingleCase() {
        HelpDeck deck = new HelpDeck(5);

        assertEquals(deck.getNbCards(), 0);
        assertEquals(deck.getCapacity(), 5);
    }

    @Test
    public void testInspectorTopMatchesOn_Legal() {
        HelpDeck help = new HelpDeck(5);
        help.push(new JokerCard());
        TargetDeck target = new TargetDeck(10, new JokerCard());

        assertTrue(help.topMatchesOn(target));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal1() {
        HelpDeck help = new HelpDeck(5);
        help.push(new JokerCard());

        assertFalse(help.topMatchesOn((TargetDeck) null));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal2() {
        HelpDeck help = new HelpDeck(5);
        TargetDeck target = new TargetDeck(10, new JokerCard());

        assertFalse(help.topMatchesOn(target));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal3() {
        HelpDeck help = new HelpDeck(5);
        help.push(new JokerCard());
        TargetDeck target = new TargetDeck(1, new JokerCard());

        assertFalse(help.topMatchesOn(target));
    }
}

