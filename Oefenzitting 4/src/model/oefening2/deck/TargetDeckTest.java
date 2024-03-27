package model.oefening2.deck;

import model.oefening2.card.NumberedCard;

import model.oefening2.card.Card;
import model.oefening2.card.JackCard;
import model.oefening2.card.JokerCard;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

public class TargetDeckTest {


    @Test
    public void testConstructor_Legal() {
        Card joker = new JokerCard();
        TargetDeck target = new TargetDeck(5, joker);

        assertEquals(target.getNbCards(), 1);
        assertEquals(target.getCapacity(), 5);
        assertEquals(target.getTop(), joker);
    }

    @Test
    public void testConstructor_Illegal() {
        assertThrows( IllegalArgumentException.class,() -> {
            TargetDeck target = new TargetDeck(5, null);
        });
    }

    @Test
    public void testInspectorHasMinimalSize_Legal1() {
        TargetDeck deck = new TargetDeck(4, new JokerCard());
        assertTrue(deck.hasMinimalSize());
    }

    @Test
    public void testInspectorHasMinimalSize_Legal2() {
        TargetDeck deck = new TargetDeck(2, new JokerCard());
        deck.addCard(new JackCard());

        assertFalse(deck.hasMinimalSize());
    }

    @Test
    public void testInspectorGetTop_Legal() {
        TargetDeck target = new TargetDeck(5, new JokerCard());
        target.push(new JokerCard());
        Card joker = new JokerCard();
        target.push(joker);

        assertEquals(target.getTop(), joker);
    }

    @Test
    public void testMutatorRemoveCard_Illegal() {
        assertThrows( IllegalStateException.class,() -> {
            TargetDeck target = new TargetDeck(5, new JokerCard());
            target.removeCard();
        });
    }

    @Test
    public void testMutatorMoveToHelp_Legal1() {
        TargetDeck target = new TargetDeck(3, new NumberedCard(10));
        target.push(new JackCard());
        HelpDeck helpDeck = new HelpDeck(3);

        assertTrue(target.topMatchesOn(helpDeck));
    }

    @Test
    public void testMutatorMoveToHelp_Legal2() {
        TargetDeck target = new TargetDeck(3, new NumberedCard(10));
        target.push(new JokerCard());
        TargetDeck target2 = new TargetDeck(2, new JokerCard());

        assertFalse(target.topMatchesOn(target2));
    }

    @Test
    public void testMutatorMoveToHelp_Legal3() {
        TargetDeck target = new TargetDeck(3, new NumberedCard(8));
        target.push(new NumberedCard(7));
        HelpDeck helpDeck = new HelpDeck(0);

        assertFalse(target.topMatchesOn(helpDeck));
    }
}

