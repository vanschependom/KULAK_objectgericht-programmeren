package model.oefening2.deck;

import model.oefening2.card.JackCard;
import model.oefening2.card.JokerCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SourceDeckTest {

    @Test
    public void testConstructorSingleCase() {
        SourceDeck deck = new SourceDeck(5);

        assertEquals(deck.getNbCards(), 0);
        assertEquals(deck.getCapacity(), 5);
    }

    @Test
    public void testInspectorTopMatchesOn_Legal() {
        SourceDeck source = new SourceDeck(5);
        source.push(new JokerCard());
        TargetDeck target = new TargetDeck(10, new JokerCard());

        assertTrue(source.topMatchesOn(target));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal1() {
        SourceDeck source = new SourceDeck(5);
        source.push(new JokerCard());

        assertFalse(source.topMatchesOn((TargetDeck) null));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal2() {
        SourceDeck source = new SourceDeck(5);
        TargetDeck target = new TargetDeck(10, new JokerCard());

        assertFalse(source.topMatchesOn(target));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal3() {
        SourceDeck source = new SourceDeck(5);
        source.push(new JokerCard());
        TargetDeck target = new TargetDeck(1, new JokerCard());

        assertFalse(source.topMatchesOn(target));
    }

    @Test
    public void testInspectorTopMatchesOn_Illegal4() {
        SourceDeck source = new SourceDeck(5);
        source.push(new JackCard());
        TargetDeck target = new TargetDeck(3, new JackCard());

        assertFalse(source.topMatchesOn(target));
    }
}

